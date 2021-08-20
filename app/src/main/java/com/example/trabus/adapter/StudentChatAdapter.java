package com.example.trabus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.Main.ChatsDetailActivity;
import com.example.trabus.R;
import com.example.trabus.models.StudentHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentChatAdapter extends RecyclerView.Adapter<StudentChatAdapter.ViewHolderStudents> {
    ArrayList<StudentHelper> list;
    Context context;
    public StudentChatAdapter(ArrayList<StudentHelper> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public StudentChatAdapter.ViewHolderStudents onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_student_user,parent,false);
        return new ViewHolderStudents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderStudents holder, int position) {
        final StudentHelper users=list.get(position);
        FirebaseUser ID= FirebaseAuth.getInstance().getCurrentUser();
        Picasso.get().load(users.getImageurl()).placeholder(R.drawable.ic_profile).into(holder.image);
        holder.UserName.setText(users.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatsDetailActivity.class);
                intent.putExtra("id",ID);
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
    public  class ViewHolderStudents extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView UserName;

        public ViewHolderStudents(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profilepicstudent);
            UserName=itemView.findViewById(R.id.usernamestudent);
        }
    }
}
