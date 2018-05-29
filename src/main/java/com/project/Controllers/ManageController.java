package com.project.Controllers;

import com.project.Models.Report;
import com.project.Models.SearchAjaxRespBody;
import com.project.Models.SearchCriteria;
import com.project.Models.User;
import com.project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ManageController {

    private final UserRepository userRepository;

    @Autowired
    public ManageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        return new ModelAndView("Personal_Management/reports");
    }
    
    @RequestMapping(value = "/manage/user", method = RequestMethod.GET)
    public ModelAndView user(Model model, HttpSession httpSession) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ModelAndView("Personal_Management/user");
    }
    
    @RequestMapping(value = "/manage/reports/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchReport(@Valid @RequestBody SearchCriteria searchCriteria, Model model, HttpSession httpSession) {
    	  SearchAjaxRespBody response = new SearchAjaxRespBody();
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          String userName = auth.getName();
          User user = userRepository.findByUserLogin(userName);
          
          List<Report> reports = user.getReports()
        		  .stream()
        		  .filter(report -> report.getReportDescr().toUpperCase().contains((CharSequence) searchCriteria.getSearchCrit().toUpperCase()))
        		  .collect(Collectors.toList());
          
          if (reports.isEmpty()) {
              response.setMsg("Nie znaleziono raportow!");
          } else {
              response.setMsg("Sukces!");
          }
          response.setData(reports);

          return ResponseEntity.ok(response);
    }
}