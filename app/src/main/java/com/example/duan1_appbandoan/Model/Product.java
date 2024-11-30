package com.example.duan1_appbandoan.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int idProduct;
    private String name;
    private String description;
    private Integer idReview;
    private int idCategory;
    private int totalSale;
    private int price;
    private int quantity;
    private String imageUrl; // Tên file ảnh (dạng String, ví dụ: "banh_mi_pate")
    private int imageResId;  // ID của ảnh trong drawable (dạng int)

    // Constructor không tham số
    public Product() {
    }

    // Constructor đầy đủ tham số
    public Product(int idProduct, String name, String description, Integer idReview, int idCategory,
                   int totalSale, int price, int quantity, String imageUrl, int imageResId) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.idReview = idReview;
        this.idCategory = idCategory;
        this.totalSale = totalSale;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.imageResId = imageResId;
    }

    public Product(String name, String description, int idCategory, int price, int imageResId) {
        this.name = name;
        this.description = description;
        this.idCategory = idCategory;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters và Setters cho các thuộc tính

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

    public Integer getIdReview() {
        return idReview;
    }

    public void setIdReview(Integer idReview) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
