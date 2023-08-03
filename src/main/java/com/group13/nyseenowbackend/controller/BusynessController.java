package com.group13.nyseenowbackend.controller;

/**
 * @Name:NYSeeNow-backend
 * @Description:
 * @Author:Weitao Li
 * @Date:29/07/2023
 */

import com.group13.nyseenowbackend.dto.BusynessDTO;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group13.nyseenowbackend.service.BusynessService;

@RestController
@Data
@RequestMapping(value = "/busyness", produces = "application/json")
public class BusynessController {
    private String name;
    private String day;
    private String hour;
    private final BusynessService busynessService;

    @Autowired
    public BusynessController(BusynessService busynessService) {
        this.busynessService = busynessService;
    }

    @ResponseBody
    @GetMapping("/get")
    public ResponseEntity<BusynessDTO> getBusynessByVenueNameDayAndHour(@Valid @RequestBody BusynessDTO busynessDTO) {
        return handleBusynessRequest(busynessDTO.getName(), busynessDTO.getDay(), busynessDTO.getHour());
    }

    @PostMapping("/create")
    public ResponseEntity<BusynessDTO> postBusynessByVenueNameDayAndHour(
            @RequestBody BusynessDTO busynessDTO) {
        return handleBusynessRequest(busynessDTO.getName(), busynessDTO.getDay(), busynessDTO.getHour());
    }

    private ResponseEntity<BusynessDTO> handleBusynessRequest(String name, String day, String hour) {
        BusynessDTO busyness = busynessService.getBusynessByVenueNameDayAndHour(name, day, hour);
        if (busyness != null) {
            return ResponseEntity.ok(busyness);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}