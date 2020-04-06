package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pratik.healthappdoctor.models.Patient;

public class AddTreatmentActivity extends AppCompatActivity {

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");
    }
}
