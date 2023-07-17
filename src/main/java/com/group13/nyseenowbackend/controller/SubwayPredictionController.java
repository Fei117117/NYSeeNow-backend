package com.group13.nyseenowbackend.controller;

import com.group13.nyseenowbackend.model.PredictionRequest;
import com.group13.nyseenowbackend.service.SubwayPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubwayPredictionController {

    private final SubwayPredictionService predictionService;

    @Autowired
    public SubwayPredictionController(SubwayPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    // Handler method for POST requests to /subway/predict endpoint
    @PostMapping("/subway/predict")
    public Map<String, Integer> predictSubwayBusyness(@RequestBody PredictionRequest request) {
        // Extract longitude, latitude, and date from the request body
        double longitude = request.getGeometry().getCoordinates()[0];
        double latitude = request.getGeometry().getCoordinates()[1];
        String date = request.getDay();

        // Call the prediction service to predict subway busyness
        return predictionService.predictSubwayBusyness(longitude, latitude, date);
    }
}
