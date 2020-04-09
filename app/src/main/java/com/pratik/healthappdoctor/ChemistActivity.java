package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pratik.healthappdoctor.adapters.ChemistAdapter;
import com.pratik.healthappdoctor.models.Appointment;
import com.pratik.healthappdoctor.models.Chemist;
import com.pratik.healthappdoctor.models.Patient;
import com.pratik.healthappdoctor.models.Prescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChemistActivity extends AppCompatActivity {

    Patient patient;
    Prescription prescription;
    Appointment appointment;
    Chemist chemist;
    MaterialButton DoneButton;
    RadioGroup OrderTypeRadioGroup;
    //Recycler
    ChemistAdapter madapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Chemist> chemists = new ArrayList<>();
    String orderType;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist);

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");
        prescription = (Prescription) i.getSerializableExtra("Prescription");
        appointment = (Appointment) i.getSerializableExtra("Appointment");
        Log.d("Prescription", prescription.getID() + " " + prescription.getDoctorName() + " " + prescription.getDoctorDegree() + " " + prescription.getType() + " " + prescription.getMedicines());

        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        OrderTypeRadioGroup = findViewById(R.id.radioGroupOrderType);
        DoneButton = findViewById(R.id.btnDone);

        recyclerView = findViewById(R.id.recyclerChemist);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        getData();

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChemistActivity.this, TreatActivity.class);
                i.putExtra("Patient", patient);
                i.putExtra("Appointment", appointment);
                startActivity(i);
                finish();
            }
        });
    }

    void getData() {

        Log.d("Chemists", "Fetching Chemists");
        db.collection("chemists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                chemist = new Chemist(document.getId(), document.get("name").toString(), document.get("gender").toString(),
                                        document.get("state").toString(), document.get("city").toString(), document.get("area").toString(), document.get("addressline").toString());

                                chemists.add(chemist);
                                Log.d("Document Fetch", document.getId() + " => " + document.getData());
                            }
                            madapter = new ChemistAdapter(chemists);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                            //For Button Click
                            madapter.setOnItemClickListener(new ChemistAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Chemist chemist, int position) {
                                    addtochemist();
                                    addtopatient();
                                }
                            });

                        } else {
                            Log.d("Document Fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    void addtochemist() {
        final int checked = OrderTypeRadioGroup.getCheckedRadioButtonId();
        if (checked == -1) {
            Toast.makeText(ChemistActivity.this, "Select Order Type!", Toast.LENGTH_SHORT).show();
            return;
        } else if (checked == R.id.radioButtonOffline)
            orderType = "Offline";
        else if (checked == R.id.radioButtonOnline)
            orderType = "Online";

        final DocumentReference docRef = db.collection("doctors").document(mAuth.getCurrentUser().getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Prescription", "DocumentSnapshot data: " + document.getData());

                        final Map<String, Object> order = new HashMap<>();
                        order.put("doctorID", mAuth.getCurrentUser().getPhoneNumber());
                        order.put("doctorName", document.get("name"));
                        order.put("doctorSpeciality", document.get("speciality"));
                        order.put("doctorDegree", document.get("degree"));
                        order.put("patientName", patient.getName());
                        order.put("medicines", prescription.getMedicines());
                        order.put("type", prescription.getType());
                        order.put("date", appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear());
                        order.put("orderType", orderType);
                        order.put("status", 1);

                        db.collection("chemists").document(chemist.getId())
                                .collection("activeorders")
                                .document(chemist.getId() + patient.getID() + appointment.getDay() + appointment.getMonth() + appointment.getYear())
                                .set(order)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Prescription", "DocumentSnapshot successfully written!");
                                        Toast.makeText(ChemistActivity.this, "Prescription Sent to Chemist!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Prescription", "Error writing document", e);
                                        Toast.makeText(ChemistActivity.this, "Prescription Not Added!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Log.d("Prescription", "No such document");
                        Toast.makeText(ChemistActivity.this, "Doctor Not Found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Prescription", "get failed with ", task.getException());
                }
            }
        });
    }

    void addtopatient() {
        final int checked = OrderTypeRadioGroup.getCheckedRadioButtonId();
        if (checked == -1) {
            Toast.makeText(ChemistActivity.this, "Select Order Type!", Toast.LENGTH_SHORT).show();
            return;
        } else if (checked == R.id.radioButtonOffline)
            orderType = "Offline";
        else if (checked == R.id.radioButtonOnline)
            orderType = "Online";

        final DocumentReference docRef = db.collection("doctors").document(mAuth.getCurrentUser().getPhoneNumber());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Prescription", "DocumentSnapshot data: " + document.getData());

                        final Map<String, Object> order = new HashMap<>();
                        order.put("doctorID", mAuth.getCurrentUser().getPhoneNumber());
                        order.put("doctorName", document.get("name"));
                        order.put("doctorSpeciality", document.get("speciality"));
                        order.put("doctorDegree", document.get("degree"));
                        order.put("patientName", patient.getName());
                        order.put("medicines", prescription.getMedicines());
                        order.put("type", prescription.getType());
                        order.put("date", appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear());
                        order.put("orderType", orderType);
                        order.put("status", 1);

                        db.collection("patients").document(patient.getID())
                                .collection("orders")
                                .document(chemist.getId() + patient.getID() + appointment.getDay() + appointment.getMonth() + appointment.getYear())
                                .set(order)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Prescription", "DocumentSnapshot successfully written!");
                                        Toast.makeText(ChemistActivity.this, "Prescription Sent to Patient!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Prescription", "Error writing document", e);
                                        Toast.makeText(ChemistActivity.this, "Prescription Not Added!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Log.d("Prescription", "No such document");
                        Toast.makeText(ChemistActivity.this, "Doctor Not Found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Prescription", "get failed with ", task.getException());
                }
            }
        });
    }
}
