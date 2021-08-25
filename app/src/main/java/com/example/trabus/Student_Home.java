package com.example.trabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trabus.Login.SignIn;
import com.example.trabus.Main.ChatActivity;
import com.example.trabus.Main.ChatsDetailActivity;
import com.example.trabus.Student_Navigation_fragments.ChangePassword;
import com.example.trabus.Student_Navigation_fragments.Complaint;
import com.example.trabus.Student_Navigation_fragments.Contact;
import com.example.trabus.Student_Navigation_fragments.Home;
import com.example.trabus.Student_Navigation_fragments.Reminder;
import com.example.trabus.models.StudentHelper;

import android.app.Dialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigation;
    TextView heading,StudentName,Email;
    ImageView notification;
    CircleImageView StudentImage;
    Dialog dialog_notice;
    FirebaseUser user;
    String id;
    FirebaseAuth fAuth;

    FirebaseDatabase database;

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.activity_student_home);
        drawerLayout=findViewById(R.id.DrawerlayoutStudent);
        navigation=findViewById(R.id.navigation_layout_student);
        toolbar=findViewById(R.id.toolbar_student);
        heading=findViewById(R.id.studenthome);
        fAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        notification=findViewById(R.id.iv_notification_std);
        navigation.bringToFront();
        retrivedata();
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar ,R.string.open_navigation,R.string.close_navigation);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Home()).commit();
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                item.setChecked(true);
                switch(item.getItemId())
                {


                    case R.id.nav_home_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Home()).commit();
                        break;
                    case R.id.nav_setreminder_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Reminder()).commit();
                        heading.setText("Set Reminder");
                        notification.setVisibility(View.INVISIBLE);
                        break;
                   
                    case R.id.nav_change_password_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new ChangePassword()).commit();
                        heading.setText("Change Password");
                        notification.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.nav_logout_student:
                        fAuth.signOut();
                        startActivity(new Intent(Student_Home.this, SignIn.class));
                        finish();
                        break;
                    case R.id.nav_complaint:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Complaint()).commit();
                        heading.setText("Complaints");
                        notification.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.nav_contact:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Contact()).commit();
                        heading.setText("Contact with Driver");
                        notification.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.studentchat:
                        startActivity(new Intent(Student_Home.this, ChatActivity.class));
                        finish();
                        break;

                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // notification button listener -----------------------------------------

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_notice=new Dialog(Student_Home.this);
                dialog_notice.setContentView(R.layout.student_notification_dialog);
                dialog_notice.getWindow().setBackgroundDrawable(getDrawable(R.drawable.book_bus_background));
                dialog_notice.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_notice.setCancelable(true);
                dialog_notice.getWindow().getAttributes().windowAnimations =R.style.animation;
                dialog_notice.show();
            }
        });


    }

    // Read data from database --------------------------------------------------

    public void retrivedata() {
         id = fAuth.getUid();
        DatabaseReference reference = database.getReference("User").child("Students").child("Profiles").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StudentHelper helper = snapshot.getValue(StudentHelper.class);
                    NavigationView navigationView = findViewById(R.id.navigation_layout_student);
                    View hView = navigationView.getHeaderView(0);
                    StudentImage = hView.findViewById(R.id.student_image_nav);
                    StudentName = hView.findViewById(R.id.student_name_nav);
                    Email = hView.findViewById(R.id.student_email_nav);
                    assert helper != null;
                    String FirstName = helper.getFname();
                    String LastName = helper.getLname();
                    String email = helper.getEmail();
                    String Image = helper.getImageurl();
                    String Fullname = FirstName + " " + LastName;
                    StudentName.setText(Fullname);
                    Email.setText(email);
                    Picasso.get().load(Image).into(StudentImage);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(Student_Home.this, "DataBase error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

