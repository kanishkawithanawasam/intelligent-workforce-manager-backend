package com.iwm.backend.api.services;

import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.dtos.mappers.EmployeeDomainMapper;
import com.iwm.backend.api.dtos.mappers.WeeklyScheduleDTOMapper;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.api.repository.WeeklyScheduleRepository;
import com.iwm.backend.schedulegenerator.FuzzyGeneticScheduleGenerator;
import com.iwm.backend.schedulegenerator.models.Employee;
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


    public WeeklyScheduleDTO getWeekScheduleFromWeek(LocalDate date) throws IOException {
        return WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(weeklyScheduleRepository.findByScheduleStartDate(date));
    }

    public List<LocalDate> getWeekStartDates() {

        List<LocalDate> weekStartDates = new ArrayList<>();

        // Determine the last schedule date
        LocalDate latestWeekStartDate = weeklyScheduleRepository.findLatestWeekStartDate();

        LocalDate dateCounter = LocalDate.now();
        if(latestWeekStartDate == null || latestWeekStartDate.isBefore(LocalDate.now())) {
            dateCounter = dateCounter.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            weekStartDates.add(dateCounter);
        }else{
            List<LocalDate> unavailableStartDates = weeklyScheduleRepository.findStartDatesBetween(dateCounter, latestWeekStartDate);
            for (int i = 0; i <8; i++) {
                dateCounter = dateCounter.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                if(!unavailableStartDates.contains(dateCounter)) {
                    weekStartDates.add(dateCounter);
                }
            }
        }
        return weekStartDates;
    }

    public WeeklyScheduleDTO generateWeeklySchedule(LocalDate startDate) throws IOException {
        List<Employee> employees = EmployeeDomainMapper.toDomainList(employeeRepository.findAll());
        FuzzyGeneticScheduleGenerator generator =
                new FuzzyGeneticScheduleGenerator(employees,
                        DemandReader.getDemand());
        WeeklyScheduleDTO dto = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(generator.genSchedule());
        dto.setScheduleStartDate(startDate);
        return dto;
    }

    public void saveWeeklySchedule(WeeklyScheduleDTO weeklyScheduleDTO) throws IOException {
        WeeklyScheduleEM saved = weeklyScheduleRepository.save(
                WeeklyScheduleDTOMapper.toWeeklyScheduleEM(weeklyScheduleDTO));
    }

}
