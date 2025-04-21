package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day-schedules")
public class DayScheduleController{

    private final DayScheduleService dayScheduleService;

    public DayScheduleController(DayScheduleService dayScheduleService) {
        this.dayScheduleService = dayScheduleService;
    }
    @GetMapping()
    public ResponseEntity<?> getTodaySchedule() {
        return ResponseEntity
                .ok()
                .body(dayScheduleService.getTodaySchedule());
    }

    @PostMapping("/optimise")
    public ResponseEntity<?> optimise(@RequestBody List<ShiftDTO> dtos) {
        return ResponseEntity.ok().body(dayScheduleService.optimiseDaySchedule(dtos));
    }


}
