package com.example.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Map;

public class MainActivityMedicine extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAnalytics firebaseAnalytics;

    // Declare all UI elements
    private EditText donorName, contactNumber, email, medicineName, medicineType, quantity, expiryDate, batchNumber, manufacturer, storageRequirements;
    private Button submitButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine); // Ensure this matches your XML file name

        // Initialize Firebase Firestore and Analytics
        db = FirebaseFirestore.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting Donation...");
        progressDialog.setCancelable(false);

        // Find UI elements by ID
        donorName = findViewById(R.id.donorName);
        contactNumber = findViewById(R.id.contactNumber);
        email = findViewById(R.id.email);
        medicineName = findViewById(R.id.medicineName);
        medicineType = findViewById(R.id.medicineType);
        quantity = findViewById(R.id.quantity);
        expiryDate = findViewById(R.id.expiryDate);
        batchNumber = findViewById(R.id.batchNumber);
        manufacturer = findViewById(R.id.manufacturer);
        storageRequirements = findViewById(R.id.storageRequirements);
        submitButton = findViewById(R.id.submitButton);

        // Set up the submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDonation();
            }
        });
    }

    // Method to handle form submission
    private void submitDonation() {
        // Get the input values
        String donorNameValue = donorName.getText().toString().trim();
        String contactNumberValue = contactNumber.getText().toString().trim();
        String emailValue = email.getText().toString().trim();
        String medicineNameValue = medicineName.getText().toString().trim();
        String medicineTypeValue = medicineType.getText().toString().trim();
        String quantityValue = quantity.getText().toString().trim();
        String expiryDateValue = expiryDate.getText().toString().trim();
        String batchNumberValue = batchNumber.getText().toString().trim();
        String manufacturerValue = manufacturer.getText().toString().trim();
        String storageRequirementsValue = storageRequirements.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(donorNameValue) || TextUtils.isEmpty(contactNumberValue) || TextUtils.isEmpty(emailValue) ||
                TextUtils.isEmpty(medicineNameValue) || TextUtils.isEmpty(medicineTypeValue) || TextUtils.isEmpty(quantityValue) ||
                TextUtils.isEmpty(expiryDateValue) || TextUtils.isEmpty(batchNumberValue) || TextUtils.isEmpty(manufacturerValue) ||
                TextUtils.isEmpty(storageRequirementsValue)) {
            Toast.makeText(MainActivityMedicine.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            Toast.makeText(MainActivityMedicine.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate contact number (example: must be 10 digits)
        if (contactNumberValue.length() != 10 || !TextUtils.isDigitsOnly(contactNumberValue)) {
            Toast.makeText(MainActivityMedicine.this, "Please enter a valid 10-digit contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress dialog
        progressDialog.show();

        // Prepare data to be stored in Firestore
        Map<String, Object> donation = new HashMap<>();
        donation.put("donorName", donorNameValue);
        donation.put("contactNumber", contactNumberValue);
        donation.put("email", emailValue);
        donation.put("medicineName", medicineNameValue);
        donation.put("medicineType", medicineTypeValue);
        donation.put("quantity", quantityValue);
        donation.put("expiryDate", expiryDateValue);
        donation.put("batchNumber", batchNumberValue);
        donation.put("manufacturer", manufacturerValue);
        donation.put("storageRequirements", storageRequirementsValue);

        // Save to Firestore
        db.collection("Medicines")
                .add(donation)
                .addOnSuccessListener(documentReference -> {
                    // Hide progress dialog
                    progressDialog.dismiss();

                    // Show a popup message for success
                    Toast.makeText(MainActivityMedicine.this, "Donation successfully submitted!", Toast.LENGTH_SHORT).show();

                    // Track successful submission event (optional, with Firebase Analytics)
                    Bundle analyticsBundle = new Bundle();
                    analyticsBundle.putString("donor_name", donorNameValue);
                    firebaseAnalytics.logEvent("donation_submission", analyticsBundle);

                    // Clear form after submission
                    clearForm();
                })
                .addOnFailureListener(e -> {
                    // Hide progress dialog
                    progressDialog.dismiss();
                    Toast.makeText(MainActivityMedicine.this, "Error adding donation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Print error log in Logcat
                });
    }

    // Clear form method
    private void clearForm() {
        donorName.setText("");
        contactNumber.setText("");
        email.setText("");
        medicineName.setText("");
        medicineType.setText("");
        quantity.setText("");
        expiryDate.setText("");
        batchNumber.setText("");
        manufacturer.setText("");
        storageRequirements.setText("");
        Toast.makeText(MainActivityMedicine.this, "Form has been cleared", Toast.LENGTH_SHORT).show();
    }
}
