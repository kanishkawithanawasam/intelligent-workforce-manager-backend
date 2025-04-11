package com.iwm.backend.api.controllers;

import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.services.SchedulerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final SchedulerService schedulerService;


    public ScheduleController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }


    @PostMapping("/preview")
    public WeeklyScheduleDTO previewSchedule(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate)
            throws IOException {
        return schedulerService.generateWeeklySchedule(startDate);
    }

    @GetMapping("/available-dates")
    public List<LocalDate> getAvailableDate(){
        return schedulerService.getWeekStartDates();
    }


    @GetMapping("/{startDate}")
    public WeeklyScheduleDTO getWeekSchedule(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate)
            throws IOException {
        return this.schedulerService.getWeekScheduleFromWeek(startDate);
    }

    @PutMapping
    public void save(@RequestBody WeeklyScheduleDTO weeklyScheduleDTO) throws IOException {
        schedulerService.saveWeeklySchedule(weeklyScheduleDTO);
    }

}
