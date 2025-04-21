package com.example.demo;

public class stationary_donation {
    private String name;
    private String item;
    private String phone;
    private String description;
    private String imageUrl; // Ensure you have this if you're using images

    // Default constructor required for calls to DataSnapshot.getValue(StationaryDonation.class)
    public stationary_donation() {
    }

    // Constructor without imageUrl
    public stationary_donation(String name, String item, String phone, String description) {
        this.name = name;
        this.item = item;
        this.phone = phone;
        this.description = description;
        this.imageUrl = null; // Optional: Set to null if no imageUrl is provided
    }

    public stationary_donation(String name, String item, String phone, String description, String imageUrl) {
        this.name = name;
        this.item = item;
        this.phone = phone;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
