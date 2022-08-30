package com.example.school.controller;

import com.example.school.model.Person;
import com.example.school.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    PersonalService personalService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register.html";
    }
}
