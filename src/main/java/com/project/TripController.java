package com.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartosz on 06.04.2018.
 */
@Controller
public class TripController {

   @GetMapping("/")
    public String index() {
       return "redirect:/calculator";
   }

   @GetMapping("/calculator")
    public String calculatorGet(Model model) {
       model.addAttribute("calculator", new Trip());
       return "calculator";
   }

   @PostMapping("/calculator")
    public String calculatorPost(@ModelAttribute Trip trip, Model model) throws ParseException {
       CalculateTrip calculateTrip = new CalculateTrip(trip);
       Map<String, String> attributes = new HashMap<>();
       attributes.put("leave", calculateTrip.getLeaveTime());
       attributes.put("arrive", calculateTrip.getArriveTime());
       attributes.put("total", calculateTrip.getTotalTime());
       model.addAllAttributes(attributes);
       return "result";
   }

}
