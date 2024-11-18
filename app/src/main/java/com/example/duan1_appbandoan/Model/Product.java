package com.example.duan1_appbandoan.Model;

public class Product {
    private int idProduct;
    private String name;
    private String description;
    private int idReview;
    private int idCategory;
    private int totalSale;

    // Constructor
    public Product(int idProduct, String name, String description, int idReview, int idCategory, int totalSale) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.idReview = idReview;
        this.idCategory = idCategory;
        this.totalSale = totalSale;
    }

    // Default Constructor
    public Product() {}

    // Getter and Setter methods
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
}
