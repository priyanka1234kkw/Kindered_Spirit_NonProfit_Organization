package com.example.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCloth extends AppCompatActivity {

    private EditText etName, etContactNumber, etAddress, etClothesType, etClothesSize, etClothesCondition, etClothesQuantity;
    private ImageView imgClothes;
    private Button btnUploadImage, btnDonate;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    private RecyclerView recyclerView;
    private Cloth_DonationAdapter donationAdapter; // Updated to use Cloth_DonationAdapter
    private List<Cloth_Donation> donationList;     // Updated to use Cloth_Donation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycloth);

        // Initialize UI components
        initializeUI();

        // Initialize the image picker launcher
        setupImagePickerLauncher();

        // Initialize the RecyclerView
        donationList = new ArrayList<>();
        donationAdapter = new Cloth_DonationAdapter(donationList); // Updated to use Cloth_DonationAdapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(donationAdapter);

        // Set up button click listeners
        btnUploadImage.setOnClickListener(view -> openGallery());
        btnDonate.setOnClickListener(view -> submitDonation());
    }

    private void initializeUI() {
        etName = findViewById(R.id.etName);
        etContactNumber = findViewById(R.id.etContactNumber);
        etAddress = findViewById(R.id.etAddress);
        etClothesType = findViewById(R.id.etClothesType);
        etClothesSize = findViewById(R.id.etClothesSize);
        etClothesCondition = findViewById(R.id.etClothesCondition);
        etClothesQuantity = findViewById(R.id.etClothesQuantity);
        imgClothes = findViewById(R.id.imgClothes);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnDonate = findViewById(R.id.btnDonate);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupImagePickerLauncher() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imgClothes.setImageURI(imageUri);
                    } else {
                        Toast.makeText(this, "Image selection failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void submitDonation() {
        String name = etName.getText().toString().trim();
        String contactNumber = etContactNumber.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String clothesType = etClothesType.getText().toString().trim();
        String clothesSize = etClothesSize.getText().toString().trim();
        String clothesCondition = etClothesCondition.getText().toString().trim();
        String clothesQuantity = etClothesQuantity.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || contactNumber.isEmpty() || address.isEmpty() ||
                clothesType.isEmpty() || clothesSize.isEmpty() ||
                clothesCondition.isEmpty() || clothesQuantity.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Cloth_Donation object and add it to the list
        Cloth_Donation donation = new Cloth_Donation(name, clothesType, clothesQuantity); // Updated to use Cloth_Donation
        donationList.add(donation);
        donationAdapter.notifyItemInserted(donationList.size() - 1);

        // Show a success message
        Toast.makeText(this, "Donation submitted successfully!", Toast.LENGTH_SHORT).show();

        clearFields();
    }

    private void clearFields() {
        etName.setText("");
        etContactNumber.setText("");
        etAddress.setText("");
        etClothesType.setText("");
        etClothesSize.setText("");
        etClothesCondition.setText("");
        etClothesQuantity.setText("");
        imgClothes.setImageResource(0); // Clear the image
    }
}
