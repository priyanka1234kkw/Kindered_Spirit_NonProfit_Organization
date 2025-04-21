package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class stationary_receiver extends AppCompatActivity {

    private RecyclerView recyclerView;
    private stationary_donation_adapter adapter;
    private List<stationary_donation> donationList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewDonations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize donation list
        donationList = new ArrayList<>();
        adapter = new stationary_donation_adapter(donationList);
        recyclerView.setAdapter(adapter);

        // Fetch donations from Firestore
        fetchDonationsFromFirestore();
    }

    private void fetchDonationsFromFirestore() {
        db.collection("Stationary_Donation") // Ensure this matches exactly with your Firestore collection
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot document : querySnapshot) {
                                String name = document.getString("name");
                                String item = document.getString("item");
                                String phone = document.getString("phone");
                                String description = document.getString("description");
                                String imageUrl = document.getString("imageUrl");  // Optional, can be null if not used

                                // Create a stationary_donation object and add it to the list
                                stationary_donation donation = new stationary_donation(name, item, phone, description, imageUrl);
                                donationList.add(donation);
                                Log.d("FirestoreData", "Donation fetched: " + name + ", " + item);
                            }

                            // Notify the adapter of the data change
                            adapter.notifyDataSetChanged();
                            Log.d("FirestoreData", "Total donations: " + donationList.size());
                            Toast.makeText(stationary_receiver.this, "Data fetched: " + donationList.size(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("FirestoreError", "No documents found");
                        }
                    } else {
                        Log.e("FirestoreError", "Error fetching data", task.getException());
                        Toast.makeText(stationary_receiver.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
