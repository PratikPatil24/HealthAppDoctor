package com.pratik.healthappdoctor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pratik.healthappdoctor.R;
import com.pratik.healthappdoctor.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private List<Appointment> appointments;

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public AppointmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.AppointmentIDTextView.setText(String.valueOf(appointment.getNumber()));
        holder.PatientIDTextView.setText(appointment.getpID());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView AppointmentIDTextView;
        TextView PatientIDTextView;

        MyViewHolder(View view) {
            super(view);
            AppointmentIDTextView = itemView.findViewById(R.id.textViewMFGDate);
            PatientIDTextView = itemView.findViewById(R.id.textViewstock);
        }
    }
}


//public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {
//
//    private List<ProductMFG> stocks;
//
//    public StockAdapter(List<ProductMFG> stocks) {
//        this.stocks = stocks;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.stock_card_layout, parent, false);
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        ProductMFG stock = stocks.get(position);
//        holder.mfg.setText(stock.getDay() + "/" + stock.getMonth() + "/" + stock.getYear());
//        holder.stock.setText(String.valueOf(stock.getStock()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return stocks.size();
//    }
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView mfg;
//        TextView stock;
//
//        MyViewHolder(View view) {
//            super(view);
//            mfg = itemView.findViewById(R.id.textViewMFGDate);
//            stock = itemView.findViewById(R.id.textViewstock);
//        }
//    }
//}
