package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pratik.healthappdoctor.adapters.AppointmentAdapter;
import com.pratik.healthappdoctor.models.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashActivity extends AppCompatActivity {

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Firebase Firestore
    private FirebaseFirestore db;

    TextInputEditText DayTextInput, MonthTextInput, YearTextInput;
    TextView DateTextView;
    MaterialButton GetAppointmentButton;

    String doctorid, date2, day, month, year;

    //Recycler
    AppointmentAdapter madapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Appointment> appointments = new ArrayList<>();
    private CollectionReference AppointRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        doctorid = mAuth.getCurrentUser().getPhoneNumber() + "d";

        DayTextInput = findViewById(R.id.textInputDay);
        MonthTextInput = findViewById(R.id.textInputMonth);
        YearTextInput = findViewById(R.id.textInputYear);
        DateTextView = findViewById(R.id.textViewDate);

        GetAppointmentButton = findViewById(R.id.btnGetAppointment);

        recyclerView = findViewById(R.id.recyclerAppointments);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        SimpleDateFormat df2 = new SimpleDateFormat("ddMMYYYY");
        date2 = df2.format(c);

        SimpleDateFormat df3 = new SimpleDateFormat("dd");
        day = df3.format(c);

        SimpleDateFormat df4 = new SimpleDateFormat("MM");
        month = df4.format(c);

        SimpleDateFormat df5 = new SimpleDateFormat("YYYY");
        year = df5.format(c);

        DateTextView.setText("Date: " + day + "/" + month + "/" + year);
        DayTextInput.setText(day);
        MonthTextInput.setText(month);
        YearTextInput.setText(year);

        GetAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointments.clear();
                day = DayTextInput.getText().toString();
                month = MonthTextInput.getText().toString();
                year = YearTextInput.getText().toString();

                DateTextView.setText("Date: " + day + "/" + month + "/" + year);
                Snackbar.make(v, "Getting Appointments for " + day + "/" + month + "/" + year, Snackbar.LENGTH_SHORT).show();
                getData();
            }
        });

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

    void getData() {
        Log.d("GetData", "Getting Appointments for " + day + "/" + month + "/" + year + " " + doctorid);
        db.collection("doctors").document(doctorid).collection(day + month + year)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Appointment appointment = new Appointment(count, document.getId(), document.get("pID").toString(), document.get("dID").toString(),
                                        document.get("doctorName").toString(), document.get("speciality").toString(), document.get("degree").toString(), document.get("address").toString(),
                                        document.get("day").toString(), document.get("month").toString(), document.get("year").toString(), document.get("otp").toString());
                                appointments.add(appointment);
                                Log.d("Document Fetch", document.getId() + " => " + document.getData());
                                count++;
                            }
                            madapter = new AppointmentAdapter(appointments);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(madapter);
                            madapter.notifyDataSetChanged();

                            //For Button Click
                            madapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Appointment appointment, int position) {
                                    Intent i = new Intent(DashActivity.this, AppointmentVerifyActivity.class);
                                    i.putExtra("Appointment", appointment);
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

