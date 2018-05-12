package com.project.Controllers;

import java.util.LinkedHashSet;

import javax.servlet.http.HttpSession;

import com.project.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.Repositories.UserRepository;

@RestController
public class ManageController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("/manage/reports");
    }
    
    @RequestMapping(value = "/manage/reports", method = RequestMethod.GET)
    public ModelAndView report(Model model, HttpSession httpSession) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userRepository.findByUserLogin(userName);
        model.addAttribute("reports", user.getReports());
        return new ModelAndView("reportsMgmt");
    }
    
    @RequestMapping(value = "/manage/user", method = RequestMethod.GET)
    public ModelAndView user(Model model, HttpSession httpSession) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ModelAndView("usersMgmt");
    }
}
