package com.task.sigma.controller;

import com.task.sigma.exceptions.NoContentException;
import com.task.sigma.model.Statistics;
import com.task.sigma.model.Transaction;
import com.task.sigma.service.CentralService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

    @Autowired
    private CentralService service;

    @Test
    public void testGetStatistics() throws NoContentException {
        service.deleteAll();
        service.createTransaction(new Transaction(15.00, Instant.now()));
        service.createTransaction(new Transaction(45.00,Instant.now()));
        service.createTransaction(new Transaction(75.00,Instant.now()));
        Statistics statistics = service.getStatistics();


        assertEquals("135.00", statistics.getSum().toString());
        assertEquals("45.00", statistics.getAvg().toString());
        assertEquals("75.00", statistics.getMax().toString());
        assertEquals("15.00", statistics.getMin().toString());
    }
}
