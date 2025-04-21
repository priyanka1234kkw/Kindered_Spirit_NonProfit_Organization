package com.example.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Cloth_DonationAdapter extends RecyclerView.Adapter<Cloth_DonationAdapter.DonationViewHolder> {
    private final List<Cloth_Donation> donations;

    // Constructor to pass the list of donations
    public Cloth_DonationAdapter(List<Cloth_Donation> donations) {
        this.donations = donations;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each RecyclerView item using the correct item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_item_donation, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        // Bind data for each donation item at the current position
        Cloth_Donation donation = donations.get(position);
        holder.bind(donation);
    }

    @Override
    public int getItemCount() {
        // Return the size of the donation list
        return donations != null ? donations.size() : 0;
    }

    // ViewHolder class for RecyclerView items
    static class DonationViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView sizeTextView;
        private final TextView descriptionTextView;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Link the UI components with the layout
            nameTextView = itemView.findViewById(R.id.textViewName);
            sizeTextView = itemView.findViewById(R.id.textViewSize);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
        }

        // Method to bind data to the TextViews
        public void bind(Cloth_Donation donation) {
            // Safely set the text for each field
            nameTextView.setText(donation.getName() != null ? donation.getName() : "N/A");
            sizeTextView.setText(donation.getSize() != null ? donation.getSize() : "N/A");
            descriptionTextView.setText(donation.getDescription() != null ? donation.getDescription() : "N/A");
        }
    }
}
