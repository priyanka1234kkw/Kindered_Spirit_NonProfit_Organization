package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Tag for logging
    private EditText nameEditText;
    private EditText foodItemEditText;
    private EditText phoneNoEditText;
    private EditText descriptionEditText;
    private Button donateNowButton;
    private TextView thankYouTextView;

    // Initialize Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        nameEditText = findViewById(R.id.editTextText3);
        foodItemEditText = findViewById(R.id.editTextText4);
        phoneNoEditText = findViewById(R.id.editTextText5);
        descriptionEditText = findViewById(R.id.editTextText6);
        donateNowButton = findViewById(R.id.buttonDonateNow);
        thankYouTextView = findViewById(R.id.textView);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        donateNowButton.setOnClickListener(v -> {
            Log.d(TAG, "Donate Now button clicked");
            // Check if all fields are filled
            if (isAllFieldsFilled()) {
                // Prepare data to store
                String name = nameEditText.getText().toString().trim();
                String foodItem = foodItemEditText.getText().toString().trim();
                String phoneNo = phoneNoEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                // Create a new donation entry
                Donation donation = new Donation(name, foodItem, phoneNo, description);

                // Log the donation object to debug
                Log.d(TAG, "Creating donation: " + donation.toString());

                // Add a new document with a generated ID
                db.collection("donations")
                        .add(donation)
                        .addOnSuccessListener(documentReference -> {
                            // Update UI on success
                            thankYouTextView.setVisibility(View.VISIBLE);
                            thankYouTextView.setText("Donation added successfully! Thank you for your donation!");
                            Toast.makeText(MainActivity.this, "Donation added successfully!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Donation added successfully: " + documentReference.getId());
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                            Log.e(TAG, "Error adding donation: " + e.getMessage());
                            Toast.makeText(MainActivity.this, "Error adding donation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isAllFieldsFilled() {
        // Check if all EditText fields are filled
        return !nameEditText.getText().toString().trim().isEmpty() &&
                !foodItemEditText.getText().toString().trim().isEmpty() &&
                !phoneNoEditText.getText().toString().trim().isEmpty() &&
                !descriptionEditText.getText().toString().trim().isEmpty();
    }
}
