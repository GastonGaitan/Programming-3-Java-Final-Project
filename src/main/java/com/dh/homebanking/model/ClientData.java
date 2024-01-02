package com.dh.homebanking.model;
import java.util.Date;

public class ClientData {
        private int clientDataId;
        private int clientId;
        private String firstName;
        private String lastName;
        private Date bornDate;
        private String dni;
        private String phoneNumber;
        private String nationality;

        // Constructor
        public ClientData(int clientDataId,int clientId, String firstName, String lastName, Date bornDate,
                          String dni, String phoneNumber, String nationality) {
            this.clientDataId = clientDataId;
            this.clientId = clientId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.bornDate = bornDate;
            this.dni = dni;
            this.phoneNumber = phoneNumber;
            this.nationality = nationality;
        }

    public ClientData(int clientId, String firstName, String lastName, Date bornDate,
                      String dni, String phoneNumber, String nationality) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = bornDate;
        this.dni = dni;
        this.phoneNumber = phoneNumber;
        this.nationality = nationality;
    }

        // Getters y setters (puedes generarlos automáticamente en muchos entornos de desarrollo)

        public int getClientDataId() {
            return clientDataId;
        }

        public void setClientDataId(int clientDataId) {
            this.clientDataId = clientDataId;
        }

        public int getClientId() {
            return clientId;
        }

        public void setClientId(int clientId) {
            this.clientId = clientId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Date getBornDate() {
            return bornDate;
        }

        public void setBornDate(Date bornDate) {
            this.bornDate = bornDate;
        }

        public String getDni() {
            return dni;
        }

        public void setDni(String dni) {
            this.dni = dni;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

    @Override
    public String toString() {
        return "ClientData [clientDataId=" + clientDataId +
                ", clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bornDate=" + bornDate +
                ", dni='" + dni + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                "]";
    }

}
