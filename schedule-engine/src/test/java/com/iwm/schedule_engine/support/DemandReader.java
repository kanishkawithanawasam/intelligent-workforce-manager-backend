package com.iwm.schedule_engine.support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DemandReader {
    public static Map<LocalDate,Map<Integer,Integer>> getDemand(){
        String csvFile = Objects.requireNonNull(
                DemandReader.class.getResource("/data/demand_schedule.csv")).getPath(); // Path to the CSV file
        csvFile = URLDecoder.decode(csvFile, StandardCharsets.UTF_8);
        String line;
        String csvSplitBy = ","; // CSV delimiter

        // Map to store demand schedule: Date -> (Hour -> Demand)
        Map<LocalDate, Map<Integer, Integer>> demand = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the header line and ignore it
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] data = line.split(csvSplitBy);

                // Extract values from the CSV row
                String[] dateSplit = data[0].split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateSplit[0]),
                        Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2])) ;
                int hour = Integer.parseInt(data[1]);
                int demandValue = Integer.parseInt(data[2]);

                // If the date is not in the map, add it
                demand.putIfAbsent(date, new HashMap<>());

                // Add the hour and demand value
                demand.get(date).put(hour, demandValue);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return demand;
    }
}
