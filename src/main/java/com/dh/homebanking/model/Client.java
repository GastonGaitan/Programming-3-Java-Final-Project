package com.dh.homebanking.model;

import java.sql.Date;

public class Client {
    private Integer clientId;
    private String status;
    private Date clientStatusDate;
    private String clientType;
    private String username;
    private String email;
    private String password;

    public Client(Integer clientId, String status, Date clientStatusDate,
                  String clientType, String username, String email, String password) {
        this.clientId = clientId;
        this.status = status;
        this.clientStatusDate = clientStatusDate;
        this.clientType = clientType;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Constructor sin status, client_id, ni client_status_date
    public Client(String clientType, String username, String email, String password) {
        this.clientType = clientType;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Client(Integer clientId, String clientStatus, Date clientStatusDate, Integer accountTotalCount, String clientType, String username, String email, String password) {
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getClientStatusDate() {
        return clientStatusDate;
    }

    public void setClientStatusDate(Date clientStatusDate) {
        this.clientStatusDate = clientStatusDate;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client [client_id=" + clientId + ", status=" + status +
                ", client_status_date=" + clientStatusDate + ", client_type=" +
                clientType + ", client=" + username + ", email=" + email +
                ", password=" + password + "]";
    }
}

