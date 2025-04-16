package com.iwm.backend.api.schedule;

import com.iwm.backend.api.controllers.exceptions.ScheduleNotFoundException;
import com.iwm.backend.api.dtos.ScheduleRequestDTO;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.dtos.mappers.EmployeeDomainMapper;
import com.iwm.backend.api.dtos.mappers.WeeklyScheduleDTOMapper;
import com.iwm.backend.api.employee.EmployeeRepository;
import com.iwm.backend.schedulegenerator.FuzzyGeneticScheduleGenerator;
import com.iwm.backend.schedulegenerator.models.Employee;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.trial.DemandReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerService {
    private final EmployeeRepository employeeRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    public SchedulerService(EmployeeRepository employeeRepository, WeeklyScheduleRepository weeklyScheduleRepository) {
        this.employeeRepository = employeeRepository;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
    }


    public WeeklyScheduleDTO getWeekScheduleFromDate(LocalDate startDate){
        WeeklyScheduleDTO weeklyScheduleDTO = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(
                weeklyScheduleRepository.findByScheduleStartDate(startDate));

        // Return an empty schedule DTO is the weekly schedule is null.
        if (weeklyScheduleDTO == null) {
            weeklyScheduleDTO = new WeeklyScheduleDTO(LocalDate.now(),new ArrayList<>(),0.0);
        }

        return weeklyScheduleDTO;
    }

    public List<LocalDate> getWeekStartDates() {

        List<LocalDate> weekStartDates = new ArrayList<>();


        // Determine the last schedule date
        LocalDate latestWeekStartDate = weeklyScheduleRepository.findLatestWeekStartDate();
        if (latestWeekStartDate == null) {
            latestWeekStartDate = LocalDate.now();
        }


        LocalDate dateCounter = LocalDate.now();
        List<LocalDate> unavailableStartDates = weeklyScheduleRepository.findStartDatesBetween(dateCounter, latestWeekStartDate);
        for (int i = 0; i <8; i++) {
            dateCounter = dateCounter.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            if(!unavailableStartDates.contains(dateCounter)) {
                weekStartDates.add(dateCounter);
            }
        }

        return weekStartDates;
    }

    public WeeklyScheduleDTO generateWeeklySchedule(ScheduleRequestDTO requestDTO) throws IOException {
        List<Employee> employees = EmployeeDomainMapper.toDomainList(employeeRepository.findAll());
        WeeklySchedule schedule =
                new FuzzyGeneticScheduleGenerator(employees,
                        DemandReader.getDemand()).genSchedule();

        if (schedule == null) {
            throw new ScheduleNotFoundException();
        }
        WeeklyScheduleDTO dto = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(schedule);
        assert dto != null;
        dto.setScheduleStartDate(requestDTO.getStartDate());
        return dto;
    }


    public void saveWeeklySchedule(WeeklyScheduleDTO weeklyScheduleDTO){
        WeeklyScheduleEM saved = weeklyScheduleRepository.save(
                WeeklyScheduleDTOMapper.toWeeklyScheduleEM(weeklyScheduleDTO));
    }

}
