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
import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping("/schedules")
public class ScheduleController {
    private final SchedulerService schedulerService;


    public ScheduleController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }


    @GetMapping("/generate")
    public WeeklyScheduleDTO generateWeeklySchedule(@RequestBody LocalDate startDate) throws IOException {
        return  this.schedulerService.generateWeeklySchedule();
    }

    @GetMapping("/get-available-start-dates")
    public List<LocalDate> getAvailableDate(){
        return schedulerService.getWeekStartDates();
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
        schedulerService.saveWeeklySchedule(weeklyScheduleDTO);
    }



}
