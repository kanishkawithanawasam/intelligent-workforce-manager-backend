package com.iwm.backend.modules.schedules;

import com.iwm.backend.modules.employee.EmployeeService;
import com.iwm.schedule_engine.engine.FGAScheduleGenerator;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import com.iwm.schedule_engine.models.dtos.SchedEngWeklySchedDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing weekly employee schedules.
 * Provides functionality for generating, retrieving and saving weekly schedules.
 */
@Service
public class WeeklyScheduleService {
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final EmployeeService employeeService;

    public WeeklyScheduleService(WeeklyScheduleRepository weeklyScheduleRepository,
                                 EmployeeService employeeService) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.employeeService = employeeService;
    }


    /**
     * Retrieves a weekly schedule for a given start date.
     *
     * @param startDate The start date of the week to retrieve the schedule for
     * @return WeeklyScheduleDTO containing the schedule, or an empty schedule if none exists
     */
    public WeeklyScheduleDTO getWeekScheduleFromDate(LocalDate startDate){
        WeeklyScheduleDTO weeklyScheduleDTO = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(
                weeklyScheduleRepository.findByScheduleStartDate(startDate));

        // Return an empty schedule DTO is the weekly schedule is null.
        if (weeklyScheduleDTO == null) {
            weeklyScheduleDTO = new WeeklyScheduleDTO(LocalDate.now(),new ArrayList<>());
        }

        return weeklyScheduleDTO;
    }

    /**
     * Gets a list of available week start dates for schedule generation.
     * Returns the next 8 Mondays that don't have existing schedules.
     *
     * @return List of available start dates
     */
    public List<LocalDate> getWeekStartDates() {

        List<LocalDate> weekStartDates = new ArrayList<>();


        // Find the most recent schedule's start date from the database
        LocalDate latestWeekStartDate = weeklyScheduleRepository.findLatestWeekStartDate();
        if (latestWeekStartDate == null) {
            latestWeekStartDate = LocalDate.now();
        }


        LocalDate dateCounter = LocalDate.now();
        // Get all dates that already have schedules to exclude them from available dates
        List<LocalDate> unavailableStartDates = weeklyScheduleRepository.findStartDatesBetween(dateCounter, latestWeekStartDate);
        for (int i = 0; i <8; i++) {
            dateCounter = dateCounter.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            if(!unavailableStartDates.contains(dateCounter)) {
                weekStartDates.add(dateCounter);
            }
        }

        return weekStartDates;
    }

    /**
     * Generates a new weekly schedule based on the provided request.
     * Uses FGA algorithm to generate optimal schedule for employees.
     *
     * @param requestDTO Request containing schedule generation parameters
     * @return Generated weekly schedule
     * @throws IOException If there's an error during schedule generation
     */
    public WeeklyScheduleDTO generateWeeklySchedule(WeeklyScheduleRequestDTO requestDTO) throws IOException {


        List<SchedEngEmpDTO> employees = employeeService.generateEmployeeForSchedule();


        SchedEngWeklySchedDTO schedule =
                new FGAScheduleGenerator(employees,
                       requestDTO.getStartDate()).genSchedule();

        for (SchedEngShiftDTO shiftDTO : schedule.getShifts()) {
            System.out.println(shiftDTO.getDate());
        }
        WeeklyScheduleDTO dto = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(schedule);
        assert dto != null;
        dto.setScheduleStartDate(requestDTO.getStartDate());
        return dto;
    }


    /**
     * Persists a weekly schedule to the database.
     *
     * @param weeklyScheduleDTO The schedule to be saved
     */
    public void saveWeeklySchedule(WeeklyScheduleDTO weeklyScheduleDTO){
        weeklyScheduleRepository.save(
                WeeklyScheduleDTOMapper.toWeeklyScheduleEM(weeklyScheduleDTO));
    }

}
