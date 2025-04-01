package com.iwm.backend.api.controllers;

import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController{

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public List<EmployeeEM> findAll(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeEM findById(long id){
        return employeeRepository.findById(id).orElse(null);
    }
}
