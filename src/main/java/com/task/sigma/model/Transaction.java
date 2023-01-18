package com.task.sigma.model;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class Transaction {

    @NotNull(message = "Transaction amount cannot be null.")
    private Double amount;
    @NotNull(message = "Transaction time cannot be null.")
    private Instant timestamp;


    public Transaction(Double amount, Instant timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }


    public Instant getTimestamp() {
        return timestamp;
    }
}
