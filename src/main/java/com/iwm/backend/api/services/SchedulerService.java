package com.iwm.backend.api.services;


import com.iwm.backend.api.mappers.EmployeeDomainMapper;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.schedulegenerator.FuzzyGeneticScheduleGenerator;
import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.trial.DemandReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SchedulerService {
    private final EmployeeRepository employeeRepository;

    public SchedulerService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public WeeklySchedule getThisWeekSchedule() throws IOException {
        return null;
    }

    public WeeklySchedule generateWeeklySchedule() throws IOException {
        List<Employee> employees = EmployeeDomainMapper.toDomainList(employeeRepository.findAll());
        FuzzyGeneticScheduleGenerator generator =
                new FuzzyGeneticScheduleGenerator(employees,
                        DemandReader.getDemand());
        return generator.genSchedule();
    }
}
