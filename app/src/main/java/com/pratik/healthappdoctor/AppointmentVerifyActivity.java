package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.healthappdoctor.models.Appointment;
import com.pratik.healthappdoctor.models.Patient;

public class AppointmentVerifyActivity extends AppCompatActivity {

    Patient patient;
    Appointment appointment;

    TextView PhoneNumberTextView;
    TextInputEditText OTPTextInput;
    MaterialButton VerifyButton;

    String otp;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_verify);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        appointment = (Appointment) i.getSerializableExtra("Appointment");
//        Toast.makeText(this, appointment.getpID() + mAuth.getCurrentUser().getPhoneNumber(), Toast.LENGTH_SHORT).show();

        PhoneNumberTextView = findViewById(R.id.textViewPhoneNumber);
        OTPTextInput = findViewById(R.id.textInputEditTextOTP);
        VerifyButton = findViewById(R.id.btnVerify);

        PhoneNumberTextView.setText(appointment.getpID());

        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                otp = OTPTextInput.getText().toString().trim();

                if (otp.equals(null)) {
                    OTPTextInput.setError("Enter OTP!");
                    OTPTextInput.requestFocus();
                    return;
                }

                DocumentReference docRef = db.collection("patients").document(appointment.getpID());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("Patient Fetch", "DocumentSnapshot data: " + document.getData());
                                patient = new Patient(document.getId(), document.getId(), document.get("name").toString(),
                                        document.get("state").toString(), document.get("city").toString(), document.get("area").toString(), document.get("addressline").toString(),
                                        document.get("gender").toString(), Float.parseFloat(document.get("weight").toString()), Float.parseFloat(document.get("height").toString()),
                                        Integer.parseInt(document.get("age").toString()));

                                Snackbar.make(v, "Verifying!", Snackbar.LENGTH_SHORT).show();
                                //Verifying
                                if (otp.equals(appointment.getOtp())) {
                                    Intent i = new Intent(AppointmentVerifyActivity.this, TreatActivity.class);
                                    i.putExtra("Patient", patient);
                                    i.putExtra("Appointment", appointment);
                                    startActivity(i);
                                    finish();
                                } else {
                                    OTPTextInput.setError("Incorrect OTP!");
                                    OTPTextInput.requestFocus();
                                }

                            } else {
                                Log.d("Patient Fetch", "No such document");
                            }
                        } else {
                            Log.d("Patient Fetch", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
}


