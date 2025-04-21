package com.example.demo;

public class medicine_Donation {
    private String donorName, contactNumber, email, medicineName, medicineType, quantity, expiryDate, batchNumber, manufacturer, storageRequirements;

    // Default constructor required for Firebase
    public medicine_Donation() {}

    // Parameterized constructor
    public medicine_Donation(String donorName, String contactNumber, String email, String medicineName, String medicineType,
                    String quantity, String expiryDate, String batchNumber, String manufacturer, String storageRequirements) {
        this.donorName = donorName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.batchNumber = batchNumber;
        this.manufacturer = manufacturer;
        this.storageRequirements = storageRequirements;
    }

    // Getters and Setters for all fields
    public String getDonorName() {
        return donorName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getStorageRequirements() {
        return storageRequirements;
    }
}
