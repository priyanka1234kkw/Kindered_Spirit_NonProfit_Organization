package com.example.demo;

  // Change to your package name

public class ClothingItem {
    private String name;
    private String size;
    private String description;

    public ClothingItem(String name, String size, String description) {
        this.name = name;
        this.size = size;
        this.description = description;
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

