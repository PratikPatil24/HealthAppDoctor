package com.pratik.healthappdoctor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.models.Treatment;

import java.util.List;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.MyViewHolder> {

    private List<Treatment> treatments;

    public TreatmentAdapter(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.treatment_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Treatment treatment = treatments.get(position);
        holder.NameTextView.setText("Dr. " + treatment.getDoctorName());
        holder.DateTextView.setText(treatment.getDate());
        holder.SpecialityTextView.setText(treatment.getDoctorSpeciality());
        holder.DegreeTextView.setText(treatment.getDoctorDegree());
        holder.TreatmentTextView.setText(treatment.getTreatment());
    }

    @Override
    public int getItemCount() {
        return treatments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameTextView, DateTextView, SpecialityTextView, DegreeTextView, TreatmentTextView;

        MyViewHolder(View view) {
            super(view);

            NameTextView = view.findViewById(R.id.textViewDoctorName);
            DateTextView = view.findViewById(R.id.textViewDate);
            SpecialityTextView = view.findViewById(R.id.textViewSpeciality);
            DegreeTextView = view.findViewById(R.id.textViewDegree);
            TreatmentTextView = view.findViewById(R.id.textViewTreatment);
        }
    }
}
