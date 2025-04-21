package com.example.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class stationary extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri photoURI;
    private File photoFile;

    private EditText editTextName;
    private EditText editTextItem;
    private EditText editTextPhone;
    private EditText editTextDescription;
    private ImageView imageViewPreview;
    private RadioGroup radioGroup;

    // Firestore instance
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary2);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextItem = findViewById(R.id.editTextItem);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewPreview = findViewById(R.id.imageViewPreview);
        radioGroup = findViewById(R.id.radioGroup);

        Button buttonCaptureImage = findViewById(R.id.buttonCaptureImage);
        Button buttonDonateNow = findViewById(R.id.buttonDonateNow);

        // Capture Image Button Click Listener
        buttonCaptureImage.setOnClickListener(v -> captureImage());

        // Donate Now Button Click Listener
        buttonDonateNow.setOnClickListener(v -> donateNow());

        // Test Firestore connectivity
        testFirestoreConnection();
    }

    // Method to test Firestore connection
    private void testFirestoreConnection() {
        db.collection("Stationary_Donation")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Firestore", "Firestore is reachable. Document count: " + task.getResult().size());
                    } else {
                        Log.e("Firestore", "Error getting documents.", task.getException());
                    }
                });
    }

    // Method to capture an image (if required)
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
                Log.e("ImageCapture", "Error creating file: " + ex.getMessage());
                return;
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageViewPreview.setImageURI(photoURI);
        }
    }

    // Method to handle donation submission
    private void donateNow() {
        String name = editTextName.getText().toString().trim();
        String item = editTextItem.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        // Check for empty fields
        if (name.isEmpty() || item.isEmpty() || phone.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            Log.e("DonationSubmission", "One or more fields are empty");
            return;
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();
        String condition = selectedId == R.id.radioButtonNew ? "New" : "Used";

        // Prepare data for Firestore
        Map<String, Object> stationaryDonation = new HashMap<>();
        stationaryDonation.put("name", name);
        stationaryDonation.put("item", item);
        stationaryDonation.put("phone", phone);
        stationaryDonation.put("description", description);
        stationaryDonation.put("condition", condition);

        // Directly add donation to Firestore without image upload
        addDonationToFirestore(stationaryDonation);
    }

    // Method to add donation data to Firestore
    private void addDonationToFirestore(Map<String, Object> stationaryDonation) {
        db.collection("Stationary_Donation")
                .add(stationaryDonation)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Donation submitted successfully!", Toast.LENGTH_LONG).show();

                    // Clear fields after submission
                    editTextName.setText("");
                    editTextItem.setText("");
                    editTextPhone.setText("");
                    editTextDescription.setText("");
                    imageViewPreview.setImageDrawable(null);
                    radioGroup.clearCheck();
                    Log.d("DonationSubmission", "Donation submitted successfully!");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error submitting donation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error submitting donation: ", e);
                });
    }
}
