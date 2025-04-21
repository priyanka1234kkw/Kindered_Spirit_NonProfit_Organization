package com.example.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private final List<Donation> donationList;
    private final OnOrderClickListener onOrderClickListener; // Add listener interface

    public DonationAdapter(List<Donation> donationList, OnOrderClickListener onOrderClickListener) {
        this.donationList = donationList;
        this.onOrderClickListener = onOrderClickListener; // Initialize listener
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donation donation = donationList.get(position);
        holder.nameTextView.setText(donation.getName());
        holder.foodItemTextView.setText(donation.getFoodItem());
        holder.phoneNoTextView.setText(donation.getPhoneNo());
        holder.descriptionTextView.setText(donation.getDescription());

        // Set up button click listener
        holder.buttonAction.setOnClickListener(view -> {
            if (onOrderClickListener != null) {
                onOrderClickListener.onOrderClick(donation); // Trigger the listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public interface OnOrderClickListener { // Define the listener interface
        void onOrderClick(Donation donation);
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, foodItemTextView, phoneNoTextView, descriptionTextView;
        Button buttonAction; // Button for actions

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            foodItemTextView = itemView.findViewById(R.id.textViewFoodItem);
            phoneNoTextView = itemView.findViewById(R.id.textViewPhoneNo);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            buttonAction = itemView.findViewById(R.id.buttonAction); // Correct ID reference
        }
    }
}
