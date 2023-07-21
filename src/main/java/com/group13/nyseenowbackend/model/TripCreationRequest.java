package com.group13.nyseenowbackend.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.time.ZoneId;
import java.util.Date;

public class TripCreationRequest {
    private String username;
    private Map<String, List<Map<String, Object>>> tripDetails;

    public String getUsername() {
        return username;
    }

    public Map<String, List<Map<String, Object>>> getTripDetails() {
        return tripDetails;
    }

    public LocalDate getEndDate() {
        String latestDate = tripDetails.keySet().stream().min(String::compareTo).orElse(null);
        if (latestDate != null) {
            return new Date(latestDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public LocalDate getStartDate() {
        String earliestDate= tripDetails.keySet().stream().max(String::compareTo).orElse(null);
        if (earliestDate != null) {
            return new Date(earliestDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public int getNumberOfAttractions() {
        return tripDetails.values().stream().mapToInt(List::size).sum();
    }
}
