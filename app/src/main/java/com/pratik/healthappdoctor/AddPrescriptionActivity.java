package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pratik.healthappdoctor.models.Patient;

public class AddPrescriptionActivity extends AppCompatActivity {

    Patient patient;

    TextView NameTextView, AgeTextView, GenderTextView, WeightTextView, HeightTextView, MedicineTextView;
    TextInputEditText MedNameTextInput, DaysTextInput;
    MaterialButton AddMedicineButton, AddPrescriptionButton;

    CheckBox BBreakfastCheckBox, ABreakfastCheckBox, BLunchCheckBox, ALunchCheckBox, BDinnerCheckBox, ADinnerCheckBox;

    String Medicines;
    int count = 0;
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
        MedicineTextView = findViewById(R.id.textViewMedicine);

        MedNameTextInput = findViewById(R.id.textInputMedicineName);
        DaysTextInput = findViewById(R.id.textInputDay);

        BBreakfastCheckBox = findViewById(R.id.checkBoxBeforeBreakfast);
        ABreakfastCheckBox = findViewById(R.id.checkBoxAfterBreakfast);

        BLunchCheckBox = findViewById(R.id.checkBoxBeforeLunch);
        ALunchCheckBox = findViewById(R.id.checkBoxAfterLunch);

        BDinnerCheckBox = findViewById(R.id.checkBoxBeforeDinner);
        ADinnerCheckBox = findViewById(R.id.checkBoxAfterDinner);


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
                if (MedNameTextInput.getText().toString().equals(null)) {
                    MedNameTextInput.setError("Enter Medicine Name!");
                    MedNameTextInput.requestFocus();
                    return;
                }
                if (DaysTextInput.getText().toString().equals(null) || DaysTextInput.getText().toString().equals("0")) {
                    DaysTextInput.setError("Enter Dose Days!");
                    DaysTextInput.requestFocus();
                    return;
                }
                count++;
                if (count == 1) {
                    Medicines = count + ". " + MedNameTextInput.getText().toString() + "\n      For: " + DaysTextInput.getText().toString() + " Days\n      Dose Timings:";
                } else {
                    Medicines += count + ". " + MedNameTextInput.getText().toString() + "\n      For: " + DaysTextInput.getText().toString() + " Days\n      Dose Timings:";
                }
                Medicines += "\n        ";
                if (BBreakfastCheckBox.isChecked()) {
                    Medicines += "Before Breakfast\t\t\t";
                }
                if (ABreakfastCheckBox.isChecked()) {
                    Medicines += "After Breakfast\t\t\t";
                }
                Medicines += "\n        ";
                if (BLunchCheckBox.isChecked()) {
                    Medicines += "Before Lunch\t\t\t";
                }
                if (ALunchCheckBox.isChecked()) {
                    Medicines += "After Lunch\t\t\t";
                }
                Medicines += "\n        ";
                if (BDinnerCheckBox.isChecked()) {
                    Medicines += "Before Dinner";
                }
                if (ADinnerCheckBox.isChecked()) {
                    Medicines += "After Dinner";
                }
                Medicines += "\n\n";

                MedicineTextView.setText(Medicines);
            }
        });

        AddPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddPrescriptionActivity.this, TreatActivity.class);
                i.putExtra("Patient", patient);
                startActivity(i);
                finish();
            }
        });
    }
}
