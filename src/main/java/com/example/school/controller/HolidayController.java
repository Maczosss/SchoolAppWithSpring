package com.example.school.controller;

import com.example.school.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidayController {

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
        List<Holiday> holidays = Arrays.asList(
                new Holiday(" Jan 1 ", "New Years Day", Holiday.Type.FESTIVAL),
                new Holiday(" Oct 31 ", "Halloween", Holiday.Type.FESTIVAL),
                new Holiday(" Nov 24 ", "Thanksgiving ", Holiday.Type.FESTIVAL),
                new Holiday(" Dec 25 ", "Christmas", Holiday.Type.FESTIVAL),
                new Holiday(" Jan 17 ", "MLK", Holiday.Type.FEDERAL),
                new Holiday(" July 4 ", "Independence Day", Holiday.Type.FEDERAL),
                new Holiday(" Sep 5 ", "Labor Day", Holiday.Type.FEDERAL),
                new Holiday(" Nov 11 ", "Veterans Day", Holiday.Type.FEDERAL)
        );
        for (Holiday.Type type : Holiday.Type.values()) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type))
                            .collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
