package com.example.trabus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.models.HistoryModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    ArrayList<HistoryModel> list;
    Context context;
    public AdapterHistory(ArrayList<HistoryModel>list,Context context)
    {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_history,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterHistory.ViewHolder holder, int position) {
        final HistoryModel model=list.get(position);
        holder.setDate(model.getDate(),model.getTime(),model.getDiesel(),model.getMileage_Diesel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    protected static class ViewHolder extends RecyclerView.ViewHolder{

        View viewHolder;
        TextView mDate,mTime,Diesel,Mileage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            viewHolder=itemView;

        }
        public void setDate(String date,String time,String petrol,String mileage){
            mDate = (TextView)viewHolder.findViewById(R.id.history_date);
            mTime = (TextView)viewHolder.findViewById(R.id.history_time);
            Diesel = (TextView)viewHolder.findViewById(R.id.history_petrol);
            Mileage = (TextView)viewHolder.findViewById(R.id.history_mileage);

            mTime.setText(time);
            mDate.setText(date);
            Diesel.setText(petrol);
            Mileage.setText(mileage);
        }
    }
}
