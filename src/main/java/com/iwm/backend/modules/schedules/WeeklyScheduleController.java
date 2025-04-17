package com.iwm.backend.modules.schedules;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class WeeklyScheduleController {
    private final WeeklyScheduleService weeklyScheduleService;


    public WeeklyScheduleController(WeeklyScheduleService weeklyScheduleService) {
        this.weeklyScheduleService = weeklyScheduleService;
    }


    @PostMapping("/preview")
    public ResponseEntity<?> previewSchedule(@RequestBody WeeklyScheduleRequestDTO weeklyScheduleRequestDTO)
            throws IOException {
        return ResponseEntity.ok(weeklyScheduleService.generateWeeklySchedule(weeklyScheduleRequestDTO));
    }

    @GetMapping("/available-start-dates")
    public ResponseEntity<?> getAvailableDate(){
        List<LocalDate> dates = weeklyScheduleService.getWeekStartDates();
        return ResponseEntity.ok(dates);
    }


    @GetMapping("/by-date")
    public ResponseEntity<?> getScheduleByStartDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        WeeklyScheduleDTO dto= weeklyScheduleService.getWeekScheduleFromDate(startDate);
        return ResponseEntity.ok(dto);

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody WeeklyScheduleDTO weeklyScheduleDTO){
        weeklyScheduleService.saveWeeklySchedule(weeklyScheduleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(weeklyScheduleDTO);
    }

}
