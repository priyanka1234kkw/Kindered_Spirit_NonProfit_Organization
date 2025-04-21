package com.example.demo; // Update with your correct package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class stationary_donation_adapter extends RecyclerView.Adapter<stationary_donation_adapter.DonationViewHolder> {
    private List<stationary_donation> donations;
    private Set<Integer> orderedPositions; // To track which donations have been ordered

    public stationary_donation_adapter(List<stationary_donation> donations) {
        this.donations = donations;
        this.orderedPositions = new HashSet<>(); // Initialize the set
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_item_receiver, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        stationary_donation donation = donations.get(position);
        holder.textViewName.setText(donation.getName());
        holder.textViewItem.setText(donation.getItem());
        holder.textViewPhone.setText("Phone: " + donation.getPhone());
        holder.textViewDescription.setText(donation.getDescription());

        // Set the button click listener
        holder.btnOrderNow.setOnClickListener(v -> {
            if (orderedPositions.contains(position)) {
                Toast.makeText(holder.itemView.getContext(), "Order is already taken", Toast.LENGTH_SHORT).show();
            } else {
                orderedPositions.add(position); // Mark this position as ordered
                Toast.makeText(holder.itemView.getContext(), "Order successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewItem, textViewPhone, textViewDescription;
        Button btnOrderNow; // Declare the button

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvName); // Ensure correct ID
            textViewItem = itemView.findViewById(R.id.tvItem); // Ensure correct ID
            textViewPhone = itemView.findViewById(R.id.tvPhone); // Ensure correct ID
            textViewDescription = itemView.findViewById(R.id.tvDescription); // Ensure correct ID
            btnOrderNow = itemView.findViewById(R.id.btnOrderNow); // Find button by ID
        }
    }
}
