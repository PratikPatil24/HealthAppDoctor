package com.pratik.healthappdoctor.historyfragments;

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
import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.adapters.TreatmentAdapter;
import com.pratik.healthappdoctor.models.Patient;
import com.pratik.healthappdoctor.models.Treatment;

import java.util.ArrayList;

public class TreatmentsFragment extends Fragment {

    Patient patient;

    //Recycler
    TreatmentAdapter madapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Treatment> treatments = new ArrayList<>();

    private FirebaseFirestore db;

    public TreatmentsFragment() {
        // Required empty public constructor
    }

    public TreatmentsFragment(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment", "Treatments");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_treatments, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.recyclerTreatment);
        mLayoutManager = new LinearLayoutManager(view.getContext());

        getData();

        return view;
    }

    void getData() {

        Log.d("Treatments", "Fetching Treatments");
        db.collection("patients").document(patient.getID())
                .collection("treatments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Treatment treatment = new Treatment(document.getId(), document.get("date").toString(), document.get("doctorID").toString(),
                                        document.get("doctorName").toString(), document.get("doctorSpeciality").toString(), document.get("doctorDegree").toString(),
                                        document.get("treatment").toString());
                                treatments.add(treatment);

                                Log.d("Document Fetch", document.getId() + " => " + document.getData());
                            }
                            madapter = new TreatmentAdapter(treatments);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                        } else {
                            Log.d("Document Fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
