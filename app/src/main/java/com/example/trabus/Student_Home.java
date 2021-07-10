package com.example.trabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.trabus.Driver_Navigation_fragment.ProfileFragment;
import com.example.trabus.Student_Navigation_fragments.ChangePassword;
import com.example.trabus.Student_Navigation_fragments.Contact;
import com.example.trabus.Student_Navigation_fragments.Home;
import com.example.trabus.Student_Navigation_fragments.Logout;
import com.example.trabus.Student_Navigation_fragments.Profile;
import com.example.trabus.Student_Navigation_fragments.Reminder;
import com.google.android.material.snackbar.Snackbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Student_Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigation;
    TextView heading;
    ImageView notification;

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
        notification=findViewById(R.id.ivnotificationstd);
        navigation.bringToFront();

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
                    case R.id.nav_profile_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Profile()).commit();
                        heading.setText("Update Profile");
                        notification.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.nav_setreminder_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Reminder()).commit();
                        heading.setText("Set Reminder");
                        notification.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.nav_logout_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Logout()).commit();
                        heading.setText("Logout");
                        notification.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.nav_change_password_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new ChangePassword()).commit();
                        heading.setText("Change Password");
                        notification.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.nav_home_student:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Home()).commit();
                        heading.setText("Student Home");
                        notification.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.nav_contact:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RL_student_home,new Contact()).commit();
                        heading.setText("Contact with Driver");
                        notification.setVisibility(View.INVISIBLE);

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

