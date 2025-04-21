package com.example.demo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReceiverActivityCloth extends AppCompatActivity {

    private RecyclerView recyclerViewClothes;
    private ClothesAdapter clothesAdapter;
    private List<ClothingItem> clothingItemList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_cloth); // Ensure this matches your XML layout file name

        recyclerViewClothes = findViewById(R.id.recyclerViewClothes);
        recyclerViewClothes.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore and the list
        db = FirebaseFirestore.getInstance();
        clothingItemList = new ArrayList<>();

        // Initialize adapter
        clothesAdapter = new ClothesAdapter(clothingItemList);
        recyclerViewClothes.setAdapter(clothesAdapter);

        // Load data from Firebase Firestore
        loadClothingData();
    }

    private void loadClothingData() {
        // Fetch the data from the "Cloth_Donations" Firestore collection
        db.collection("Cloth_Donations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            clothingItemList.clear();  // Clear the list before adding new items
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                // Assuming the document has fields: name, size, description
                                String name = document.getString("name");
                                String size = document.getString("size");
                                String description = document.getString("description");

                                // Create ClothingItem object and add to the list
                                clothingItemList.add(new ClothingItem(name, size, description));
                            }

                            // Notify adapter of data changes
                            clothesAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Handle failure in fetching data
                        Toast.makeText(ReceiverActivityCloth.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Proper ClothesAdapter class
    private class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder> {

        private List<ClothingItem> clothingItemList;

        public ClothesAdapter(List<ClothingItem> clothingItemList) {
            this.clothingItemList = clothingItemList;
        }

        @NonNull
        @Override
        public ClothesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_item_donation, parent, false); // Ensure cloth_item_donation.xml exists
            return new ClothesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ClothesViewHolder holder, int position) {
            ClothingItem clothingItem = clothingItemList.get(position);
            holder.textViewName.setText(clothingItem.getName());
            holder.textViewSize.setText(clothingItem.getSize());
            holder.textViewDescription.setText(clothingItem.getDescription());
        }

        @Override
        public int getItemCount() {
            return clothingItemList.size();
        }

        public class ClothesViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName, textViewSize, textViewDescription;

            public ClothesViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName); // Ensure IDs exist in cloth_item_donation.xml
                textViewSize = itemView.findViewById(R.id.textViewSize);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
            }
        }
    }
}
