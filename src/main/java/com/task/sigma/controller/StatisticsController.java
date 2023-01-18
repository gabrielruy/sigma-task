package com.task.sigma.controller;

import com.task.sigma.service.CentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private CentralService service;

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getStatistics() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getStatistics());
    }
}
