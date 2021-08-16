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

import com.example.trabus.Driver_Navigation_fragment.DriverReminder;
import com.example.trabus.Driver_Navigation_fragment.HomeFragment;
import com.example.trabus.Driver_Navigation_fragment.ReportSituationFragment;
import com.example.trabus.Driver_Navigation_fragment.Update_Password_Driver;
import com.example.trabus.Login.SignIn;
import com.example.trabus.R;
import com.example.trabus.models.DriverHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Driver_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    String id;
    NavigationView navigation;
    ImageView notification;
    TextView heading,DriverName,BusNumber;
    Dialog notificationdialog;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
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
        drawerLayout=findViewById(R.id.Drawerlayout_driver);
        heading=findViewById(R.id.driver_home);
        navigation=findViewById(R.id.navigation_layout_driver);
        toolbar=findViewById(R.id.toolbar);
        notification=findViewById(R.id.Ivnotification);
        fAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=FirebaseDatabase.getInstance().getReference();
        navigation.bringToFront();
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation,R.string.close_navigation);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);
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
                        startActivity(new Intent(Driver_Home.this,ChatsActivity.class));
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
    public void retrivedata()
    {
        id=fAuth.getUid();
        reference=database.getReference("User").child("Drivers").child("Profile").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              if(snapshot.exists())
              {
                  DriverHelper helper=snapshot.getValue(DriverHelper.class);
                  NavigationView navigationView=findViewById(R.id.navigation_layout_driver);
                  View hView=navigationView.getHeaderView(0);
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

    }
}