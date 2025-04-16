package com.iwm.backend.api.schedule;

import com.iwm.backend.api.dtos.ScheduleRequestDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> previewSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO)
            throws IOException {
        return ResponseEntity.ok(schedulerService.generateWeeklySchedule(scheduleRequestDTO));
    }

    @GetMapping("/available-start-dates")
    public ResponseEntity<?> getAvailableDate(){
        List<LocalDate> dates = schedulerService.getWeekStartDates();
        return ResponseEntity.ok(dates);
    }


    @GetMapping("/by-date")
    public ResponseEntity<?> getScheduleByStartDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        WeeklyScheduleDTO dto= schedulerService.getWeekScheduleFromDate(startDate);
        return ResponseEntity.ok(dto);

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody WeeklyScheduleDTO weeklyScheduleDTO){
        schedulerService.saveWeeklySchedule(weeklyScheduleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(weeklyScheduleDTO);
    }

}
