package com.iwm.backend.api.controllers;

import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.api.services.EmployeeService;
import com.iwm.backend.schedulegenerator.FuzzyGeneticScheduleGenerator;
import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.trial.DemandReader;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/schedules")
public class ScheduleController {

    private final EmployeeService employeeService;

    public ScheduleController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/generate")
    public WeeklySchedule getWeeklySchedule() throws IOException {
        List<Employee> employees = employeeService.getEmployeesForScheduling();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        FuzzyGeneticScheduleGenerator fuzzyGeneticScheduleGenerator =
                new FuzzyGeneticScheduleGenerator(employees, DemandReader.getDemand());
        return fuzzyGeneticScheduleGenerator.genSchedule();
    }
}
