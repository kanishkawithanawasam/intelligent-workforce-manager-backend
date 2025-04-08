package com.iwm.backend.api.controllers;

import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.mappers.WeeklyScheduleDTOMapper;
import com.iwm.backend.api.services.SchedulerService;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/schedules")
public class ScheduleController {
    private final SchedulerService schedulerService;

    private WeeklySchedule weeklySchedule;

    public ScheduleController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping("/generate")
    public WeeklyScheduleDTO generateWeeklySchedule() throws IOException {
        WeeklyScheduleDTO scheduleDTO =
                WeeklyScheduleDTOMapper.toDTO(schedulerService.generateWeeklySchedule());
        return scheduleDTO;
    }

    @GetMapping("/getschedule")
    public WeeklyScheduleDTO getWeeklySchedule() throws IOException {
        return WeeklyScheduleDTOMapper.toDTO(schedulerService.getWeeklySchedule());
    }

}
