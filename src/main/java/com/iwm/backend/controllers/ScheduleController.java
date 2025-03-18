package com.iwm.backend.controllers;


import com.iwm.backend.models.Model;
import com.iwm.backend.models.ScheduleTransferObject;
import com.iwm.backend.schedulegenerator.FuzzyGeneticScheduleGenerator;
import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.Schedule;
import com.iwm.backend.schedulegenerator.models.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/scheduler")
public class ScheduleController {

    @Autowired
    private Model model;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/generate-schedule")
    public ScheduleTransferObject schedule() {
        FuzzyGeneticScheduleGenerator fuzzyGeneticScheduleGenerator = new FuzzyGeneticScheduleGenerator(100);
        Schedule schedule = fuzzyGeneticScheduleGenerator.genSchedule();
        model.setSchedule(new ScheduleTransferObject(schedule));
        return model.getSchedule();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-schedule")
    public ScheduleTransferObject getSchedule() {
        if (model.getSchedule().getShifts().isEmpty()) {
            FuzzyGeneticScheduleGenerator fuzzyGeneticScheduleGenerator = new FuzzyGeneticScheduleGenerator(100);
            Schedule schedule = fuzzyGeneticScheduleGenerator.genSchedule();
            model.setSchedule(new ScheduleTransferObject(schedule));
        }
        return model.getSchedule();
    }

}
