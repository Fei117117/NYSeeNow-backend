package com.group13.nyseenowbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group13.nyseenowbackend.model.Trip;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByUsername(String username);
}
