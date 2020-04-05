package com.pratik.healthappdoctor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    //For Button Click Interface
    private OnItemClickListener listener;

    private List<Appointment> appointments;

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public AppointmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.AppointmentIDTextView.setText(String.valueOf(appointment.getNumber()));
        holder.PatientIDTextView.setText(appointment.getpID());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // For Button Click
    public interface OnItemClickListener {
        void onItemClick(Appointment appointment, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView AppointmentIDTextView;
        TextView PatientIDTextView;
        MaterialButton CheckPatientButton;
        MyViewHolder(View view) {
            super(view);
            AppointmentIDTextView = itemView.findViewById(R.id.textViewMFGDate);
            PatientIDTextView = itemView.findViewById(R.id.textViewstock);
            CheckPatientButton = itemView.findViewById(R.id.btnCheckPatient);

            CheckPatientButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(appointments.get(position), position);
                    }
                }
            });
        }
    }
}