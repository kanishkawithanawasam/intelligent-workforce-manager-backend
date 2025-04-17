package com.iwm.backend.modules.schedules;

import com.iwm.backend.modules.employee.EmployeeForScheduleEngine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeeklyScheduleService {
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    public WeeklyScheduleService(WeeklyScheduleRepository weeklyScheduleRepository) {
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

    public WeeklyScheduleDTO generateWeeklySchedule(WeeklyScheduleRequestDTO requestDTO) throws IOException {


        List<EmployeeForScheduleEngine> employees = EmployeeDomainMapper.toDomainList(employeeRepository.findAll());
        WeeklySchedule schedule =
                new FuzzyGeneticScheduleGenerator(employees,
                        DemandReader.getDemand()).genSchedule();

        if (schedule == null) {
            throw new WeeklyScheduleNotFoundException();
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
