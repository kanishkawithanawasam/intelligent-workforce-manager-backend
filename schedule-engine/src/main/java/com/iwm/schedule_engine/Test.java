package com.iwm.schedule_engine;

import com.iwm.schedule_engine.engine.FGAScheduleGenerator;
import com.iwm.schedule_engine.models.HourlyDemand;
import com.iwm.schedule_engine.models.dtos.SchedEngWeklySchedDTO;
import com.iwm.schedule_engine.models.dtos.interfaces.IScheduleEngineShiftDTO;
import com.iwm.schedule_engine.trial.DemandReader;
import com.iwm.schedule_engine.trial.EmloyeesReader;

import java.time.LocalDate;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) {


        FGAScheduleGenerator generator = new FGAScheduleGenerator(
                EmloyeesReader.readEmployees(),
                DemandReader.getDemand()
        );
        SchedEngWeklySchedDTO weeklySchedule = null;

        try {
            weeklySchedule = generator.genSchedule();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        TreeMap<Integer,Integer> demandMap = new TreeMap<>();
        demandMap.put(13,4);
        demandMap.put(14,4);
        LocalDate date  = LocalDate.of(2025,3,21);


        HourlyDemand demand = new HourlyDemand(date,demandMap);


        for(IScheduleEngineShiftDTO shift : weeklySchedule.getShifts()) {
            if(shift.getDate().equals(demand.getDate())) {
                System.out.println(shift);
            }
        }

        System.out.println("\n Optimised Schedule: \n");


        /*
        HourlyScheduleOptimiser optimiser =
                new HourlyScheduleOptimiser(WeeklyScheduleMapper.toWeeklySchedule(weeklySchedule), demand);
        List<Shift> shifts = optimiser.getOptimisedRealTimeSchedule().getShifts();

        for (Shift shift : shifts) {
            System.out.println(shift);
        }*/
    }
}
