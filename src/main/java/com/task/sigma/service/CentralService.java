package com.task.sigma.service;

import com.task.sigma.exceptions.NoContentException;
import com.task.sigma.exceptions.UnprocessableEntityException;
import com.task.sigma.model.Statistics;
import com.task.sigma.model.Transaction;

public interface CentralService {

    void createTransaction(Transaction transaction) throws UnprocessableEntityException, NoContentException;

    void deleteAll();

    Statistics getStatistics();
}
