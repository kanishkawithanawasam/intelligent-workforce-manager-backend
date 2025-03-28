package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.models.*;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) {
        FuzzyGeneticScheduleGenerator generator = new FuzzyGeneticScheduleGenerator();
        WeeklySchedule weeklySchedule = generator.genSchedule();

        TreeMap<Integer,Integer> demandMap = new TreeMap<>();
        demandMap.put(13,0);
        demandMap.put(15,0);
        LocalDate date  = LocalDate.of(2025,3,21);


        HourlyDemand demand = new HourlyDemand(date,demandMap);

        /*
        demand.setDate();
        demand.setStartTimeInMinutes(demandMap.firstKey()*60);
        demand.setEndTimeInMinutes(demandMap.lastKey()*60);
        demand.setEndTimeInMinutes(15*60);*/

        for(Shift shift : weeklySchedule.getShifts()) {
            if(shift.getDate().equals(demand.getDate())) {
                System.out.println(shift);
            }
        }

        System.out.println("\n Optimised Schedule: \n");


        HourlyScheduleOptimiser optimiser =
                new HourlyScheduleOptimiser(weeklySchedule, demand);
        List<Shift> shifts = optimiser.getOptimisedRealTimeSchedule().getShifts();

        for (Shift shift : shifts) {
            System.out.println(shift);
        }
    }
}
