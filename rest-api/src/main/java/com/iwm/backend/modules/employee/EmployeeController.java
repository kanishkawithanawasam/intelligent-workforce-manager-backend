package com.iwm.backend.modules.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing employee-related operations.
 * Provides endpoints for retrieving and updating employee information.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController{

    private final EmployeeService employeeService;

    /**
     * Constructs a new EmployeeController with the specified EmployeeService.
     *
     * @param employeeService the service for handling employee-related operations
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieves basic information for all employees.
     *
     * @return a list of DTOs containing basic employee information
     */
    @GetMapping("/basic-info")
    public List<EmployeeBasicInfoDTO> getEmployeeBasicInfo() {
        return employeeService.getEmployeesBasicInfo();
    }

    /**
     * Retrieves detailed demographic information for a specific employee.
     *
     * @param id the ID of the employee
     * @return ResponseEntity containing the employee's demographic information
     */
    @GetMapping("/demographics/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeFile(@PathVariable long id) {
        EmployeeDTO dto = employeeService.getEmployeeDTObyId(id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Updates an employee's demographic information.
     *
     * @param dto DTO containing the updated employee information
     * @return ResponseEntity containing the updated employee information
     */
    @PostMapping("/demographics")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO dto) {
        System.out.println(dto.toString());
        EmployeeDTO savedDto = employeeService.saveEmployee(dto);
        return ResponseEntity.ok().body(dto);

    }
}
