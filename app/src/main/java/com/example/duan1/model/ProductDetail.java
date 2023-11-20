package com.example.duan1.model;

public class ProductDetail {
    private int id;
    private int idProduct;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductDetail(int id, int idProduct, String description) {
        this.id = id;
        this.idProduct = idProduct;
        this.description = description;
    }

    public ProductDetail() {
    }
}
