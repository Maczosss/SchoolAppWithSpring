package com.example.school.controller;

import com.example.school.model.Person;
import com.example.school.model.SchoolClass;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.SchoolClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        var schoolClasses = schoolClassRepository.findAll();
        var modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("schoolClasses", schoolClasses);
        modelAndView.addObject("schoolClass", new SchoolClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("schoolClass") SchoolClass schoolClass) {
        schoolClassRepository.save(schoolClass);
        var modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        var schoolClass = schoolClassRepository.findById(id);
        for (Person person : schoolClass.get().getPersons()) {
            person.setSchoolClass(null);
            personRepository.save(person);
        }
        schoolClassRepository.deleteById(id);
        var modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId) {
//        var students = schoolClassRepository.findById(classId).get().getPersons();
        var modelAndView = new ModelAndView("students.html");
//        modelAndView.addObject(students);
        return modelAndView;
    }
}
