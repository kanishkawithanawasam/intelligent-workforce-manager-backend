package com.iwm.schedule_engine.engine;

import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import com.iwm.schedule_engine.support.DemandReader;
import com.iwm.schedule_engine.support.EmloyeesReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@DisplayName("Fuzzy Genetic Schedules Generator Test")
class FGAScheduleGeneratorTest {


    @Test
    @DisplayName("Schedules Generator output test")
    void genSchedule() throws IOException {

        FGAScheduleGenerator generator = new FGAScheduleGenerator(
                EmloyeesReader.readEmployees(),LocalDate.now()
        );

        List<SchedEngShiftDTO> shifts = generator.genSchedule().getShifts();
        Assertions.assertNotNull(shifts, "Generated schedule must contain shifts list!");
        Assertions.assertFalse(shifts.isEmpty(), "Generated schedule's shift list must not be empty!");

        for(SchedEngShiftDTO shift : shifts) {
            Assertions.assertNotNull(shift.getDate(), "Shift date must not be null!");
            Assertions.assertNotNull(shift.getEmployee(), "Shift employee must not be null!");
        }
    }

    // Provides a test employee list for the generator
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


    // Provides the required demand map for the generator
    private static Map<LocalDate, Map<Integer,Integer>> getDemand(){

        // CSV path
        String csvFile = Objects.requireNonNull(
                DemandReader.class.getResource("/data/demand_schedule.csv")).getPath();
        csvFile = URLDecoder.decode(csvFile, StandardCharsets.UTF_8);
        String line;
        String csvSplitBy = ","; // CSV delimiter

        // Map to store demand schedule: Date -> (Hour -> Demand)
        Map<LocalDate, Map<Integer, Integer>> demand = new HashMap<>();

        // Reads csv file line by line and stores the data in a map
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            // Reads the header line and ignore it
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Splits the line by comma
                String[] data = line.split(csvSplitBy);

                // Extracts values from the CSV row
                String[] dateSplit = data[0].split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[0]),
                        Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2])) ;
                int hour = Integer.parseInt(data[1]);
                int demandValue = Integer.parseInt(data[2]);

                // If the date is not in the map, adds it
                demand.putIfAbsent(date, new HashMap<>());

                // Add the hour and demands value
                demand.get(date).put(hour, demandValue);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return demand;
    }
}