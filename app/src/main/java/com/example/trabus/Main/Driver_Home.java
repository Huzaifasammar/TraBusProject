package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Driver_Home_Activities.ChatActivityDriver;
import com.example.trabus.Driver_Navigation_fragment.DriverReminder;
import com.example.trabus.Driver_Navigation_fragment.HomeFragment;
import com.example.trabus.Driver_Navigation_fragment.ReportSituationFragment;
import com.example.trabus.Driver_Navigation_fragment.Update_Password_Driver;
import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.Rating;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Driver_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    String id;
    Query query;
    FirebaseUser Id;
    NavigationView navigation;
    ImageView notification;
    TextView heading,DriverName,BusNumber,ratings;
    Dialog notificationdialog;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth fAuth;
    View hView;
    NavigationView navigationView;
    FirebaseDatabase database;
    DatabaseReference reference,reference1;
    CircleImageView driverImage;

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
        setContentView(R.layout.activity_driver_home);
        Initialization();
        onClick();
        retrivedata();






        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationdialog = new Dialog(Driver_Home.this);
                notificationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                notificationdialog.setContentView(R.layout.notification_dialog);
                notificationdialog.show();

            }
        });


    }

    // Read data from firebase to show user Info

    public void retrivedata()
    {
        query= reference1.child("User").child("Drivers").child("Rating").child(id);

        ArrayList<Rating> list=new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int counter=0;
                float result=0;
                for (DataSnapshot d : snapshot.getChildren()) {
                    counter++;
                    Rating rating = d.getValue(Rating.class);
                    result+= rating.getRate();
                }
                float result2=result/counter;
                ratings.setText(String.valueOf(result2));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    // Initialize all fields

    public void Initialization()
    {
        drawerLayout=findViewById(R.id.Drawerlayout_driver);
        heading=findViewById(R.id.driver_home);
        navigation=findViewById(R.id.navigation_layout_driver);
        toolbar=findViewById(R.id.toolbar);
        Id=FirebaseAuth.getInstance().getCurrentUser();
        notification=findViewById(R.id.Ivnotification);
        fAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=FirebaseDatabase.getInstance().getReference();
        reference1=FirebaseDatabase.getInstance().getReference();
        navigation.bringToFront();
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation,R.string.close_navigation);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        id=Id.getUid();
        toolbar.setNavigationIcon(R.drawable.menu);
        navigationView=findViewById(R.id.navigation_layout_driver);
        hView=navigationView.getHeaderView(0);
        ratings=hView.findViewById(R.id.driverrating);
    }

    // on Click Listeners

    public void onClick()
    {

        // Read Driver Profile from firebase and uploaad on Header

        reference=database.getReference("User").child("Drivers").child("Profile").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    DriverHelper helper=snapshot.getValue(DriverHelper.class);
                    driverImage=hView.findViewById(R.id.driver_image_nav);
                    DriverName=hView.findViewById(R.id.driver_name_nav);
                    BusNumber=hView.findViewById(R.id.driver_bus_nav);
                    assert helper != null;
                    String FirstName=helper.getFname();
                    String LastName=helper.getLname();
                    String BusNo=helper.getBusno();
                    String Image=helper.getImageurl();
                    BusNumber.setText(BusNo);
                    String Fullname=FirstName+" "+LastName;
                    DriverName.setText(Fullname);
                    Picasso.get().load(Image).into(driverImage);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(Driver_Home.this, "DataBase error.", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigation Item Selected Listener

        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new HomeFragment()).commit();
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                retrivedata();

                item.setChecked(true);
                switch(item.getItemId())
                {
                    case R.id.nav_home_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new HomeFragment()).commit();
                        break;
                    case R.id.nav_change_password_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new Update_Password_Driver()).commit();
                        heading.setText("Update Password");
                        notification.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.nav_report:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new ReportSituationFragment()).commit();
                        heading.setText("Report a Situation");
                        notification.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.nav_logout_driver:
                        fAuth.signOut();
                        startActivity(new Intent(Driver_Home.this, SignIn.class));
                        finish();
                        break;
                    case R.id.nav_setreminder_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new DriverReminder()).commit();
                        heading.setText("Set Reminder");
                        break;
                    case R.id.chatdriver:
                        startActivity(new Intent(Driver_Home.this, ChatActivityDriver.class));
                        finish();
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}