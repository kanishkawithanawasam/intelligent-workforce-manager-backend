package com.iwm.backend.api.controllers;


import com.iwm.backend.api.dtos.EmployeeScheduleShiftDTO;
import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.services.ShiftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping("/find-by-week-and-employee")
    public ResponseEntity<?> getShiftsByEmployeeAndWeeklySchedule(EmployeeScheduleShiftDTO employeeScheduleShiftDTO)
            throws IOException {
        List<ShiftDTO> shiftDTOList = shiftService.getWeeklySchedule(employeeScheduleShiftDTO);
        if (shiftDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        return ResponseEntity.ok(shiftDTOList);
    }


    @PostMapping
    public ResponseEntity<?> addShift(@RequestBody ShiftDTO shiftDTO) {
        return ResponseEntity.ok(null);
    }
}
