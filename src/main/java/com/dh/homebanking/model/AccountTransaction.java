package com.dh.homebanking.model;

public class AccountTransaction {
    private Integer transactionId;
    private Integer originAccount;
    private Integer destinyAccount;


    public AccountTransaction(int transactionId, int originAccount, int destinyAccount) {
        this.transactionId = transactionId;
        this.originAccount = originAccount;
        this.destinyAccount = destinyAccount;
    }

    // Getters y Setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(int originAccount) {
        this.originAccount = originAccount;
    }

    public Integer getDestinyAccount() {
        return destinyAccount;
    }

    public void setDestinyAccount(int destinyAccount) {
        this.destinyAccount = destinyAccount;
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "transactionId=" + transactionId +
                ", originAccount=" + originAccount +
                ", destinyAccount=" + destinyAccount +
                '}';
    }
}
