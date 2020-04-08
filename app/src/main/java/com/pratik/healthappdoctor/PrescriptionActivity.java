package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pratik.healthappdoctor.models.Prescription;

public class PrescriptionActivity extends AppCompatActivity {

    Prescription prescription;
    TextView DoctorName, Date, Speciality, Degree, Type, Medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        Intent i = getIntent();
        prescription = (Prescription) i.getSerializableExtra("Prescription");

        DoctorName = findViewById(R.id.textViewDoctorName);
        Date = findViewById(R.id.textViewDate);
        Speciality = findViewById(R.id.textViewSpeciality);
        Degree = findViewById(R.id.textViewDegree);
        Type = findViewById(R.id.textViewType);
        Medicines = findViewById(R.id.textViewMedicine);

        DoctorName.setText(prescription.getDoctorName());
        Date.setText(prescription.getDate());
        Speciality.setText(prescription.getDoctorSpeciality());
        Degree.setText(prescription.getDoctorDegree());
        Type.setText(prescription.getType());
        Medicines.setText(prescription.getMedicines());
    }
}
