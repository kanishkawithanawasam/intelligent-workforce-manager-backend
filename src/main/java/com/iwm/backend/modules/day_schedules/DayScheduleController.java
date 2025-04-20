package com.iwm.backend.modules.day_schedules;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/day-schedules")
public class DayScheduleController{

    private DayScheduleService dayScheduleService;

    public DayScheduleController(DayScheduleService dayScheduleService) {
        this.dayScheduleService = dayScheduleService;
    }
    @GetMapping()
    public ResponseEntity<?> getTodaySchedule() {
        return ResponseEntity
                .ok()
                .body(dayScheduleService.getTodaySchedule());
    }



}
