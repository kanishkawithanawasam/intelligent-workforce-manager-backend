package com.iwm.backend.schedulegenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwm.backend.schedulegenerator.models.Shift;
import com.iwm.backend.schedulegenerator.models.WeeklySchedule;
import com.iwm.backend.schedulegenerator.models.*;
import com.iwm.backend.trial.DemandReader;
import com.iwm.backend.trial.EmloyeesReader;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) throws JsonProcessingException {


        FuzzyGeneticScheduleGenerator generator = new FuzzyGeneticScheduleGenerator(
                EmloyeesReader.readEmployees(),
                DemandReader.getDemand()
        );
        WeeklySchedule weeklySchedule = null;
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
