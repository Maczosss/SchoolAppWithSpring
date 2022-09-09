package com.example.school.controller;

import com.example.school.model.Person;
import com.example.school.repository.CoursesRepository;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.SchoolClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {

//    @Autowired
//    private SchoolClassRepository schoolClassRepository;
//
//    @Autowired
//    private PersonRepository personRepository;
//
//    @Autowired
//    private CoursesRepository coursesRepository;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session) {
        var person = (Person) session.getAttribute("loggedInUser");
        var modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return modelAndView;
    }
}
