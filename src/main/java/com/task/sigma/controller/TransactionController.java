package com.task.sigma.controller;

import com.task.sigma.exceptions.NoContentException;
import com.task.sigma.exceptions.UnprocessableEntityException;
import com.task.sigma.model.Transaction;
import com.task.sigma.service.CentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    private CentralService service;

    @PostMapping("/transactions")
    public ResponseEntity<?> newTransaction(@Valid @RequestBody Transaction transaction) throws UnprocessableEntityException, NoContentException {
        service.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
