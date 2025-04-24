package com.iwm.backend.modules.schedules;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing weekly work schedules.
 * Provides endpoints for creating, retrieving and managing weekly schedules.
 */
@RestController
@RequestMapping("/schedules")
public class WeeklyScheduleController {
    private final WeeklyScheduleService weeklyScheduleService;


    /**
     * Constructs a new WeeklyScheduleController with the required service.
     *
     * @param weeklyScheduleService the service for managing weekly schedules
     */
    public WeeklyScheduleController(WeeklyScheduleService weeklyScheduleService) {
        this.weeklyScheduleService = weeklyScheduleService;
    }


    /**
     * Generates a preview of a weekly schedule based on the provided request data.
     *
     * @param weeklyScheduleRequestDTO the schedule request data
     * @return ResponseEntity containing the generated schedule preview
     * @throws IOException if there's an error processing the request
     */
    @PostMapping("/preview")
    public ResponseEntity<?> previewSchedule(@RequestBody WeeklyScheduleRequestDTO weeklyScheduleRequestDTO)
            throws IOException {
        return ResponseEntity.ok(weeklyScheduleService.generateWeeklySchedule(weeklyScheduleRequestDTO));
    }

    /**
     * Retrieves all available schedule start dates.
     *
     * @return ResponseEntity containing a list of available start dates
     */
    @GetMapping("/available-start-dates")
    public ResponseEntity<?> getAvailableDate(){
        List<LocalDate> dates = weeklyScheduleService.getWeekStartDates();
        return ResponseEntity.ok(dates);
    }


    /**
     * Retrieves a weekly schedule for a specific start date.
     *
     * @param startDate the start date of the schedule to retrieve
     * @return ResponseEntity containing the requested schedule
     */
    @GetMapping("/by-date")
    public ResponseEntity<?> getScheduleByStartDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        WeeklyScheduleDTO dto= weeklyScheduleService.getWeekScheduleFromDate(startDate);
        return ResponseEntity.ok(dto);

    }

    /**
     * Saves a new weekly schedule.
     *
     * @param weeklyScheduleDTO the schedule data to save
     * @return ResponseEntity containing the saved schedule
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody WeeklyScheduleDTO weeklyScheduleDTO){
        weeklyScheduleService.saveWeeklySchedule(weeklyScheduleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(weeklyScheduleDTO);
    }

}
