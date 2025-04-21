package com.iwm.backend.modules.day_schedules;

import com.iwm.backend.modules.shift.ShiftDTO;
import com.iwm.backend.modules.shift.ShiftDTOMapper;
import com.iwm.backend.modules.shift.ShiftService;
import com.iwm.schedule_engine.engine.HourlyScheduleOptimiser;
import com.iwm.schedule_engine.models.HourlyDemand;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@Service
public class DayScheduleService {


    private final ShiftService shiftService;

    public DayScheduleService(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    public List<ShiftDTO> getTodaySchedule(){
        return shiftService.findByDate(LocalDate.now());
    }

    public List<ShiftDTO> optimiseDaySchedule(List<ShiftDTO> dtos) {

        // Defines template hourly demand.
        TreeMap<Integer,Integer> hourlyDemand = new TreeMap<>();
        hourlyDemand.put(13,3);
        hourlyDemand.put(14,4);
        hourlyDemand.put(15,5);
        HourlyDemand demand = new HourlyDemand(LocalDate.now(), hourlyDemand);

        List<SchedEngShiftDTO> schedEngInput = ShiftDTOMapper.toSchedEngShiftDTOList(dtos);
        HourlyScheduleOptimiser optimiser = new HourlyScheduleOptimiser(schedEngInput, demand);
        return ShiftDTOMapper.toShiftDTOList(optimiser.getOptimisedRealTimeSchedule());
    }
}
