package com.example.school.controller;

import com.example.school.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        var person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if (person.getSchoolClass() != null && person.getSchoolClass().getName() != null) {
            model.addAttribute("enrolledClass", person.getSchoolClass().getName());
        }
        session.setAttribute("loggedInUser", person);
        return "dashboard.html";
    }
}
