package com.example.trabus.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.trabus.Driver_Navigation_fragment.HomeFragment;
import com.example.trabus.Driver_Navigation_fragment.ProfileFragment;
import com.example.trabus.Driver_Navigation_fragment.Update_Password_Driver;
import com.example.trabus.R;
import com.example.trabus.Student_Navigation_fragments.Profile;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Driver_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
   Toolbar toolbar;
    NavigationView navigation;
    ImageView notification;
    Dialog notificationdialog;

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
        navigation=findViewById(R.id.navigation_layout_driver);
        toolbar=findViewById(R.id.toolbar);
        notification=findViewById(R.id.Ivnotification);

        navigation.bringToFront();
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation,R.string.close_navigation);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);





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
                item.setChecked(true);
                switch(item.getItemId())
                {
                    case R.id.nav_home_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new HomeFragment()).commit();
                        break;
                    case R.id.nav_profile_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new ProfileFragment()).commit();
                        break;
                    case R.id.nav_change_password_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new Update_Password_Driver()).commit();
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