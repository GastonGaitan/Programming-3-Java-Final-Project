package com.dh.homebanking.model;

public class Account {
    private Integer accountID;
    private Integer clientID;
    private Integer balance;
    private String currencyType;

    // Constructor
    public Account(Integer accountID, Integer clientID, Integer balance, String currencyType) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.balance = balance;
        this.currencyType = currencyType;
    }

    public Account(Integer clientID, Integer balance, String currencyType) {
        this.clientID = clientID;
        this.balance = balance;
        this.currencyType = currencyType;
    }

    // Getter for accountID
    public Integer getaccountID() {
        return accountID;
    }

    // Setter for accountID
    public void setaccountID(Integer accountID) {
        this.accountID = accountID;
    }

    // Getter for clientID
    public Integer getclientID() {
        return clientID;
    }

    // Setter for clientID
    public void setclientID(Integer clientID) {
        this.clientID = clientID;
    }

    // Getter for balance
    public Integer getBalance() {
        return balance;
    }

    // Setter for balance
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    // Getter for currencyType
    public String getcurrencyType() {
        return currencyType;
    }

    // Setter for currencyType
    public void setcurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", clientID=" + clientID +
                ", balance=" + balance +
                ", currencyType='" + currencyType + '\'' +
                '}';
    }
}

