package com.group13.nyseenowbackend.controller;

import com.group13.nyseenowbackend.model.Attraction;
import com.group13.nyseenowbackend.model.Trip;
import com.group13.nyseenowbackend.model.TripAttraction;
import com.group13.nyseenowbackend.model.TripCreationRequest;
import com.group13.nyseenowbackend.repository.AttractionRepository;
import com.group13.nyseenowbackend.repository.TripAttractionRepository;
import com.group13.nyseenowbackend.repository.TripRepository;
import com.group13.nyseenowbackend.service.TripService;
import com.group13.nyseenowbackend.dto.TripAttractionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private TripAttractionRepository tripAttractionRepository;

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/create")
    public Trip createTrip(@RequestBody TripCreationRequest request) {
        // Create a Trip
        Trip trip = new Trip();
        trip.setUsername(request.getUsername());
        trip.setStart_date(request.getStartDate());
        trip.setEnd_date(request.getEndDate());
        trip.setNumber_of_attractions(request.getNumberOfAttractions());
        // Save trip to get generated ID
        trip = tripRepository.save(trip);

        // Define the DateTimeFormatter pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);

        // Iterate over tripDetails and create TripAttractions
        for (Map.Entry<String, List<Map<String, Object>>> entry : request.getTripDetails().entrySet()) {
            String dateString = entry.getKey();
            List<Map<String, Object>> attractions = entry.getValue();

            for (Map<String, Object> attraction : attractions) {
                Integer attractionId = (Integer) attraction.get("attraction_id");

                Optional<Attraction> attractionEntityOptional = attractionRepository.findById(attractionId);
                Trip finalTrip = trip;
                attractionEntityOptional.ifPresent(attractionEntity -> {

                    String visitTime = (String) attraction.get("visitTime");

                    // Parse the date using the formatter
                    LocalDate date = LocalDate.parse(dateString, formatter);

                    List<Integer> dayBusyness = (List<Integer>) attraction.get("day_busyness");
                    String predictionString = dayBusyness.toString();

                    // Save each TripAttraction
                    TripAttraction tripAttraction = new TripAttraction();
                    tripAttraction.setTripId(finalTrip.getTripId());
                    tripAttraction.setAttractionId(attractionEntity.getAttractionId());
                    tripAttraction.setDate(date);
                    tripAttraction.setTime(LocalTime.parse(visitTime));
                    tripAttraction.setPrediction(predictionString);
                    tripAttractionRepository.save(tripAttraction);

                });
            }
        }

        return trip;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Map<Trip, List<TripAttractionDTO>>> getTripsByUsername(@PathVariable String username) {
        // Fetch trips by username
        List<Trip> trips = tripRepository.findByUsername(username);



        // If no trips were found, return NO_CONTENT
        if (trips.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Prepare the map to be returned
        Map<Trip, List<TripAttractionDTO>> tripsWithAttractions = new HashMap<>();

        // For each trip, fetch trip attractions and add them to the map
        for (Trip trip : trips) {
            List<TripAttraction> tripAttractions = tripAttractionRepository.findByTripId(trip.getTripId());
            List<TripAttractionDTO> tripAttractionDTOs = new ArrayList<>();

            for (TripAttraction tripAttraction : tripAttractions) {
                String predictionString = tripAttraction.getPrediction();
                List<Integer> dayBusyness = Arrays.asList(predictionString.substring(1, predictionString.length() - 1).split(",")).stream().map(String::trim).map(Integer::parseInt).collect(Collectors.toList());

                TripAttractionDTO dto = new TripAttractionDTO();
                dto.setTripId(tripAttraction.getTripId());
                dto.setAttractionId(tripAttraction.getAttractionId());
                dto.setDate(tripAttraction.getDate());
                dto.setTime(tripAttraction.getTime());
                dto.setDayBusyness(dayBusyness);

                tripAttractionDTOs.add(dto);
            }

            tripsWithAttractions.put(trip, tripAttractionDTOs);
        }

        return new ResponseEntity<>(tripsWithAttractions, HttpStatus.OK);
    }
}