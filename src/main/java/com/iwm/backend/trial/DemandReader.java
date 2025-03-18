package com.iwm.backend.trial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DemandReader {
    public static Map<String,Map<Integer,Integer>> getDemand(){
        String csvFile = DemandReader.class.getResource("/data/demand_schedule.csv").getPath(); // Path to the CSV file
        csvFile = URLDecoder.decode(csvFile, StandardCharsets.UTF_8);
        String line;
        String csvSplitBy = ","; // CSV delimiter

        // Map to store demand schedule: Date -> (Hour -> Demand)
        Map<String, Map<Integer, Integer>> demand = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the header line and ignore it
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] data = line.split(csvSplitBy);

                // Extract values from the CSV row
                String date = data[0];
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

        // Print the demand schedule
        /*
        for (Map.Entry<String, Map<Integer, Integer>> entry : demand.entrySet()) {
            System.out.println("Date: " + entry.getKey());
            for (Map.Entry<Integer, Integer> hourEntry : entry.getValue().entrySet()) {
                System.out.println("  Hour: " + hourEntry.getKey() + " -> Demand: " + hourEntry.getValue());
            }
        }*/

        return demand;
    }
}
