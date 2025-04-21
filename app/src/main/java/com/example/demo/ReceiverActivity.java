package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReceiverActivity extends AppCompatActivity implements DonationAdapter.OnOrderClickListener { // Implement the interface

    private RecyclerView recyclerView;
    private DonationAdapter donationAdapter;
    private List<Donation> donationList;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private TextView textViewNoDonations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewDonations);
        progressBar = findViewById(R.id.progressBar);
        textViewNoDonations = findViewById(R.id.textViewNoDonations);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donationList = new ArrayList<>();
        donationAdapter = new DonationAdapter(donationList, this); // Pass the listener to the adapter
        recyclerView.setAdapter(donationAdapter);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch donations from Firestore
        fetchDonations();
    }

    private void fetchDonations() {
        progressBar.setVisibility(View.VISIBLE); // Show progress bar while loading

        db.collection("donations") // Assuming your collection is named "donations"
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE); // Hide progress bar after loading

                    if (task.isSuccessful()) {
                        donationList.clear(); // Clear the list before adding new data
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            // Map Firestore data to Donation object
                            Donation donation = document.toObject(Donation.class);
                            donationList.add(donation); // Add donation to the list
                        }

                        donationAdapter.notifyDataSetChanged(); // Notify adapter of data changes

                        // Show or hide the no donations message based on list content
                        if (donationList.isEmpty()) {
                            textViewNoDonations.setVisibility(View.VISIBLE); // Show no donations message
                        } else {
                            textViewNoDonations.setVisibility(View.GONE); // Hide the message when donations are available
                        }

                    } else {
                        Toast.makeText(ReceiverActivity.this, "Error getting donations", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onOrderClick(Donation donation) {
        // Handle the order placement logic here
        // For example, you might want to navigate to another activity or show a confirmation dialog
        Toast.makeText(this, "Order placed for: " + donation.getFoodItem(), Toast.LENGTH_SHORT).show();

        // Here you can add your logic for placing the order, e.g., saving it in Firestore or updating the UI
    }
}
