package com.example.duan1.model;

public class Product {
    private int id;
    private String name;
    private String image;
    private int price;
    private int quantitySold;
    private String status;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product(int id, String name, String image, int price, int quantitySold, String status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantitySold = quantitySold;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantitySold=" + quantitySold +
                ", status='" + status + '\'' +
                '}';
    }
}
