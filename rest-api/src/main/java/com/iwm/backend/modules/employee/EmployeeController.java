package com.iwm.backend.modules.employee;

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

    @PostMapping("/demographics")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO dto) {
        System.out.println(dto.toString());
        EmployeeDTO savedDto = employeeService.saveEmployee(dto);
        return ResponseEntity.ok().body(dto);

    }
}
