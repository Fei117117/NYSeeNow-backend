package com.group13.nyseenowbackend.controller;

import com.group13.nyseenowbackend.service.ItineraryPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attraction")
public class AttractionPredictionController {

    private final ItineraryPredictionService predictionService;

    @Autowired
    public AttractionPredictionController(ItineraryPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public Map<String, List<Map<String, Map<String, List<Integer>>>>> predictSubwayBusyness(@RequestBody Map<String, Object> attractionData) {
        return predictionService.predictSubwayBusyness(attractionData);
    }
}

