package com.example.trabus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    ArrayList<NotificationModel> list;
    Context context;
    public NotificationAdapter(ArrayList<NotificationModel>list,Context context)
    {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notification,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.ViewHolder holder, int position) {

               final NotificationModel model=list.get(position);
               holder.notifiaction.setText(model.getNotification());
               holder.date.setText(model.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

           TextView notifiaction,date;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            notifiaction=itemView.findViewById(R.id.tvNotification);
            date=itemView.findViewById(R.id.tvdatenoti);



        }
    }
}
