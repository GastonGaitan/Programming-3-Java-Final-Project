package com.dh.homebanking.model;

import java.util.Date;

public class Transaction {
    private Integer transactionId;
    private Integer amount;
    private Date operationTime;

    public Transaction(Integer amount) {
        this.amount = amount;
    }

    public Transaction(Integer transactionId, Integer amount, Date operationTime) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.operationTime = operationTime;
    }

    // Getters y Setters

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", operationTime=" + operationTime +
                '}';
    }
}

