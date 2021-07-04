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

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.trabus.Driver_Navigation_fragment.ProfileFragment;
import com.example.trabus.R;
import com.example.trabus.Student_Navigation_fragments.Profile;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Driver_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
   Toolbar toolbar;
    NavigationView navigation;

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

        navigation.bringToFront();
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation,R.string.close_navigation);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                item.setChecked(true);
                switch(item.getItemId())
                {
                    case R.id.nav_profile_driver:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_driver_home,new ProfileFragment()).commit();
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