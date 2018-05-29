package com.project.Controllers;

import com.itextpdf.text.DocumentException;
import com.project.Misc.*;
import com.project.Models.Report;
import com.project.Models.Trip;
import com.project.Models.User;
import com.project.Repositories.ReportRepository;
import com.project.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.URLConnection;
import java.text.ParseException;

/**
 * Created by Bartosz on 06.04.2018.
 */
@RestController
public class TripController {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public TripController(UserRepository userRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.GET)
    public ModelAndView index(Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("error") != null) {
            model.addAttribute("error", httpSession.getAttribute("error"));
            httpSession.removeAttribute("error");
        }
        return new ModelAndView("calculator");
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.POST)
    public RedirectView calculatorPost(@ModelAttribute("Trip") @Valid Trip trip,
                                       BindingResult bindingResult, HttpSession httpSession,
                                       Model model) throws ParseException {
        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("error", "Wypełnij pola poprawnymi wartościami!");
            return new RedirectView("/calculator");
        }

        CalculateTrip calculateTrip = new CalculateTrip(trip);

        //obiekt raportu
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userRepository.findByUserLogin(userName);
        ReportDbTranslator dbTranslator = new ReportDbTranslator(calculateTrip, user);
        Report report = dbTranslator.getReport();
        user.getReports().add(report);
        userRepository.save(user);
        userRepository.flush();

        int newestId = 0;
        for (Report rp : user.getReports()) {

            if (rp.getId() > newestId) {
                newestId = rp.getId();
            }
        }
        return new RedirectView("/calculator/result?reportId=" + newestId);
    }

    @RequestMapping(value = "/calculator/result{reportId}", method = RequestMethod.GET)
    public ModelAndView displayResults(@RequestParam(value = "reportId", required = false) int reportId, Model model,
                                       HttpSession session, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userRepository.findByUserLogin(userName);
        DbReportProtector protector = new DbReportProtector(user, reportId);
        ModelAndView modelAndView;


        if (protector.isOwner() || auth.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
            Report report = reportRepository.findReportById(reportId);
            ReportResultTranslator resultTranslator = new ReportResultTranslator(report);


            model.addAttribute("addCosts", resultTranslator.getAdditionalValues());
            model.addAllAttributes(resultTranslator.getFormValues());
            model.addAttribute("reportId", reportId);

            session.setAttribute("Report", report);

            modelAndView = new ModelAndView();
            modelAndView.setViewName("result");
        } else {
            modelAndView = new ModelAndView("redirect:../manage/reports");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/calculator/download/pdf{reportId}", method = RequestMethod.GET)
    public void getPdfFile(@RequestParam(value = "reportId", required = false) int reportId, Model model, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userRepository.findByUserLogin(userName);
        DbReportProtector protector = new DbReportProtector(user, reportId);

        if(protector.isOwner() || auth.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
            Report report = reportRepository.findReportById(reportId);
            ReportFileTranslator reportFileTranslator = new ReportFileTranslator(report);

            PdfFileSupporter pdfFileSupporter = new PdfFileSupporter(reportFileTranslator.getAddMap(), reportFileTranslator.getGeneralMap());
            pdfFileSupporter.prepareDocument();
            pdfFileSupporter.generatePdf();

            File file = new File(pdfFileSupporter.getFileName() + ".pdf");
            InputStream in = new FileInputStream(file);

            response.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            response.setHeader("Content-Length", String.valueOf(file.length()));
            OutputStream os = response.getOutputStream();
            FileCopyUtils.copy(in, os);
            in.close();
            os.close();
        } else {
            response.sendRedirect("../manage/reports");
        }
    }

    @RequestMapping(value = "/calculator/download/csv{reportId}", method = RequestMethod.GET)
    public void getCsvFile(@RequestParam(value = "reportId", required = false) int reportId, Model model, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userRepository.findByUserLogin(userName);
        DbReportProtector protector = new DbReportProtector(user, reportId);

        if(protector.isOwner() || auth.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
            Report report = reportRepository.findReportById(reportId);
            ReportFileTranslator reportFileTranslator = new ReportFileTranslator(report);
            CsvFileSupporter csvFileSupporter = new CsvFileSupporter(reportFileTranslator.getAddMap(), reportFileTranslator.getGeneralMap());

            csvFileSupporter.generateCsv();

            File file = new File(csvFileSupporter.getFileName() + ".csv");
            InputStream in = new FileInputStream(file);

            response.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            response.setHeader("Content-Length", String.valueOf(file.length()));
            OutputStream os = response.getOutputStream();
            FileCopyUtils.copy(in, os);
            in.close();
            os.close();

        } else {
            response.sendRedirect("../manage/reports");
        }
    }
}
