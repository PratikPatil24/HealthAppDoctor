package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.healthappdoctor.models.Patient;

public class TreatActivity extends AppCompatActivity {

    MaterialButton CompleteButton;
    Patient patient;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treat);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");

        Toast.makeText(this, patient.getID() + " " + patient.getName(), Toast.LENGTH_SHORT).show();

        CompleteButton = findViewById(R.id.btnComplete);

        CompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TreatActivity.this, DashActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
