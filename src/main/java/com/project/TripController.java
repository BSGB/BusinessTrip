package com.project;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public RedirectView calculatorPost(@ModelAttribute("Trip") Trip trip, HttpSession httpSession) throws ParseException {
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
        formValues.put("breakfast", calculateTrip.getBreakfastAmount());
        formValues.put("dinner", calculateTrip.getDinnerAmount());
        formValues.put("supper", calculateTrip.getSupperAmount());
        formValues.put("freeFood", calculateTrip.getFreeFoodCost().toString());
        formValues.put("totalDiet", calculateTrip.getDietValue().toString());
        formValues.put("trnsprtType", calculateTrip.getTransType());
        formValues.put("tcktPrice", calculateTrip.getTicketPrice().toString());
        formValues.put("underCcm", calculateTrip.getUnCcm().toString());
        formValues.put("overCcm", calculateTrip.getOvCcm().toString());
        formValues.put("motoCycle", calculateTrip.getMotorcycle().toString());
        formValues.put("motoBicycle", calculateTrip.getMotBicycle().toString());
        formValues.put("travelCost", calculateTrip.getTrvlCost().toString());
        formValues.put("lmpSum", calculateTrip.getLumpSum());
        formValues.put("lmp", calculateTrip.getLump().toString());
        formValues.put("billSleep", calculateTrip.getSleepBill().toString());
        formValues.put("pLmpSum", calculateTrip.getPLumpSum());
        formValues.put("pLmp", calculateTrip.getPLump().toString());
        formValues.put("rtrnPay", calculateTrip.getReturnPay().toString());
        formValues.put("costs", calculateTrip.getSumCosts().toString());
        formValues.put("advnc", calculateTrip.getAdvance().toString());
        formValues.put("paymnt", calculateTrip.getPayment().toString());

        httpSession.setAttribute("Trip", formLists);
        httpSession.setAttribute("CalculateTrip", formValues);
        return new RedirectView("/calculator/result");
    }

    @RequestMapping(value = "/calculator/result", method = RequestMethod.GET)
    public ModelAndView displayResults(@RequestParam(value = "report_id", required = false) String reportId, Model model, HttpSession session) {
//        Map<String, ?> arguments = RequestContextUtils.getInputFlashMap(request);
        model.addAllAttributes((Map)session.getAttribute("Trip"));
        model.addAllAttributes((Map)session.getAttribute("CalculateTrip"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        if(reportId != null) {
            //generatePDF
            modelAndView = new ModelAndView(new RedirectView("/calculator/result"));
        }
        return modelAndView;
    }
}
