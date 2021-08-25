package com.example.trabus;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsetsAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;
import com.example.trabus.Student_Home_Activities.TrackBuses;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.Mylocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    FirebaseAuth firebaseAuth;
    private DatabaseReference reference, db;
    private static final int REQUEST_CODE = 101;
    private LocationManager manager;
    private final int mintime = 1000;
    private final int distance = 1;
    private Marker marker;
    Mylocation location;
    TextView speed, arrival, arrivalTime;
    String BusNo;
    Location currentLocation;
    LatLng latlng1;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseUser CurrentUser;
    double longitude, latitude;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.maps_activity);

        //Initilization
        dialog=new ProgressDialog(MapsActivity.this);
        dialog.setMessage("please wait while we fetch your location");
        dialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        speed = findViewById(R.id.speedcal);
        arrival = findViewById(R.id.arrival);
        arrival.setVisibility(View.INVISIBLE);
        arrivalTime = findViewById(R.id.arrivalTime);
        arrivalTime.setVisibility(View.INVISIBLE);
        reference = FirebaseDatabase.getInstance().getReference("User").child("Drivers").child("Location").child(CurrentUser.getUid());
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fetchLocation();
        getlocationupdates();
        Button EndRoute;
        EndRoute = findViewById(R.id.endroute);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Fetching Bus No ----------------------------------------------------

        db = database.getReference("User").child("Drivers").child("Profile").child(CurrentUser.getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DriverHelper helper = snapshot.getValue(DriverHelper.class);
                    assert helper != null;
                    BusNo = helper.getBusno();
                    marker = mMap.addMarker(new MarkerOptions().position(latlng1).title(BusNo));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "DataBase Error", Toast.LENGTH_LONG).show();
            }
        });


        // End Route ClickListner -------------------------------------------------------------

        EndRoute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(MapsActivity.this);
                dialog.setTitle("End Route");
                dialog.setMessage("We Ending Your Route");
                dialog.show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query query = ref.child("User").child("Drivers").child("Location").child(CurrentUser.getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                            onPause();
                            dialog.dismiss();
                            startActivity(new Intent(MapsActivity.this, Driver_Home.class));
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "DataBase Error", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    //   On Permission Granted ---------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == 101) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (REQUEST_CODE == 101) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getlocationupdates();
                    fetchLocation();
                }
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        dialog.show();
        mMap = googleMap;
        latlng1 = new LatLng(33, 73);
        marker = mMap.addMarker(new MarkerOptions().position(latlng1).title(BusNo));
        readchanges();


    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        savelocation(location);
    }

    private void savelocation(Location location) {
        reference.setValue(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();


    }

    // Get Location Updates ---------------------------------------------------------

    private void getlocationupdates() {
        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, mintime, distance, this);
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, mintime, distance, this);
                } else {

                    Toast.makeText(this, "No provider available", Toast.LENGTH_LONG).show();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);

            }
        }
    }

    // Read Data from Database ----------------------------------------------------

    private void readchanges() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dialog.dismiss();
                    location = snapshot.getValue(Mylocation.class);
                    assert location != null;
                    speed.setText(String.format("%.0f", location.getSpeed() * 1.609344) +" km/hr");;
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        latlng1 = new LatLng(latitude, longitude);
                        marker.setPosition(latlng1);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 16.0f));
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

// fetch location ---------------------------------------------------------------------------------

    public void fetchLocation() {

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });

    }
}

