package com.example.school.controller;

import com.example.school.model.Holiday;
import com.example.school.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidayController {

    @Autowired
    private HolidaysRepository holidaysRepository;

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable(required = false) String display, Model model) {

        if (display != null) {
            switch (display) {
                case "all" -> {
                    model.addAttribute("festival", true);
                    model.addAttribute("federal", true);
                }
                case "federal" -> model.addAttribute("federal", true);
                case "festival" -> model.addAttribute("festival", true);
            }
        }
        var holidays = holidaysRepository.findAll();
        var holidayList = StreamSupport.stream(holidays.spliterator(), false).toList();

        for (Holiday.Type type : Holiday.Type.values()) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type))
                            .collect(Collectors.toList())));
        }
        //old
//        for (Holiday.Type type : Holiday.Type.values()) {
//            model.addAttribute(type.toString(),
//                    (holidays.stream().filter(holiday -> holiday.getType().equals(type))
//                            .collect(Collectors.toList())));
//        }
        return "holidays.html";
    }
}
