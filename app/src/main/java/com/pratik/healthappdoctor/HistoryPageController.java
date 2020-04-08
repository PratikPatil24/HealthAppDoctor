package com.pratik.healthappdoctor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pratik.healthappdoctor.historyfragments.PrescriptionsFragment;
import com.pratik.healthappdoctor.historyfragments.ReportsFragment;
import com.pratik.healthappdoctor.historyfragments.TreatmentsFragment;
import com.pratik.healthappdoctor.models.Patient;

public class HistoryPageController extends FragmentPagerAdapter {

    int tabcount;
    Patient patient;

    public HistoryPageController(@NonNull FragmentManager fm, int tabcount, Patient patient) {
        super(fm);
        this.tabcount = tabcount;
        this.patient = patient;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PrescriptionsFragment(patient);
            case 1:
                return new TreatmentsFragment(patient);
            case 2:
                return new ReportsFragment(patient);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
