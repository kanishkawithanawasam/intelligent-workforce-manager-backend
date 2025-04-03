package com.iwm.backend.api.services;


import com.iwm.backend.api.mappers.EmployeeMapper;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.schedulegenerator.models.Employee;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public List<Employee> getEmployeesForScheduling(){
        return employeeMapper.toDomainList(employeeRepository.findAll());
    }

}
