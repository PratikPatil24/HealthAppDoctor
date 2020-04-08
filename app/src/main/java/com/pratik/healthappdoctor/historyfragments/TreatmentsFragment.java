package com.pratik.healthappdoctor.historyfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.models.Patient;

public class TreatmentsFragment extends Fragment {

    Patient patient;

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
        return inflater.inflate(R.layout.fragment_treatments, container, false);
    }
}
