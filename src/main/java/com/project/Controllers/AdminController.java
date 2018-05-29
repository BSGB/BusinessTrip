package com.project.Controllers;

import com.project.Misc.DbReportProtector;
import com.project.Misc.FilterHandler;
import com.project.Misc.PdfFileSupporter;
import com.project.Misc.ReportFileTranslator;
import com.project.Models.FilterAjaxRespBody;
import com.project.Models.FilterCriteria;
import com.project.Models.Report;
import com.project.Models.User;
import com.project.Repositories.ReportRepository;
import com.project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.*;

@RestController
public class AdminController {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public AdminController(UserRepository userRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
    }

    @RequestMapping(value = "/administration", method = RequestMethod.GET)
    public RedirectView redirectToAdminReports() {
        return new RedirectView("/administration/reports");
    }

    @RequestMapping(value = "/administration/reports", method = RequestMethod.GET)
    public ModelAndView admin(Model model) {

        List<User> allUsers = userRepository.findAll();
        Set<Report> allReports = new LinkedHashSet<>();
        Set<String> allCompanies = new HashSet<>();

        allUsers.forEach(u -> allReports.addAll(u.getReports()));
        allUsers.forEach(u -> allCompanies.add(u.getCompanyName()));

        model.addAttribute("reports", allReports);
        model.addAttribute("companies", allCompanies);
        return new ModelAndView("Admin_Management/reports");
    }

    @RequestMapping(value = "/administration/reports/delete{reportId}", method = RequestMethod.GET)
    public void deleteReport(@RequestParam(value = "reportId", required = false) int reportId, Model model, HttpSession session, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userRepository.findByUserLogin(userName);
        DbReportProtector protector = new DbReportProtector(user, reportId);

        if(protector.isOwner() || auth.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
            Report report = reportRepository.findReportById(reportId);
            user = report.getUser();
            user.getReports().remove(report);
            userRepository.flush();
            response.sendRedirect("/administration/reports");
        } else {
            response.sendRedirect("../../manage/reports");
        }
    }

    @RequestMapping(value = "/administration/reports/search", method = RequestMethod.POST)
    public ResponseEntity<?> filterReport(@Valid @RequestBody FilterCriteria filterCriteria) throws ParseException {
        FilterAjaxRespBody response = new FilterAjaxRespBody();

        List<User> users = new ArrayList<>();
        FilterHandler filterHandler = new FilterHandler(filterCriteria, userRepository);

        switch(filterCriteria.getFilterCrit()){
            case "com":
                users = filterHandler.filterByCompanyName();
                break;
            case "date":
                users = filterHandler.filterByDate();
                break;
            case "comAndDate":
                users = filterHandler.filterByDateAndCompanyName();
                break;
        }

        if (users.isEmpty()) {
            response.setMsg("Nie znaleziono raportow!");
        } else {
            response.setMsg("Sukces!");
        }
        response.setData(users);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/administration/users", method = RequestMethod.GET)
    public ModelAndView editUser(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return new ModelAndView("Admin_Management/users");
    }

    @RequestMapping(value = "/administration/users/activate{userId}", method = RequestMethod.GET)
    public RedirectView activateUser(@RequestParam(value = "userId", required = false) int userId, Model model, HttpSession session) {
        User user = userRepository.findUserById(userId);
        user.setAccountNonLocked(true);
        userRepository.save(user);
        return new RedirectView("/administration/users");
    }

    @RequestMapping(value = "/administration/users/deactivate{userId}", method = RequestMethod.GET)
    public RedirectView deactivateUser(@RequestParam(value = "userId", required = false) int userId, Model model, HttpSession session) {
        User user = userRepository.findUserById(userId);
        user.setAccountNonLocked(false);
        userRepository.save(user);
        return new RedirectView("/administration/users");
    }

    @RequestMapping(value = "/administration/add_user", method = RequestMethod.GET)
    public ModelAndView addUser(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("error") != null) {
            List<ObjectError> errorList = (List) httpSession.getAttribute("error");
            List<String> errorStringList = new ArrayList<>();
            for(ObjectError error : errorList) {
                errorStringList.add(error.getDefaultMessage());
            }
            model.addAttribute("error", errorStringList);
            httpSession.removeAttribute("error");
        } else if (httpSession.getAttribute("regSuccess") != null) {
            model.addAttribute("regSuccess", httpSession.getAttribute("regSuccess"));
            httpSession.removeAttribute("regSuccess");
        }
        return new ModelAndView("Admin_Management/add_user");
    }

    @RequestMapping(value = "/administration/add_user", method = RequestMethod.POST)
    public RedirectView addUserPost(@ModelAttribute("User") @Valid User user,
                                 BindingResult bindingResult, HttpSession httpSession,
                                 Model model) throws ParseException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> bindingErrors = bindingResult.getAllErrors();
            httpSession.setAttribute("error", bindingErrors);
            return new RedirectView("/administration/add_user");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setAccountNonLocked(true);
        userRepository.save(user);
        httpSession.setAttribute("regSuccess", "Rejestracja zakończona pomyślnie!");
        return new RedirectView("/administration/add_user");
    }
}
