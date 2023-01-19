package com.task.sigma.controller;

import com.task.sigma.exceptions.NoContentException;
import com.task.sigma.model.Statistics;
import com.task.sigma.model.Transaction;
import com.task.sigma.service.CentralService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    private final long second = 60000;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CentralService service;

    @Test
    public void testNewTransactionCreated() {
        assertEquals(HttpStatus.CREATED,
                restTemplate.postForEntity("/transactions", new Transaction(200.00, Instant.now()), Object.class)
                        .getStatusCode());
    }

    @Test
    public void testNewTransactionNoContent() {
        assertEquals(HttpStatus.NO_CONTENT,
                restTemplate.postForEntity("/transactions", new Transaction(200.00,  Instant.now().minusMillis(second)), Object.class)
                        .getStatusCode());
    }

    @Test
    public void testNewTransactionUnprocessable() {
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
                restTemplate.postForEntity("/transactions", new Transaction(200.00, Instant.now().plusMillis(second)), Object.class)
                        .getStatusCode());
    }

    @Test
    public void deleteTest() throws NoContentException {
        service.createTransaction(new Transaction(10.00, Instant.now()));
        service.createTransaction(new Transaction(50.00, Instant.now()));
        service.createTransaction(new Transaction(80.00, Instant.now()));
        service.deleteAll();
        Statistics statistics = service.getStatistics();
        assertEquals(0L, (long) statistics.getCount());
    }
}
