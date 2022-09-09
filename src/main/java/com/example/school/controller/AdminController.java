package com.example.school.controller;

import com.example.school.model.Contact;
import com.example.school.model.Courses;
import com.example.school.model.Person;
import com.example.school.model.SchoolClass;
import com.example.school.repository.CoursesRepository;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.SchoolClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;

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
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        var modelAndView = new ModelAndView("students.html");
        var schoolClass = schoolClassRepository.findById(classId);
        modelAndView.addObject("schoolClass", schoolClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("schoolClass", schoolClass.get());
        if (error != null) {
            var errorMessage = "Invalid Email entered!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        var modelAndView = new ModelAndView();
        var schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        var personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId() + "&error=true");
            return modelAndView;
        }
        personEntity.setSchoolClass(schoolClass);
        personRepository.save(personEntity);
        schoolClass.getPersons().add(personEntity);
        schoolClassRepository.save(schoolClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        var schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        var person = personRepository.findById(personId);
        person.get().setSchoolClass(null);
        schoolClass.getPersons().remove(person.get());
        var savedClass = schoolClassRepository.save(schoolClass);
        session.setAttribute("schoolClass", savedClass);
        var modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
//        var courses = coursesRepository.findByOrderByNameDesc(); static sorting
        //dynamic sorting
        var courses = coursesRepository.findAll(Sort.by("name").ascending());
        var modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        var modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id, @RequestParam(required = false) String error, HttpSession session) {
        var modelAndView = new ModelAndView("course_students.html");
        var courses = coursesRepository.findById(id);
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", courses.get());
        if (error != null) {
            var errorMessage = "Invalid Email entered!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        var modelAndView = new ModelAndView();
        var personEntity = personRepository.readByEmail(person.getEmail());
        var courses = (Courses) session.getAttribute("courses");
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession session) {
        var courses = (Courses) session.getAttribute("courses");
        var person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person.get());
        personRepository.save(person.get());
        session.setAttribute("courses", courses);
        var modelAndView = new ModelAndView("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }
}
