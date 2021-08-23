package com.example.trabus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.Driver_Home_Activities.ChatDetailActivityDriver;
import com.example.trabus.Main.ChatsDetailActivity;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.StudentHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.ViewHolder> {
    ArrayList<StudentHelper> list;
    Context context;
    public ChatDetailAdapter(ArrayList<StudentHelper> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public ChatDetailAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_student_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatDetailAdapter.ViewHolder holder, int position) {
        final StudentHelper users=list.get(position);
        Picasso.get().load(users.getImageurl()).placeholder(R.drawable.ic_profile).into(holder.image);
        holder.UserName.setText(users.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatDetailActivityDriver.class);
                intent.putExtra("profilepic",users.getImageurl());
                intent.putExtra("username",users.getUsername());
                intent.putExtra("id",users.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView UserName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profilepicstudent);
            UserName=itemView.findViewById(R.id.usernamestudent);
        }
    }
}
