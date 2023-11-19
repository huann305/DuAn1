package com.example.duan1.model;

public class Bill {
    private int id;
    private String idEmployee;
    private String idCustomer;
    private String date;
    private String shippingAddress;
    private String status;
    private String email;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bill(int id, String idEmployee, String idCustomer, String date, String shippingAddress, String status, String email) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.idCustomer = idCustomer;
        this.date = date;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.email = email;
    }

    public Bill() {
    }
}
