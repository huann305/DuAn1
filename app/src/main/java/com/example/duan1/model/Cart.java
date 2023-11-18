package com.example.duan1.model;

public class Cart {
    private int id;
    private int idProduct;
    private String name;
    private int quantity;
    private int price;

    public Cart(int id, int idProduct, String name, int quantity, int price) {
        this.id = id;
        this.idProduct = idProduct;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
