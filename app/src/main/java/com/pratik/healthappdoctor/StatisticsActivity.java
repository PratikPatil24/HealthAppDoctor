package com.pratik.healthappdoctor;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatisticsActivity extends AppCompatActivity {

    TextInputEditText AreaTextInput, MonthTextInput, YearTextInput;
    TextView StatisticsTextView;
    MaterialButton GetStatisticsButton;
    String A, B, Others;
    //Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        AreaTextInput = findViewById(R.id.textInputArea);
        MonthTextInput = findViewById(R.id.textInputMonth);
        YearTextInput = findViewById(R.id.textInputYear);

        StatisticsTextView = findViewById(R.id.textViewStatistics);

        GetStatisticsButton = findViewById(R.id.btnGetStats);

        GetStatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (AreaTextInput.getText().toString().equals(null)) {
                    AreaTextInput.setError("Enter Area!");
                    AreaTextInput.requestFocus();
                    return;
                }

                if (MonthTextInput.getText().toString().equals(null) || MonthTextInput.getText().toString().equals("00") || MonthTextInput.getText().toString().length() != 2) {
                    MonthTextInput.setError("Enter Valid Month!");
                    MonthTextInput.requestFocus();
                    return;
                }
                if (YearTextInput.getText().toString().equals(null) || YearTextInput.getText().toString().length() != 4) {
                    YearTextInput.setError("Enter Valid Year!");
                    YearTextInput.requestFocus();
                    return;
                }
                Snackbar.make(v, "Getting Statistics...", Snackbar.LENGTH_SHORT).show();

                db.collection("statistics").document(MonthTextInput.getText().toString() + YearTextInput.getText().toString() + AreaTextInput.getText().toString().toLowerCase()).
                        get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("Statistics", "DocumentSnapshot data: " + document.getData());
                                A = document.get("A").toString();
                                B = document.get("B").toString();
                                Others = document.get("Others").toString();
                                String stats = "Statistics:\n\tA: " + A + "\n\tB: " + B + "\n\tOthers: " + Others;
                                StatisticsTextView.setText(stats);
                            } else {
                                Log.d("Statistics", "No such document");
                                Snackbar.make(v, "No Statistics Available!", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Statistics", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
}
