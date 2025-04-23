package com.iwm.schedule_engine.trial;

import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmloyeesReader {

    public static List<SchedEngEmpDTO> readEmployees() {
        InputStream stream  = EmloyeesReader.class.getResourceAsStream("/data/Employee_List.csv");
        List<SchedEngEmpDTO> employees;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            br.readLine();
            String line;
            employees = new ArrayList<SchedEngEmpDTO>();
            Random random = new Random();
            while ((line =br.readLine()) != null) {
                String[] split = line.split(",");
                SchedEngEmpDTO employee = new SchedEngEmpDTO();
                employee.setId(Integer.parseInt(split[0]));
                employee.setName(split[1]);
                employee.setRole(split[2]);
                employee.setHoursPreference(Double.parseDouble(split[3]));
                employee.setMaxHoursPerWeek(Double.parseDouble(split[4]));
                employee.setCost(random.nextDouble(10.00, 20.00));
                employees.add(employee);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

}
