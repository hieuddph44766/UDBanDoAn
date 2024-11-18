package com.example.duan1_appbandoan.Model;

public class User {
    private int idUser;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String phone;
    private int role;

    // Constructor
    public User(int idUser, String userName, String password, String email, String address, String phone, int role) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public User(String password, String userName) {
        this.password = password;
        this.userName = userName;
    }

    public User() {}

    // Getter and Setter methods
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}