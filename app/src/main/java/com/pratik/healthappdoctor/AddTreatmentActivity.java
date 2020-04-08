package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.healthappdoctor.models.Appointment;
import com.pratik.healthappdoctor.models.Patient;

import java.util.HashMap;
import java.util.Map;

public class AddTreatmentActivity extends AppCompatActivity {

    TextView NameTextView, AgeTextView, GenderTextView, WeightTextView, HeightTextView;
    TextInputEditText TreatmentTextInput;

    Patient patient;
    Appointment appointment;
    MaterialButton AddTreatmentButton;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);

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

        TreatmentTextInput = findViewById(R.id.textInputTreatment);

        AddTreatmentButton = findViewById(R.id.btnAddTreatment);

        NameTextView.setText(patient.getName());
        AgeTextView.setText(String.valueOf(patient.getAge()));
        GenderTextView.setText(patient.getGender());
        WeightTextView.setText(String.valueOf(patient.getWeight()));
        HeightTextView.setText(String.valueOf(patient.getHeight()));

        AddTreatmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TreatmentTextInput.getText().toString().equals(null)) {
                    TreatmentTextInput.setError("Enter Treatment!");
                    TreatmentTextInput.requestFocus();
                    return;
                }
                addPrescription();
                deletePApp();
                deleteDApp();

                Intent i = new Intent(AddTreatmentActivity.this, TreatActivity.class);
                i.putExtra("Patient", patient);
                startActivity(i);
                finish();
            }
        });
    }

    void addPrescription() {

        final DocumentReference docRef = db.collection("doctors").document(mAuth.getCurrentUser().getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Treatment", "DocumentSnapshot data: " + document.getData());

                        final Map<String, Object> treatment = new HashMap<>();
                        treatment.put("doctorID", mAuth.getCurrentUser().getPhoneNumber());
                        treatment.put("doctorName", document.get("name"));
                        treatment.put("doctorSpeciality", document.get("speciality"));
                        treatment.put("doctorDegree", document.get("degree"));
                        treatment.put("treatment", TreatmentTextInput.getText().toString());
                        treatment.put("date", appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear());

                        db.collection("patients").document(patient.getID())
                                .collection("treatments")
                                .document()
                                .set(treatment)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Treatment", "DocumentSnapshot successfully written!");
                                        Toast.makeText(AddTreatmentActivity.this, "Prescription Added!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Treatment", "Error writing document", e);
                                        Toast.makeText(AddTreatmentActivity.this, "Prescription Not Added!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Log.d("Treatment", "No such document");
                        Toast.makeText(AddTreatmentActivity.this, "Doctor Not Found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Treatment", "get failed with ", task.getException());
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
