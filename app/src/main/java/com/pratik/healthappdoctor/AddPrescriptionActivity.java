package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pratik.healthappdoctor.models.Patient;

public class AddPrescriptionActivity extends AppCompatActivity {

    Patient patient;

    TextView NameTextView, AgeTextView, GenderTextView, WeightTextView, HeightTextView;
    RecyclerView recyclerView;
    TextInputEditText MedNameTextInput, DaysTextInput;
    MaterialButton AddMedicineButton, AddPrescriptionButton;

    CheckBox BreakfastCheckBox, LunchCheckBox, DinnerCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");

        NameTextView = findViewById(R.id.textViewName);
        AgeTextView = findViewById(R.id.textViewAge);
        GenderTextView = findViewById(R.id.textViewGender);
        WeightTextView = findViewById(R.id.textViewWeight);
        HeightTextView = findViewById(R.id.textViewHeight);

        recyclerView = findViewById(R.id.recyclerMedicine);

        MedNameTextInput = findViewById(R.id.textInputMedicineName);
        DaysTextInput = findViewById(R.id.textInputDay);

        BreakfastCheckBox = findViewById(R.id.checkBoxBreakfast);
        LunchCheckBox = findViewById(R.id.checkBoxLunch);
        DinnerCheckBox = findViewById(R.id.checkBoxDinner);

        AddMedicineButton = findViewById(R.id.btnAddMedicine);

        AddPrescriptionButton = findViewById(R.id.btnAddPrescription);

        NameTextView.setText(patient.getName());
        AgeTextView.setText(String.valueOf(patient.getAge()));
        GenderTextView.setText(patient.getGender());
        WeightTextView.setText(String.valueOf(patient.getWeight()));
        HeightTextView.setText(String.valueOf(patient.getHeight()));

        AddMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AddPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddPrescriptionActivity.this, DashActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
