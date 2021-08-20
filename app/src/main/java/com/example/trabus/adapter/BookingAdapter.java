package com.example.trabus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.Student_Home_Activities.Booking_Trip;
import com.example.trabus.Student_Home_Activities.TrackBuses;
import com.example.trabus.models.BookingHelper;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.auth.FirebaseAuth;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Booking_Trip.class);
                intent.putExtra("busno",helper.getBusno());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView busno;


        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            busno = itemView.findViewById(R.id.tv_busnobooking);

        }
    }
}
