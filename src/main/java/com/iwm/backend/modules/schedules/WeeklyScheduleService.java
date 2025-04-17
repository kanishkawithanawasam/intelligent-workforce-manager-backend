package com.iwm.backend.modules.schedules;

import com.iwm.backend.modules.employee.EmployeeService;
import com.iwm.schedule_engine.engine.FuzzGenSchedGenerator;
import com.iwm.schedule_engine.models.dots.SchedEngEmpDTO;
import com.iwm.schedule_engine.models.dots.SchedEngWeklySchedDTO;
import com.iwm.schedule_engine.trial.DemandReader;
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
    private final EmployeeService employeeService;

    public WeeklyScheduleService(WeeklyScheduleRepository weeklyScheduleRepository,
                                 EmployeeService employeeService) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.employeeService = employeeService;
    }


    public WeeklyScheduleDTO getWeekScheduleFromDate(LocalDate startDate){
        WeeklyScheduleDTO weeklyScheduleDTO = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(
                weeklyScheduleRepository.findByScheduleStartDate(startDate));

        // Return an empty schedule DTO is the weekly schedule is null.
        if (weeklyScheduleDTO == null) {
            weeklyScheduleDTO = new WeeklyScheduleDTO(LocalDate.now(),new ArrayList<>());
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


        List<SchedEngEmpDTO> employees = employeeService.generateEmployeeForSchedule();


        SchedEngWeklySchedDTO schedule =
                new FuzzGenSchedGenerator(employees,
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
        weeklyScheduleRepository.save(
                WeeklyScheduleDTOMapper.toWeeklyScheduleEM(weeklyScheduleDTO));
    }

}
