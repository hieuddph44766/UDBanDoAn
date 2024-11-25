package com.example.duan1_appbandoan.Model;

public class Product {
    private int idProduct;
    private String name;
    private String description;
    private int idReview;
    private int idCategory;
    private int totalSale;
    private int price;
    private int quantity;

    public Product() {
    }

    public Product(int idProduct, String name, String description, int idReview, int idCategory, int totalSale, int price, int quantity) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.idReview = idReview;
        this.idCategory = idCategory;
        this.totalSale = totalSale;
        this.price = price;
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(int totalSale) {
        this.totalSale = totalSale;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}