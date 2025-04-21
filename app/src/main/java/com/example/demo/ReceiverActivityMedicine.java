package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ReceiverActivityMedicine extends AppCompatActivity {

    private RecyclerView donationsRecyclerView;
    private medicine_Donation_Adapter donationAdapter;
    private List<medicine_Donation> donationList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_receiver);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView and list
        donationsRecyclerView = findViewById(R.id.donationsRecyclerView);
        donationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize list and adapter
        donationList = new ArrayList<>();
        donationAdapter = new medicine_Donation_Adapter(donationList);
        donationsRecyclerView.setAdapter(donationAdapter);

        // Fetch data from Firestore
        fetchDonations();
    }

    private void fetchDonations() {
        db.collection("Medicines")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            donationList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                medicine_Donation donation = document.toObject(medicine_Donation.class);
                                donationList.add(donation); // Add each donation to the list
                            }
                            Log.d("DonationList", "Items fetched: " + donationList.size());
                            donationAdapter.notifyDataSetChanged(); // Notify adapter that data has changed
                        } else {
                            Log.e("ReceiverActivity", "Error fetching documents", task.getException());
                            Toast.makeText(ReceiverActivityMedicine.this, "Failed to fetch donations", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
