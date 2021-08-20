package com.example.trabus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.Driver_Home_Activities.Schedule;
import com.example.trabus.Driver_Home_Activities.Schedule_Detail;
import com.example.trabus.R;
import com.example.trabus.Student_Home_Activities.TrackBuses;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.ScheduleBusNo;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduledAdapter extends RecyclerView.Adapter<ScheduledAdapter.ViewHolder> {
        ArrayList<ScheduleBusNo> list;
        Context context;

        public ScheduledAdapter(ArrayList<ScheduleBusNo> list, Context context) {
                this.list = list;
                this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public ScheduledAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.busno_sample, parent, false);

                return new ScheduledAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
                final ScheduleBusNo helper = list.get(position);
                holder.busno.setText(helper.getBusno());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(context, Schedule_Detail.class);
                                intent.putExtra("busno", helper.getBusno());
                                context.startActivity(intent);
                        }
                });

        }
        @Override
        public int getItemCount() {
                return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
                TextView busno;

                public ViewHolder(@NonNull @NotNull View itemView) {
                        super(itemView);
                        busno = itemView.findViewById(R.id.tvscheduled);

                }
        }
        public void filterlist (ArrayList<ScheduleBusNo> filterlist)
        {
                list=filterlist;
                notifyDataSetChanged();
        }
}
