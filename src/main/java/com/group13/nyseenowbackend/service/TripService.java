package com.group13.nyseenowbackend.service;

import com.group13.nyseenowbackend.model.Trip;
import com.group13.nyseenowbackend.model.UserAccount;
import com.group13.nyseenowbackend.model.Attraction;
import com.group13.nyseenowbackend.repository.TripRepository;
import com.group13.nyseenowbackend.repository.UserAccountRepository;
import com.group13.nyseenowbackend.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private AttractionRepository attractionRepository;


    public List<Trip> getUserTrips(String username) {
        // Fetch user from the database

        // Fetch the trips associated with this user
        List<Trip> userTrips = tripRepository.findByUsername(username);

        // In a real world scenario, you might want to also fetch the attractions associated with each trip here

        return userTrips;
    }
}
