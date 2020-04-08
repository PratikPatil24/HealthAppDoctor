package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.healthappdoctor.models.Appointment;
import com.pratik.healthappdoctor.models.Patient;

public class TreatActivity extends AppCompatActivity {

    Patient patient;
    Appointment appointment;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    MaterialButton DashBoardButton, ViewHistoryButton, AddPrescriptionButton, AddTreatmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");
        appointment = (Appointment) i.getSerializableExtra("Appointment");

        Toast.makeText(this, patient.getID() + " " + patient.getName(), Toast.LENGTH_SHORT).show();

        DashBoardButton = findViewById(R.id.btnDashboard);
        ViewHistoryButton = findViewById(R.id.btnViewHistory);
        AddPrescriptionButton = findViewById(R.id.btnAddPrescription);
        AddTreatmentButton = findViewById(R.id.btnAddTreatment);

        DashBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TreatActivity.this, DashActivity.class);
                startActivity(i);
                finish();
            }
        });

        ViewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TreatActivity.this, ViewHistoryActivity.class);
                i.putExtra("Patient", patient);
                startActivity(i);
            }
        });

        AddPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TreatActivity.this, AddPrescriptionActivity.class);
                i.putExtra("Patient", patient);
                i.putExtra("Appointment", appointment);
                startActivity(i);
            }
        });

        AddTreatmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TreatActivity.this, AddTreatmentActivity.class);
                i.putExtra("Patient", patient);
                i.putExtra("Appointment", appointment);
                startActivity(i);
            }
        });
    }
}
