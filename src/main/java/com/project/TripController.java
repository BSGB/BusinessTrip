package com.project;

import com.itextpdf.text.DocumentException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bartosz on 06.04.2018.
 */
@RestController
public class TripController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("/calculator");
    }
    
    @RequestMapping(value = "/calculator", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("calculator");
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.POST)
    public RedirectView calculatorPost(@ModelAttribute("Trip") @Valid Trip trip, BindingResult bindingResult, HttpSession httpSession) throws ParseException {
        if (bindingResult.hasErrors()) {
            return new RedirectView("/calculator");
        }
        CalculateTrip calculateTrip = new CalculateTrip(trip);
        HashMap<String, List> formLists = new HashMap<>();
        HashMap<String, String> formValues = new HashMap<>();
        formLists.put("costsList", trip.getCosts());
        formLists.put("amountsList", trip.getAmounts());
        formValues.put("dscrp", calculateTrip.getDescript());
        formValues.put("leave", calculateTrip.getLeaveTime());
        formValues.put("arrive", calculateTrip.getArriveTime());
        formValues.put("total", calculateTrip.getTotalTime());
        formValues.put("diet", calculateTrip.getDietCost().toString());
        formValues.put("breakfast", String.valueOf(calculateTrip.getBreakfastAmount()));
        formValues.put("dinner", String.valueOf(calculateTrip.getDinnerAmount()));
        formValues.put("supper", String.valueOf(calculateTrip.getSupperAmount()));
        formValues.put("freeFood", String.valueOf(calculateTrip.getFreeFoodCost()));
        formValues.put("totalDiet", String.valueOf(calculateTrip.getDietValue()));
        formValues.put("trnsprtType", calculateTrip.getTransType());
        formValues.put("tcktPrice", calculateTrip.getTicketPrice().toString());
        formValues.put("underCcm", calculateTrip.getUnCcm().toString());
        formValues.put("overCcm", calculateTrip.getOvCcm().toString());
        formValues.put("motoCycle", calculateTrip.getMotorcycle().toString());
        formValues.put("motoBicycle", calculateTrip.getMotBicycle().toString());
        formValues.put("travelCost", calculateTrip.getTrvlCost().toString());
        formValues.put("lmpSum", String.valueOf(calculateTrip.getLumpSum()));
        formValues.put("lmp", calculateTrip.getLump().toString());
        formValues.put("billSleep", calculateTrip.getSleepBill().toString());
        formValues.put("pLmpSum", String.valueOf(calculateTrip.getPLumpSum()));
        formValues.put("pLmp", calculateTrip.getPLump().toString());
        formValues.put("rtrnPay", calculateTrip.getReturnPay().toString());
        formValues.put("costs", calculateTrip.getSumCosts().toString());
        formValues.put("advnc", calculateTrip.getAdvance().toString());
        formValues.put("paymnt", calculateTrip.getPayment().toString());

        httpSession.setAttribute("Trip", formLists);
        httpSession.setAttribute("CalculateTrip", formValues);
        httpSession.setAttribute("TripObject", trip);
        httpSession.setAttribute("CalculateObject", calculateTrip);
        return new RedirectView("/calculator/result");
    }

    @RequestMapping(value = "/calculator/result", method = RequestMethod.GET)
    public ModelAndView displayResults(@RequestParam(value = "report_id", required = false) String reportId, Model model, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {
//        Map<String, ?> arguments = RequestContextUtils.getInputFlashMap(request);
        model.addAllAttributes((Map)session.getAttribute("Trip"));
        model.addAllAttributes((Map)session.getAttribute("CalculateTrip"));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        return modelAndView;
    }

    @RequestMapping(value = "/calculator/result/{command}", method = RequestMethod.GET)
    public void getFile(@PathVariable("command") String command, Model model, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {

            TripPersist tripPersist = new TripPersist(
                    (CalculateTrip)session.getAttribute("CalculateObject"),
                    (Trip)session.getAttribute("TripObject"));

            FileSupporter fileSupporter = new FileSupporter(tripPersist.getAdditionalMap(), tripPersist.getGeneralMap());
            fileSupporter.prepareDocument();
            fileSupporter.generatePdf();

            File file = new File(fileSupporter.getFileName() + ".pdf");
            InputStream in = new FileInputStream(file);

            response.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            response.setHeader("Content-Length", String.valueOf(file.length()));
            OutputStream os = response.getOutputStream();
            FileCopyUtils.copy(in, os);
            in.close();
            os.close();
    }
}
