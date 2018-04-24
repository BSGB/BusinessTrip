package com.project.Controllers;

import com.project.Models.User;
import com.project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.*;
import java.text.ParseException;
import java.util.Set;

@RestController
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("/home");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView index(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("error") != null) {
            model.addAttribute("error", httpSession.getAttribute("error"));
            httpSession.removeAttribute("error");
        }
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public RedirectView homePost(@ModelAttribute("User") @Valid User user,
                                       BindingResult bindingResult, HttpSession httpSession,
                                       Model model) throws ParseException {

        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("error", "Wypełnij pola poprawnymi wartościami!");
            return new RedirectView("/home");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return new RedirectView("/home");
    }
}
