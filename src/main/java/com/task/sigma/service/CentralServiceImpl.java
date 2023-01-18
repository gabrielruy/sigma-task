package com.task.sigma.service;

import com.task.sigma.exceptions.NoContentException;
import com.task.sigma.exceptions.UnprocessableEntityException;
import com.task.sigma.model.Statistics;
import com.task.sigma.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class CentralServiceImpl implements CentralService {

    private static final List<Transaction> transactionList = new ArrayList<>();
    private static final Integer milliSeconds = 60000;
    private final Object lock = new Object();
    private Statistics statistics;

    @Override
    public void createTransaction(Transaction transaction) throws UnprocessableEntityException, NoContentException {
        if (validateTime(transaction)) {
            throw new NoContentException();
        }
        synchronized (lock) {
            transactionList.add(transaction);
            updateStatistics();
        }
    }

    @Override
    public void deleteAll() {
        transactionList.clear();
        clearStatistics();
    }

    @Override
    public Statistics getStatistics() {
        if (statistics == null) {
            statistics = new Statistics();
        } else {
            removeOldTransactions();
            updateStatistics();
        }
        return statistics;
    }

    private void updateStatistics() {
        if (transactionList.isEmpty()) {
            statistics = new Statistics();
        } else {
            DoubleSummaryStatistics stat = transactionList.stream().mapToDouble(Transaction::getAmount)
                    .summaryStatistics();
            statistics = new Statistics(BigDecimal.valueOf(stat.getSum()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stat.getAverage()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stat.getMax()).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(stat.getMin()).setScale(2, RoundingMode.HALF_UP),
                    stat.getCount());
        }
    }

    private void removeOldTransactions() {
        transactionList.removeIf(this::validateTime);
    }

    private boolean validateTime(Transaction transaction)  {
        if (Instant.now().toEpochMilli() < transaction.getTimestamp().toEpochMilli()) {
            throw new UnprocessableEntityException();
        }

        return (Instant.now().toEpochMilli() - transaction.getTimestamp().toEpochMilli()) >= milliSeconds
                || Instant.now().toEpochMilli() < transaction.getTimestamp().toEpochMilli();
    }

    private void clearStatistics() {
        statistics = new Statistics();
    }
}
