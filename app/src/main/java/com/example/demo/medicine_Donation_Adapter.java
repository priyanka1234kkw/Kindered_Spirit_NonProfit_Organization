package com.example.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class medicine_Donation_Adapter extends RecyclerView.Adapter<medicine_Donation_Adapter.DonationViewHolder> {

    private List<medicine_Donation> donationList;

    // Constructor
    public medicine_Donation_Adapter(List<medicine_Donation> donationList) {
        this.donationList = donationList;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation_medicine, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        medicine_Donation donation = donationList.get(position);
        holder.donorNameTextView.setText(donation.getDonorName());
        holder.contactNumberTextView.setText(donation.getContactNumber());
        holder.emailTextView.setText(donation.getEmail());
        holder.medicineNameTextView.setText(donation.getMedicineName());
        holder.medicineTypeTextView.setText(donation.getMedicineType());
        holder.quantityTextView.setText(donation.getQuantity());
        holder.expiryDateTextView.setText(donation.getExpiryDate());
        holder.batchNumberTextView.setText(donation.getBatchNumber());
        holder.manufacturerTextView.setText(donation.getManufacturer());
        holder.storageRequirementsTextView.setText(donation.getStorageRequirements());
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView donorNameTextView, contactNumberTextView, emailTextView, medicineNameTextView, medicineTypeTextView, quantityTextView, expiryDateTextView, batchNumberTextView, manufacturerTextView, storageRequirementsTextView;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            donorNameTextView = itemView.findViewById(R.id.donorName);
            contactNumberTextView = itemView.findViewById(R.id.contactNumber);
            emailTextView = itemView.findViewById(R.id.email);
            medicineNameTextView = itemView.findViewById(R.id.medicineName);
            medicineTypeTextView = itemView.findViewById(R.id.medicineType);
            quantityTextView = itemView.findViewById(R.id.quantity);
            expiryDateTextView = itemView.findViewById(R.id.expiryDate);
            batchNumberTextView = itemView.findViewById(R.id.batchNumber);
            manufacturerTextView = itemView.findViewById(R.id.manufacturer);
            storageRequirementsTextView = itemView.findViewById(R.id.storageRequirements);
        }
    }
}
