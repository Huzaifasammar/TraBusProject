package com.example.trabus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    ArrayList<DriverHelper> list;
    Context context;
    public ContactsAdapter(ArrayList<DriverHelper>list,Context context)
    {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_contact,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactsAdapter.ViewHolder holder, int position) {
     final DriverHelper helper=list.get(position);
     holder.busno.setText(helper.getBusno());
     String Fullname=helper.getFname()+" "+helper.getLname();
     holder.name.setText(Fullname);
     holder.contact.setText(helper.getPhonenumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
       TextView busno,name,contact;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            busno=itemView.findViewById(R.id.busno);
            name=itemView.findViewById(R.id.drivername);
            contact=itemView.findViewById(R.id.drivercontact);

        }
    }
}
