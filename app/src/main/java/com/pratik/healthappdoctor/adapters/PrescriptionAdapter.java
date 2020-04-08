package com.pratik.healthappdoctor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.models.Prescription;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.MyViewHolder> {

    //For Button Click Interface
    private OnItemClickListener listener;

    private List<Prescription> prescriptions;

    public PrescriptionAdapter(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Prescription prescription = prescriptions.get(position);
        holder.NameTextView.setText("Dr. " + prescription.getDoctorName());
        holder.DateTextView.setText(prescription.getDate());
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // For Button Click
    public interface OnItemClickListener {
        void onItemClick(Prescription prescription, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameTextView, DateTextView;
        MaterialButton ViewPresButton;

        MyViewHolder(View view) {
            super(view);

            NameTextView = view.findViewById(R.id.textViewDoctorName);
            DateTextView = view.findViewById(R.id.textViewDate);
            ViewPresButton = view.findViewById(R.id.btnViewPrescription);

            ViewPresButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(prescriptions.get(position), position);
                    }
                }
            });

        }
    }
}