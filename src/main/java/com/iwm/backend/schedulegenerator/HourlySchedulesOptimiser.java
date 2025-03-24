package com.iwm.backend.schedulegenerator;

import com.iwm.backend.schedulegenerator.configurations.FGAConfigs;
import com.iwm.backend.schedulegenerator.models.Shift;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HourlySchedulesOptimiser {

    private List<Shift> todaySchedule = new ArrayList<>();
    private int[] hourlyDemand = new int[]{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 4, 4, 4, 4, 3, 3, 4, 4, 4, 4, 3, 3};

    public List<Shift> getTodaySchedule() {
        return todaySchedule;
    }

    public static void main(String[] args) {
        // Setting configurations
        FGAConfigs fgaConfigs = new FGAConfigs();
        fgaConfigs.setNumberOfIterations(100);
        fgaConfigs.setPopulationSize(100);

        // Creating a new schedule generator
        FuzzyGeneticScheduleGenerator generator = new FuzzyGeneticScheduleGenerator(fgaConfigs);
        HourlySchedulesOptimiser optimiser = new HourlySchedulesOptimiser();

        // Generating shifts
        List<Shift> shifts = generator.genSchedule().getShifts();

        for (Shift shift : shifts) {
            if(Objects.equals(shift.getDate(), "2025-03-22")){
                optimiser.todaySchedule.add(shift);
            }
        }

        for (Shift shift : optimiser.getTodaySchedule()) {
            System.out.println(shift);
        }

    }

}
