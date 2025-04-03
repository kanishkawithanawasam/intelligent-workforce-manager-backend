package com.iwm.backend.api.services;


import com.iwm.backend.api.mappers.EmployeeDomainMapper;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.schedulegenerator.models.Employee;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDomainMapper employeeDomainMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDomainMapper employeeDomainMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeDomainMapper = employeeDomainMapper;
    }

    @Transactional
    public List<Employee> getEmployeesForScheduling(){
        return employeeDomainMapper.toDomainList(employeeRepository.findAll());
    }

}
