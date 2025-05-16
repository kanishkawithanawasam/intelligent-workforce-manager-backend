package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing day schedules operations.
 * Provides endpoints for retrieving and optimizing daily schedules.
 */
@RestController
@RequestMapping("/day-schedules")
public class DayScheduleController{

    private final DayScheduleService dayScheduleService;

    /**
     * Constructs a new DayScheduleController.
     * @param dayScheduleService the service for managing day schedule operations
     */
    public DayScheduleController(DayScheduleService dayScheduleService) {
        this.dayScheduleService = dayScheduleService;
    }

    /**
     * Retrieves the schedule for the current day.
     * @return ResponseEntity containing the today's schedule
     */
    @GetMapping()
    public ResponseEntity<?> getTodaySchedule() {
        return ResponseEntity
                .ok()
                .body(dayScheduleService.getTodaySchedule());
    }

    /**
     * Optimises the provided day schedule.
     * @param dtos list of shifts to be optimized
     * @return ResponseEntity containing the optimized list of shifts
     */
    @PostMapping("/optimise")
    public ResponseEntity<?> optimise(@RequestBody List<ShiftDTO> dtos) {
        List<ShiftDTO> updated = dayScheduleService.optimiseDaySchedule(dtos);
        return ResponseEntity.ok().body(updated);
    }


}
