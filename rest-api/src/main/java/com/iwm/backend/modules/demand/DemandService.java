package com.iwm.backend.modules.demand;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Service responsible for handling operations related to demand forecasting and processing.
 * <p>
 * This service typically interacts with external forecasting APIs and internal systems
 * to retrieve, transform, and provide demand data required for schedule optimization and
 * workforce planning. It includes functionality to:
 * <ul>
 *   <li>Request short-term demand forecasts from external services</li>
 *   <li>Parse and normalize raw forecast data into usable formats</li>
 *   <li>Provide hourly demand mappings used by scheduling algorithms</li>
 * </ul>
 * Designed to support dynamic scheduling and real-time optimization by supplying
 * up-to-date demand information on an hourly basis.
 *
 * <p>Typical usage might involve:
 * <pre>
 * {@code
 * Map<Integer, Integer> hourlyDemand = demandService.getWeeklyDemand(1);
 * }
 * </pre>
 *
 * @author Kanishka Withanawasam
 * @version 1.0
 */

@Service
public class DemandService {


    /**
     * Retrieves hourly demand forecasts from an external forecasting API and maps them to integer values.
     * <p>
     * Sends a POST request to the Prophet-based forecasting service with a configurable forecast period.
     * Parses the response to extract demand predictions for each hour and scales the predicted values
     * using the provided preset factor.
     * <p>
     * The response is expected to be a list of JSON objects containing:
     * <ul>
     *   <li>{@code ds} - a timestamp in ISO-8601 format (e.g., "2025-04-24T14:00:00")</li>
     *   <li>{@code yhat} - the predicted demand value</li>
     * </ul>
     *
     * The method returns a {@link TreeMap} where each key is the hour of the day (0–23) and the value
     * is the scaled integer demand.
     *
     * @param preset a scaling factor used to reduce the raw predicted demand values (e.g., divide yhat by this value)
     * @param period the number of hours ahead to forecast (e.g., 24 for a daily forecast)
     * @return a {@link TreeMap} mapping hours (0–23) to scaled demand values
     * @throws JsonProcessingException if the JSON response from the forecast API cannot be parsed
     * @see ObjectMapper
     * @see RestTemplate
     */
    public TreeMap<Integer,Integer> getWeeklyDemand(int preset, int period) throws JsonProcessingException {

        String url = "http://127.0.0.1:4000/api/v1/forecast/prophet";
        Map<String, Object> body = Map.of(
                "forecastPeriod", period
        );
        System.out.println(body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);


        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> forecastList = mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );

        // Extract and parses hour -> yhat
        TreeMap<Integer, Integer> hourToYhat = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (Map<String, Object> forecast : forecastList) {
            String ds = (String) forecast.get("ds");
            double demand = ((Number) forecast.get("yhat")).doubleValue();
            int hour = LocalDateTime.parse(ds, formatter).getHour();
            hourToYhat.put(hour, (int) (demand/preset));
        }
        return  hourToYhat;
    }
}
