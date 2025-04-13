package com.iwm.backend.api.controllers;

import com.iwm.backend.api.dtos.employee.EmployeeBasicInfoDTO;
import com.iwm.backend.api.dtos.employee.EmployeeDTO;
import com.iwm.backend.api.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController{

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/basic-info")
    public List<EmployeeBasicInfoDTO> getEmployeeBasicInfo() {
        return employeeService.getEmployeesBasicInfo();
    }

    @GetMapping("/demographics/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeFile(@PathVariable long id) {
        EmployeeDTO dto = employeeService.getEmployee(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO dto) {
        EmployeeDTO savedDto = employeeService.saveEmployee(dto);
        return ResponseEntity.ok().body(dto);

    }
}
