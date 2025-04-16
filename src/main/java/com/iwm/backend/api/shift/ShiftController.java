package com.iwm.backend.api.shift;


import com.iwm.backend.api.dtos.EmployeeScheduleShiftDTO;
import com.iwm.backend.api.dtos.ShiftDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getShiftsByEmployeeAndWeeklySchedule(EmployeeScheduleShiftDTO employeeScheduleShiftDTO) {
        List<ShiftDTO> shiftDTOList = shiftService.getWeeklySchedule(employeeScheduleShiftDTO);
        if (shiftDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        return ResponseEntity.ok(shiftDTOList);
    }

    @PostMapping
    public ResponseEntity<?> addOrUpdateShift(@RequestBody ShiftDTO shiftDTO) {
        ShiftDTO dto = shiftService.saveShift(shiftDTO);
        return ResponseEntity.ok(dto);
    }
    
    
    @PostMapping("/save-all")
    public ResponseEntity<?> saveOrUpdateAllShift(@RequestBody List<ShiftDTO> shiftDTOList) {
        List<ShiftDTO> dtos = shiftService.saveAllShifts(shiftDTOList);
        return ResponseEntity.ok(dtos);
    }

}
