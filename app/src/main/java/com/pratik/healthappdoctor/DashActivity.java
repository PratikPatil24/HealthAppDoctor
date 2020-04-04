package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashActivity extends AppCompatActivity {

    //Firebase Auth
    private FirebaseAuth mAuth;

    TextView DateTextView;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        mAuth = FirebaseAuth.getInstance();

        DateTextView = findViewById(R.id.textViewDate);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        SimpleDateFormat df2 = new SimpleDateFormat("ddMMYYYY");
        String date2 = df2.format(c);

        SimpleDateFormat df3 = new SimpleDateFormat("dd");
        String day = df3.format(c);

        SimpleDateFormat df4 = new SimpleDateFormat("MM");
        String month = df4.format(c);

        SimpleDateFormat df5 = new SimpleDateFormat("YYYY");
        String year = df5.format(c);

        DateTextView.setText(formattedDate + " " + date2 + " " + day + " " + month + " " + year + " " + mAuth.getCurrentUser().getPhoneNumber());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userprofile) {
            startActivity(new Intent(getBaseContext(), ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
}

