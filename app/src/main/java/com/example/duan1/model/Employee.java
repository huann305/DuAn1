package com.example.duan1.model;

public class Employee {
    private String email;
    private String name;
    private String phone;
    private String address;
    private String citizenshipID;
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCitizenshipID() {
        return citizenshipID;
    }

    public void setCitizenshipID(String citizenshipID) {
        this.citizenshipID = citizenshipID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee(String email, String name, String phone, String address, String citizenshipID, String status) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.citizenshipID = citizenshipID;
        this.status = status;
    }
}
