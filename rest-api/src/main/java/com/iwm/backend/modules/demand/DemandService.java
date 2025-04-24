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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DemandService {


    /**
     * Retrieves and parses hourly demand forecasts from an external forecasting service.
     * <p>
     * This method sends a POST request to a forecasting API endpoint to obtain demand predictions
     * for a specified duration (currently hardcoded to 5 hours). The response is expected to be
     * a JSON array where each element contains a timestamp ({@code ds}) and a predicted value
     * ({@code yhat}). The method processes this response to generate a mapping of hour of the day
     * to scaled integer demand values.
     * <p>
     * Specifically:
     * <ul>
     *   <li>The API is called at {@code http://localhost:8000/forecastPeriod} with a JSON body
     *       containing {@code durationHours = 5}.</li>
     *   <li>The JSON response is deserialise into a list of maps representing forecast entries.</li>
     *   <li>Each entry's timestamp ({@code ds}) is parsed to extract the hour of the day (0–23).</li>
     *   <li>The predicted demand ({@code yhat}) is scaled down by a factor of 10 and rounded to
     *       the nearest integer.</li>
     *   <li>The resulting map contains hours as keys and scaled demand values as values.</li>
     * </ul>
     *
     * @param preset an integer indicating a demand preset; currently unused but may support future logic
     * @return a map of hour of day (0–23) to predicted demand (scaled and rounded)
     * @throws JsonProcessingException if there is an error parsing the JSON response
     * @see ObjectMapper
     * @see RestTemplate
     * @see LocalDateTime
     */
    public TreeMap<Integer,Integer> getWeeklyDemand(int preset) throws JsonProcessingException {

        String url = "http://localhost:8000/forecastPeriod";
        Map<String, Object> body = Map.of(
                "durationHours", 5
        );
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
            hourToYhat.put(hour, (int) (demand/10));
        }
        return  hourToYhat;
    }
}
