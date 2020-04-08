package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.pratik.healthappdoctor.models.Patient;

public class ViewHistoryActivity extends AppCompatActivity {

    Patient patient;

    TabLayout tabLayout;
    TabItem PrescriptionsTab, TreatmentsTab, ReportsTab;

    ViewPager viewPager;

    HistoryPageController historyPageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        Intent i = getIntent();
        patient = (Patient) i.getSerializableExtra("Patient");

        tabLayout = findViewById(R.id.tabLayout);

        PrescriptionsTab = findViewById(R.id.tabPrescriptions);
        TreatmentsTab = findViewById(R.id.tabTreatments);
        ReportsTab = findViewById(R.id.tabReports);

        viewPager = findViewById(R.id.viewPagerViewHistory);

        historyPageController = new HistoryPageController(getSupportFragmentManager(), tabLayout.getTabCount(), patient);
        viewPager.setAdapter(historyPageController);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Toast.makeText(ViewHistoryActivity.this, tab.getPosition() + " " + "Changing", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
