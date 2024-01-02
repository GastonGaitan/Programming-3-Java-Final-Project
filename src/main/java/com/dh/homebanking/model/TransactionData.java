package com.dh.homebanking.model;

import java.time.LocalDateTime;
import java.util.Date;
// This object is used to receive the joined data from AccountTransaction and Transaction tables.
public class TransactionData {
    private Integer transactionId;
    private Integer amount;
    private Date operationTime;
    private Integer originAccount;
    private Integer destinyAccount;

    // Constructor
    public TransactionData(Integer transactionId, Integer amount, Date operationTime, Integer originAccount, Integer destinyAccount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.operationTime = operationTime;
        this.originAccount = originAccount;
        this.destinyAccount = destinyAccount;
    }

    // Getters and setters
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
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

    public Integer getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(Integer originAccount) {
        this.originAccount = originAccount;
    }

    public Integer getDestinyAccount() {
        return destinyAccount;
    }

    public void setDestinyAccount(Integer destinyAccount) {
        this.destinyAccount = destinyAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", operationTime=" + operationTime +
                ", originAccount=" + originAccount +
                ", destinyAccount=" + destinyAccount +
                '}';
    }
}

