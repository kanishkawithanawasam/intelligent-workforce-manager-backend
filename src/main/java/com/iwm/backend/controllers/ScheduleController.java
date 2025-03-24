package com.iwm.backend.controllers;


import com.iwm.backend.models.Model;
import com.iwm.backend.models.ScheduleTransferObject;
import com.iwm.backend.schedulegenerator.configurations.FGAConfigs;
import com.iwm.backend.schedulegenerator.FuzzyGeneticScheduleGenerator;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/scheduler")
public class ScheduleController {

    @Autowired
    private Model model;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/generate-schedule")
    public ScheduleTransferObject schedule() {
        WeeklySchedule weeklySchedule = getSchedulesGenerator().genSchedule();
        model.setSchedule(new ScheduleTransferObject(weeklySchedule));
        return model.getSchedule();

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-schedule")
    public ScheduleTransferObject getSchedule() {
        // Generate a new schedule if no schedule is generated.
        if (model.getSchedule().getShifts().isEmpty()) {
            WeeklySchedule weeklySchedule = getSchedulesGenerator().genSchedule();
            model.setSchedule(new ScheduleTransferObject(weeklySchedule));
        }
        return model.getSchedule();
    }


    /**
     * Create a schedules generator object which uses a genetic algorithm to generate schedule.
     * @return A schedule generator.
     */
    private FuzzyGeneticScheduleGenerator getSchedulesGenerator() {
        FGAConfigs fgaConfigs = new FGAConfigs();
        fgaConfigs.setNumberOfIterations(200);
        fgaConfigs.setPopulationSize(500);
        return new FuzzyGeneticScheduleGenerator(fgaConfigs);
    }

}
