package com.example.demo;

public class Cloth_Donation {
    private String name;
    private String size;
    private String description;

    public Cloth_Donation(String name, String size, String description) {
        this.name = name;
        this.size = size;
        this.description =description ;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }
}
