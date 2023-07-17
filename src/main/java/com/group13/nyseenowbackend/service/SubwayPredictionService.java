package com.group13.nyseenowbackend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@Service
public class SubwayPredictionService {

    public Map<String, Integer> predictSubwayBusyness(double longitude, double latitude, String date) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Use a Map for geometry object
        Map<String, double[]> geometry = new HashMap<>();
        geometry.put("coordinates", new double[]{longitude, latitude});

        // Use a Map to set the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("geometry", geometry);
        requestBody.put("day", date);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                "http://localhost:5001/predict",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        return responseEntity.getBody();
    }
}
