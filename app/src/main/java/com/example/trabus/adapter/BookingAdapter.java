package com.example.trabus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.Student_Home_Activities.Booking_Trip;
import com.example.trabus.models.BookingHelper;
import com.example.trabus.models.StudentHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.Viewholder> {
    ArrayList<BookingHelper> list;
    Context context;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference,reference1;
    String Email,name,pickdate;

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
        holder.busno.setText(helper.getBusNumber());
        holder.date.setText(helper.getDate());

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
                Button book_now =dialog.findViewById(R.id.book);
                Button book_cancel=dialog.findViewById(R.id.book_cancel);
                TextView Busno,tv_date4;
                Busno=dialog.findViewById(R.id.tv_bus_name);
                tv_date4=dialog.findViewById(R.id.tv_date4);
                Busno.setText(helper.getBusNumber());
                tv_date4.setText(helper.getDate());
                book_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        senddata();
                        Toast.makeText(context,"Succeessfully Booked bus ",Toast.LENGTH_SHORT).show();

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
        TextView busno,date;


        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            busno = itemView.findViewById(R.id.tv_busNo);
            date=itemView.findViewById(R.id.tv_date);
        }
    }
    public void senddata()
    {

        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("User").child("Students").child("Profiles").child(user.getUid());
        reference1=FirebaseDatabase.getInstance().getReference().child("User").child("Students").child("Booking");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    StudentHelper fetch = snapshot.getValue(StudentHelper.class);
                    assert fetch!=null;
                    Email = fetch.getEmail();
                    name = fetch.getFname() + " " + fetch.getLname();
                    HashMap<String, String> putdata = new HashMap<>();
                    putdata.put("Email", Email);
                    putdata.put("Name", name);
                    putdata.put("date", pickdate);
                    reference.setValue(putdata);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
