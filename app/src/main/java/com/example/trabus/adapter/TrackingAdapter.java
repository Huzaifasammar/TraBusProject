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
import com.example.trabus.Student_Home_Activities.TrackBuses;
import com.example.trabus.models.DriverHelper;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Objects;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder>{
    ArrayList<DriverHelper> list;
    Context context;
    FirebaseAuth auth;
    public TrackingAdapter(ArrayList<DriverHelper>list,Context context)
    {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public TrackingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_bus_tracking,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrackingAdapter.ViewHolder holder, int position) {
        final DriverHelper helper=list.get(position);
        holder.busno.setText(helper.getBusno());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TrackBuses.class);
                intent.putExtra("busno",helper.getBusno());
                intent.putExtra("id",helper.getId());
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
        String id;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            busno = itemView.findViewById(R.id.busnotracking);
            auth=FirebaseAuth.getInstance();
            id= Objects.requireNonNull(auth.getCurrentUser()).getUid();

        }
    }
    public void filterlist (ArrayList<DriverHelper> filterlist)
    {
        list=filterlist;
        notifyDataSetChanged();
    }

}
