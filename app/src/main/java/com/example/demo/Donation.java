package com.example.demo;

public class Donation {
    private String name;
    private String foodItem;
    private String phoneNo;
    private String description;
    private boolean isOrdered; // New field to track order status

    // Empty constructor for Firestore
    public Donation() {}

    public Donation(String name, String foodItem, String phoneNo, String description) {
        this.name = name;
        this.foodItem = foodItem;
        this.phoneNo = phoneNo;
        this.description = description;
        this.isOrdered = false; // Initialize as not ordered
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }
}
