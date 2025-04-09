package com.iwm.backend.api.controllers;

import com.iwm.backend.api.controllers.exceptions.ScheduleNotFoundException;
import com.iwm.backend.api.dtos.WeeklyScheduleDTO;
import com.iwm.backend.api.dtos.mappers.WeeklyScheduleDTOMapper;
import com.iwm.backend.api.dtos.mappers.exceptions.WeeklyScheduleEMIsNullException;
import com.iwm.backend.api.models.WeeklyScheduleEM;
import com.iwm.backend.api.services.SchedulerService;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import org.springframework.web.bind.annotation.*;

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
        return WeeklyScheduleDTOMapper.toDTO(schedulerService.generateWeeklySchedule());
    }

    @GetMapping("/getschedule")
    public WeeklyScheduleDTO getWeeklySchedule() throws IOException {

        WeeklyScheduleDTO scheduleDTO;
        try {
            scheduleDTO = WeeklyScheduleDTOMapper.
                    toWeeklyScheduleDTO(schedulerService.getThisWeekSchedule());
        }catch (Exception e){
            if(e instanceof WeeklyScheduleEMIsNullException){
                throw  new ScheduleNotFoundException();
            }else{
                throw e;
            }
        }
        return scheduleDTO;
    }

    @PostMapping("/save")
    public void save(@RequestBody WeeklyScheduleDTO weeklyScheduleDTO) throws IOException {
        schedulerService.saveWeeklySchedule(WeeklyScheduleDTOMapper
                .toWeeklyScheduleEM(weeklyScheduleDTO));
    }



}
