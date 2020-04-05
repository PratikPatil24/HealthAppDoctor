package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentVerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_verify);

        Intent i = getIntent();
        String id = i.getStringExtra("ID");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
}
