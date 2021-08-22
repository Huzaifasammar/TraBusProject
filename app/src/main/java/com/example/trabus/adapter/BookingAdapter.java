package com.example.trabus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.Student_Home_Activities.Booking_Trip;
import com.example.trabus.Student_Home_Activities.TrackBuses;
import com.example.trabus.models.BookingHelper;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.Viewholder> {
    ArrayList<BookingHelper> list;
    Context context;
    FirebaseAuth auth;

    public BookingAdapter(ArrayList<BookingHelper> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public BookingAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_bottom_sheet,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookingAdapter.Viewholder holder, int position) {
        final BookingHelper helper=list.get(position);
        holder.busno.setText(helper.getBusno());
        holder.trip.farecalculation();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.activity_book_bus);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.book_bus_background));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations =R.style.animation;
                dialog.show();
               // TextView tvbus;
               // tvbus=dialog.findViewById(R.id.tv_bus_name);
               // tvbus.setText(holder.busno.toString());
                Button book_now =dialog.findViewById(R.id.book);
                Button book_cancel=dialog.findViewById(R.id.book_cancel);
                book_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.trip.sendData();
                        dialog.dismiss();
                    }
                });
                book_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView busno,pick,drop,fare;
        Booking_Trip trip=new Booking_Trip();


        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            busno = itemView.findViewById(R.id.tv_busnobooking);
            pick=itemView.findViewById(R.id.pickup);
            drop=itemView.findViewById(R.id.drop);
            fare=itemView.findViewById(R.id.fare);
        }
    }
}
