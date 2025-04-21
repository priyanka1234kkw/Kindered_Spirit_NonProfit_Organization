package com.example.demo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DonationFormActivity extends AppCompatActivity {
    private EditText nameInput, numberInput, addressInput, typeInput, quantityInput, sizeInput;
    private Button submitButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycloth);

        // Initialize UI components
        nameInput = findViewById(R.id.etName);
        numberInput = findViewById(R.id.etContactNumber);
        addressInput = findViewById(R.id.etAddress);
        typeInput = findViewById(R.id.etClothesType);
        quantityInput = findViewById(R.id.etClothesQuantity);
        sizeInput = findViewById(R.id.etClothesSize);
        submitButton = findViewById(R.id.btnDonate);

        // Initialize Firestore database reference
        db = FirebaseFirestore.getInstance();

        // Set click listener for the submit button
        submitButton.setOnClickListener(v -> submitDonation());
    }

    private void submitDonation() {
        String name = nameInput.getText().toString().trim();
        String number = numberInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String type = typeInput.getText().toString().trim();
        String quantity = quantityInput.getText().toString().trim();
        String size = sizeInput.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty() || address.isEmpty() || type.isEmpty() || quantity.isEmpty() || size.isEmpty()) {
            Toast.makeText(DonationFormActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a donation object
        Cloth_Donation donation = new Cloth_Donation(name,  type, quantity);

        // Save donation to Firestore
        db.collection("Cloth_Donations")
                .add(donation)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(DonationFormActivity.this, "Donation submitted!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DonationFormActivity.this, "Failed to submit donation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
