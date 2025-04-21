package com.example.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class otherActivity extends AppCompatActivity {

    private EditText donationAmount, donationItemDetails;
    private RadioButton donateMoney, donateItems;
    private ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);

        // Initialize Views
        donationAmount = findViewById(R.id.donation_amount);
        donationItemDetails = findViewById(R.id.donation_item_details);
        donateMoney = findViewById(R.id.radio_donate_money);
        donateItems = findViewById(R.id.radio_donate_items);
        Button donateButton = findViewById(R.id.donate_button);
        itemImage = findViewById(R.id.item_image);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Handle Radio Button Selection
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_donate_money) {
                donationAmount.setVisibility(View.VISIBLE);
                donationItemDetails.setVisibility(View.GONE);
                itemImage.setVisibility(View.GONE);
            } else if (checkedId == R.id.radio_donate_items) {
                donationAmount.setVisibility(View.GONE);
                donationItemDetails.setVisibility(View.VISIBLE);
                itemImage.setVisibility(View.VISIBLE);
            }
        });

        /* Handle Donate Button Click */
        donateButton.setOnClickListener((View v) -> {
            if (donateMoney.isChecked()) {
                handleGooglePay();
            } else if (donateItems.isChecked()) {
                handleItemDonation();
            } else {
                Toast.makeText(otherActivity.this, "Select a donation type", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Google Pay Handler
    @SuppressLint("QueryPermissionsNeeded")
    private void handleGooglePay() {
        String amount = donationAmount.getText().toString();
        if (!amount.isEmpty()) {
            Uri uri = Uri.parse("upi://pay?pa=YOUR_UPI_ID&pn=YourName&tn=Donation&am=" + amount + "&cu=INR");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.nbu.paisa.user");
            if (null == intent.resolveActivity(getPackageManager())) {
                Toast.makeText(this, "Google Pay not found", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Enter donation amount", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle Item Donation
    private void handleItemDonation() {
        String itemDetails = donationItemDetails.getText().toString();
        if (!itemDetails.isEmpty()) {
            Toast.makeText(this, "Donated item: " + itemDetails, Toast.LENGTH_SHORT).show();
            // Add logic for item donation here
        } else {
            Toast.makeText(this, "Enter donation item details", Toast.LENGTH_SHORT).show();
        }
    }
}
