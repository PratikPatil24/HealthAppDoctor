package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.healthappdoctor.models.Appointment;
import com.pratik.healthappdoctor.models.Patient;
import com.pratik.healthappdoctor.models.Prescription;

import java.util.HashMap;
import java.util.Map;

public class AddPrescriptionActivity extends AppCompatActivity {

    Appointment appointment;
    RadioGroup DiagnosisRadioGroup;

    Patient patient;
    String diagnosis;

    TextView NameTextView, AgeTextView, GenderTextView, WeightTextView, HeightTextView, MedicineTextView;
    TextInputEditText MedNameTextInput, DaysTextInput;
    MaterialButton AddMedicineButton, AddPrescriptionButton, ProceedButton;

    CheckBox BBreakfastCheckBox, ABreakfastCheckBox, BLunchCheckBox, ALunchCheckBox, BDinnerCheckBox, ADinnerCheckBox;
    int A = 0, B = 0, Others = 0;

    String Medicines;
    int count = 0;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");
        appointment = (Appointment) i.getSerializableExtra("Appointment");

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

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

        DiagnosisRadioGroup = findViewById(R.id.radioGroupDiagnosis);

        AddPrescriptionButton = findViewById(R.id.btnAddPrescription);
        ProceedButton = findViewById(R.id.btnProceed);

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
                    Medicines += "After Breakfast";
                }
                Medicines += "\n        ";
                if (BLunchCheckBox.isChecked()) {
                    Medicines += "Before Lunch\t\t\t";
                }
                if (ALunchCheckBox.isChecked()) {
                    Medicines += "After Lunch";
                }
                Medicines += "\n        ";
                if (BDinnerCheckBox.isChecked()) {
                    Medicines += "Before Dinner\t\t\t";
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
                addDiagnosisStats();
                addPrescription();
                deletePApp();
                deleteDApp();
//                Intent i = new Intent(AddPrescriptionActivity.this, TreatActivity.class);
//                i.putExtra("Patient", patient);
//                startActivity(i);
//                finish();
            }
        });

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    addDiagnosisStats();
                    addPrescription();
                    deletePApp();
                    deleteDApp();
                }
                int checked = DiagnosisRadioGroup.getCheckedRadioButtonId();
                if (checked == -1) {
                    Toast.makeText(AddPrescriptionActivity.this, "Select User Type!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checked == R.id.radioButtonA) {
                    diagnosis = "A";
                    A++;
                } else if (checked == R.id.radioButtonB) {
                    diagnosis = "B";
                    B++;
                } else if (checked == R.id.radioButtonOthers) {
                    diagnosis = "Others";
                    Others++;
                }

                Prescription prescription = new Prescription("id", appointment.getdID(), appointment.getDoctorName(), appointment.getSpeciality(), appointment.getDegree(),
                        appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear(), diagnosis, Medicines);
                Intent i = new Intent(AddPrescriptionActivity.this, ChemistActivity.class);
                i.putExtra("Patient", patient);
                i.putExtra("Prescription", prescription);
                i.putExtra("Appointment", appointment);
                startActivity(i);
                finish();
            }
        });
    }

    void addDiagnosisStats() {
        int checked = DiagnosisRadioGroup.getCheckedRadioButtonId();
        if (checked == -1) {
            Toast.makeText(AddPrescriptionActivity.this, "Select User Type!", Toast.LENGTH_SHORT).show();
            return;
        } else if (checked == R.id.radioButtonA) {
            diagnosis = "A";
            A++;
        } else if (checked == R.id.radioButtonB) {
            diagnosis = "B";
            B++;
        } else if (checked == R.id.radioButtonOthers) {
            diagnosis = "Others";
            Others++;
        }

        db.collection("statistics").document(appointment.getMonth() + appointment.getYear() + patient.getArea())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Diagnosis", "DocumentSnapshot data: " + document.getData());
                        db.collection("statistics").document(appointment.getMonth() + appointment.getYear() + patient.getArea())
                                .update(diagnosis, FieldValue.increment(1))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Diagnosis", "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Diagnosis", "Error updating document", e);
                                    }
                                });
                    } else {
                        Log.d("Diagnosis", "No such document");

                        //Adding Document
                        final Map<String, Object> statistics = new HashMap<>();
                        statistics.put("A", A);
                        statistics.put("B", B);
                        statistics.put("Others", Others);

                        db.collection("statistics").document(appointment.getMonth() + appointment.getYear() + patient.getArea())
                                .set(statistics)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Diagnosis", "DocumentSnapshot successfully written!");
                                        Toast.makeText(AddPrescriptionActivity.this, "Prescription Added!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Diagnosis", "Error writing document", e);
                                        Toast.makeText(AddPrescriptionActivity.this, "Prescription Not Added!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Log.d("Diagnosis", "get failed with ", task.getException());
                }
            }
        });
    }

    void addPrescription() {

        int checked = DiagnosisRadioGroup.getCheckedRadioButtonId();
        if (checked == -1) {
            Toast.makeText(AddPrescriptionActivity.this, "Select User Type!", Toast.LENGTH_SHORT).show();
            return;
        } else if (checked == R.id.radioButtonA)
            diagnosis = "A";
        else if (checked == R.id.radioButtonB)
            diagnosis = "B";
        else if (checked == R.id.radioButtonOthers)
            diagnosis = "Others";

        final DocumentReference docRef = db.collection("doctors").document(mAuth.getCurrentUser().getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Prescription", "DocumentSnapshot data: " + document.getData());

                        final Map<String, Object> prescription = new HashMap<>();
                        prescription.put("doctorID", mAuth.getCurrentUser().getPhoneNumber());
                        prescription.put("doctorName", document.get("name"));
                        prescription.put("doctorSpeciality", document.get("speciality"));
                        prescription.put("doctorDegree", document.get("degree"));
                        prescription.put("medicines", Medicines);
                        prescription.put("type", diagnosis);
                        prescription.put("date", appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear());

                        db.collection("patients").document(patient.getID())
                                .collection("prescriptions")
                                .document()
                                .set(prescription)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Prescription", "DocumentSnapshot successfully written!");
                                        Toast.makeText(AddPrescriptionActivity.this, "Prescription Added!", Toast.LENGTH_SHORT).show();
                                        flag = 1;
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Prescription", "Error writing document", e);
                                        Toast.makeText(AddPrescriptionActivity.this, "Prescription Not Added!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Log.d("Prescription", "No such document");
                        Toast.makeText(AddPrescriptionActivity.this, "Doctor Not Found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Prescription", "get failed with ", task.getException());
                }
            }
        });

    }

    void deletePApp() {
        Log.d("Prescription", "Patient App");
        db.collection("patients").document(patient.getID())
                .collection("appointments")
                .document(appointment.getdID() + appointment.getpID() + appointment.getDay() + appointment.getMonth() + appointment.getYear())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Appointment Delete", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Appointment Delete", "Error deleting document", e);
                    }
                });
    }

    void deleteDApp() {
        db.collection("doctors").document(mAuth.getCurrentUser().getPhoneNumber())
                .collection(appointment.getDay() + appointment.getMonth() + appointment.getYear())
                .document(appointment.getdID() + appointment.getpID() + appointment.getDay() + appointment.getMonth() + appointment.getYear())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Appointment Delete", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Appointment Delete", "Error deleting document", e);
                    }
                });
    }
}
