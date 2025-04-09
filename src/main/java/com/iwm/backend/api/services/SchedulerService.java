package com.iwm.backend.api.services;

import com.iwm.backend.api.dtos.mappers.EmployeeDomainMapper;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.api.repository.WeeklyScheduleRepository;
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
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    public SchedulerService(EmployeeRepository employeeRepository, WeeklyScheduleRepository weeklyScheduleRepository) {
        this.employeeRepository = employeeRepository;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
    }

    public WeeklyScheduleEM getThisWeekSchedule() throws IOException {
        return weeklyScheduleRepository.findTopByOrderByCreateTimeDesc();
    }

    public WeeklySchedule generateWeeklySchedule() throws IOException {
        List<Employee> employees = EmployeeDomainMapper.toDomainList(employeeRepository.findAll());
        FuzzyGeneticScheduleGenerator generator =
                new FuzzyGeneticScheduleGenerator(employees,
                        DemandReader.getDemand());
        return generator.genSchedule();
    }

    public void saveWeeklySchedule(WeeklyScheduleEM weeklyScheduleEM) throws IOException {
        weeklyScheduleRepository.save(weeklyScheduleEM);
    }



}
