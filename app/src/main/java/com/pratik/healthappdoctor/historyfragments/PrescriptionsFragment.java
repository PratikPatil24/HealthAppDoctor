package com.pratik.healthappdoctor.historyfragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.pratik.healthappdoctor.DashActivity;
import com.pratik.healthappdoctor.R;

public class PrescriptionsFragment extends Fragment implements View.OnClickListener {

    MaterialButton DashButton;

    public PrescriptionsFragment() {
        // Required empty public constructor
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

        DashButton = view.findViewById(R.id.btnDash);
        DashButton.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDash:
                Intent i = new Intent(getContext(), DashActivity.class);
                startActivity(i);
                break;
        }
    }
}
