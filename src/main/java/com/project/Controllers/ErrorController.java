package com.project.Controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ErrorController {

    @RequestMapping(value = "/forbidden", method = RequestMethod.GET)
    public RedirectView forbiddenErrorRed(Model model, HttpSession httpSession, HttpServletResponse response)
            throws IOException {
        return new RedirectView("/403");
    }
    
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView forbiddenError(Model model, HttpSession httpSession, HttpServletResponse response)
            throws IOException {
        return new ModelAndView("403");
    }
}
