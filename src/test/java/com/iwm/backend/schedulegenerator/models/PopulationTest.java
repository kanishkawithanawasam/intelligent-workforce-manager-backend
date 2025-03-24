package com.iwm.backend.schedulegenerator.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    @Test
    void getPopulation() {
        assertTrue(verifyPopulation());
    }


    boolean verifyPopulation() {
        Population population =  new Population(3,
                4,8,10);

        Map<Employee,Map<String, Integer>> empDateMap = new HashMap<>();
        for (WeeklySchedule weeklySchedule : population.getPopulation()){
            for(Shift shift : weeklySchedule.getShifts()){
                Employee employee = shift.getEmployee();
                if(empDateMap.containsKey(employee)){
                    if(empDateMap.get(employee).containsKey(shift.getDate())){
                        empDateMap.get(employee).put(shift.getDate(), empDateMap.get(employee).get(shift.getDate())+1);
                    }else {
                        empDateMap.get(employee).put(shift.getDate(), 1);
                    }
                }else{
                    Map<String, Integer> map = new HashMap<>();
                    map.put(shift.getDate(), 1);
                    empDateMap.put(employee, map);
                }
            }
        }

        System.out.println(empDateMap);

        for (Employee employee : empDateMap.keySet()) {
            System.out.println(employee.getName());
            for (String date : empDateMap.get(employee).keySet()) {
                System.out.println(date);
                if (empDateMap.get(employee).get(date) > 1) {
                    return false;
                }
            }
        }

        return true;
    }
}