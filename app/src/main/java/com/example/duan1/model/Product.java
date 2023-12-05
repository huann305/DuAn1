package com.example.duan1.model;

public class Product {
    private int id;
    private String name;
    private int price;
    private int quantitySold;
    private int quantity;
    private String status;
    private String image;

    public Product(int id, String name, int price, int quantitySold, int quantity, String status, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantitySold = quantitySold;
        this.quantity = quantity;
        this.status = status;
        this.image = image;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantitySold=" + quantitySold +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' + "}";
    }
}
