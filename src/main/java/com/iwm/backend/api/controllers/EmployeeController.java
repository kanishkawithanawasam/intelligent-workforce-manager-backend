package com.iwm.backend.api.controllers;

import com.iwm.backend.api.dtos.EmployeeBasicInfoDTO;
import com.iwm.backend.api.dtos.EmployeeFileDTO;
import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.api.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController{

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees-basic-info")
    public List<EmployeeBasicInfoDTO> getEmployeeBasicInfo() {
        return employeeService.getEmployeesBasicInfo();
    };

    @GetMapping("/employee-file/{id}")
    public EmployeeFileDTO getEmployeeFile(@PathVariable long id) {
        System.out.println("id:" + id);
        return employeeService.getEmployeeFile(id);
    }

}
