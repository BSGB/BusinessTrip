package com.project.Controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.Models.User;
import com.project.Repositories.UserRepository;

@RestController
public class HomeController {
    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("/home");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView index(Model model, HttpSession httpSession, HttpServletResponse response) throws IOException {
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
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public RedirectView homePost(@ModelAttribute("User") @Valid User user,
                                       BindingResult bindingResult, HttpSession httpSession,
                                       Model model) throws ParseException {

        if (bindingResult.hasErrors()) {
        	List<ObjectError> bindingErrors = bindingResult.getAllErrors();      	
        	httpSession.setAttribute("error", bindingErrors);
//            httpSession.setAttribute("error", "Wypełnij pola poprawnymi wartościami!");
            return new RedirectView("/home");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setUserRole("USER");
        user.setAccountNonLocked(true);
        userRepository.save(user);
        httpSession.setAttribute("regSuccess", "Rejestracja zakończona pomyślnie!");
        return new RedirectView("/home");
    }
}
