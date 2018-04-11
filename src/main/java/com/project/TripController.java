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
    public RedirectView calculatorPost(@ModelAttribute Trip trip, RedirectAttributes redirectAttributes) throws ParseException {
        CalculateTrip calculateTrip = new CalculateTrip(trip);
        redirectAttributes.addFlashAttribute("costsList", trip.getCosts());
        redirectAttributes.addFlashAttribute("amountsList", trip.getAmounts());
        redirectAttributes.addFlashAttribute("dscrp", calculateTrip.getDescript());
        redirectAttributes.addFlashAttribute("leave", calculateTrip.getLeaveTime());
        redirectAttributes.addFlashAttribute("arrive", calculateTrip.getArriveTime());
        redirectAttributes.addFlashAttribute("total", calculateTrip.getTotalTime());
        redirectAttributes.addFlashAttribute("diet", calculateTrip.getDietCost().toString());
        redirectAttributes.addFlashAttribute("breakfast", calculateTrip.getBreakfastAmount());
        redirectAttributes.addFlashAttribute("dinner", calculateTrip.getDinnerAmount());
        redirectAttributes.addFlashAttribute("supper", calculateTrip.getSupperAmount());
        redirectAttributes.addFlashAttribute("freeFood", calculateTrip.getFreeFoodCost().toString());
        redirectAttributes.addFlashAttribute("totalDiet", calculateTrip.getDietValue().toString());
        redirectAttributes.addFlashAttribute("trnsprtType", calculateTrip.getTransType());
        redirectAttributes.addFlashAttribute("tcktPrice", calculateTrip.getTicketPrice().toString());
        redirectAttributes.addFlashAttribute("underCcm", calculateTrip.getUnCcm().toString());
        redirectAttributes.addFlashAttribute("overCcm", calculateTrip.getOvCcm().toString());
        redirectAttributes.addFlashAttribute("motoCycle", calculateTrip.getMotorcycle().toString());
        redirectAttributes.addFlashAttribute("motoBicycle", calculateTrip.getMotBicycle().toString());
        redirectAttributes.addFlashAttribute("travelCost", calculateTrip.getTrvlCost().toString());
        redirectAttributes.addFlashAttribute("lmpSum", calculateTrip.getLumpSum());
        redirectAttributes.addFlashAttribute("lmp", calculateTrip.getLump().toString());
        redirectAttributes.addFlashAttribute("billSleep", calculateTrip.getSleepBill().toString());
        redirectAttributes.addFlashAttribute("pLmpSum", calculateTrip.getPLumpSum());
        redirectAttributes.addFlashAttribute("pLmp", calculateTrip.getPLump().toString());
        redirectAttributes.addFlashAttribute("rtrnPay", calculateTrip.getReturnPay().toString());
        redirectAttributes.addFlashAttribute("costs", calculateTrip.getSumCosts().toString());
        redirectAttributes.addFlashAttribute("advnc", calculateTrip.getAdvance().toString());
        redirectAttributes.addFlashAttribute("paymnt", calculateTrip.getPayment().toString());

        return new RedirectView("/calculator/result");
    }

    @RequestMapping(value = "/calculator/result", method = RequestMethod.GET)
    public ModelAndView displayResults(@RequestParam(value = "report_id", required = false) String reportId, HttpServletRequest request, Model model) {
//        Map<String, ?> arguments = RequestContextUtils.getInputFlashMap(request);
        System.out.println("X: " + reportId);
        return new ModelAndView("result");
    }
}
