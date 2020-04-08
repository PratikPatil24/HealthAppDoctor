package com.pratik.healthappdoctor.historyfragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pratik.healthappdoctor.PrescriptionActivity;
import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.adapters.PrescriptionAdapter;
import com.pratik.healthappdoctor.models.Patient;
import com.pratik.healthappdoctor.models.Prescription;

import java.util.ArrayList;

public class PrescriptionsFragment extends Fragment implements View.OnClickListener {

//    MaterialButton DashButton;

    //Recycler
    PrescriptionAdapter madapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Prescription> prescriptions = new ArrayList<>();

    Patient patient;

    //Firebase Firestore
    private FirebaseFirestore db;

    public PrescriptionsFragment() {
        // Required empty public constructor
    }

    public PrescriptionsFragment(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment", "Prescription");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prescriptions, container, false);

//        DashButton = view.findViewById(R.id.btnDash);
        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.recyclerPrescriptions);
        mLayoutManager = new LinearLayoutManager(view.getContext());

        getData();
        //DashButton.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnDash:
//                Intent i = new Intent(getContext(), DashActivity.class);
//                startActivity(i);
//                break;
//        }
    }

    void getData() {

        Log.d("Prescription", "Fetching Prescriptions");
        db.collection("patients").document(patient.getID())
                .collection("prescriptions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Prescription prescription = new Prescription(document.getId(), document.get("doctorID").toString(), document.get("doctorName").toString(),
                                        document.get("doctorSpeciality").toString(), document.get("doctorDegree").toString(), document.get("date").toString(),
                                        document.get("type").toString(), document.get("medicines").toString());
                                prescriptions.add(prescription);

                                Log.d("Document Fetch", document.getId() + " => " + document.getData());
                            }
                            madapter = new PrescriptionAdapter(prescriptions);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                            //For Button Click
                            madapter.setOnItemClickListener(new PrescriptionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Prescription prescription, int position) {
                                    //Toast.makeText(BookAppointmentActivity.this, doctor.getPhoneno(), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getContext(), PrescriptionActivity.class);
                                    i.putExtra("Prescription", prescription);
                                    startActivity(i);
                                }
                            });

                        } else {
                            Log.d("Document Fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
