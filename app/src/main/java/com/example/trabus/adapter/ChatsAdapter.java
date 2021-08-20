package com.example.trabus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.Main.ChatsDetailActivity;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.StudentHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {
    ArrayList<DriverHelper> list;
    Context context;
    public ChatsAdapter(ArrayList<DriverHelper> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DriverHelper users=list.get(position);
        Picasso.get().load(users.getImageurl()).placeholder(R.drawable.ic_profile).into(holder.image);
        holder.UserName.setText(users.getUsername());
        holder.LastMessage.setText(users.getBusno());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatsDetailActivity.class);
                intent.putExtra("id",users.getId());
                intent.putExtra("profilepic",users.getImageurl());
                intent.putExtra("username",users.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView UserName,LastMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profilepic);
            UserName=itemView.findViewById(R.id.username);
            LastMessage=itemView.findViewById(R.id.lastmessage);
        }
    }
}
