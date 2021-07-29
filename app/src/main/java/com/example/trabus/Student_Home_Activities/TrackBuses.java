package com.example.trabus.Student_Home_Activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.trabus.R;
import com.example.trabus.models.Mylocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Objects;

public class TrackBuses extends FragmentActivity implements OnMapReadyCallback, TrackingBuses {

    private  GoogleMap mMap;
    String BusNo;
    Query query;
    private Marker marker;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Mylocation location;
    private final int mintime=1000;
    private final int distance=1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    LatLng latlng1,latlng2;
    double longitude,latitude;
    DatabaseReference reference;
    String id;
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.maps_activity);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        BusNo=getIntent().getStringExtra("busno");
        id=getIntent().getStringExtra("id");
        reference= FirebaseDatabase.getInstance().getReference();
        query=reference.child("User").child("Drivers").child("Location").child(id);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        latlng1=new LatLng(33,73);
        latlng1 = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        marker = mMap.addMarker(new MarkerOptions().position(latlng1).title("You are here!")) ;
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng1));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 18));
        readchanges();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
                getlocationupdates();
                readchanges();
            }
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null)
        {
            savelocation(location);
        }
        else
        {
            Toast.makeText(this,"No location found",Toast.LENGTH_LONG).show();
        }
    }

    private void fetchLocation() {
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
                    supportMapFragment.getMapAsync(TrackBuses.this);
                }
            }
        });

    }
private void savelocation(Location location)
{
    reference.setValue(location);
}
        public void readchanges() {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                       
                            location =snapshot.getValue(Mylocation.class);



                            if (location != null) {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                latlng2 = new LatLng(latitude, longitude);
                                marker = mMap.addMarker(new MarkerOptions().position(latlng2).title(BusNo)) ;
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng2, 18.0f));
                                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
                                double distenace=calculatedistance(latlng1,latlng2);
                                Toast.makeText(getApplicationContext(),"You are "+" "+distenace+" km"+" away from Bus",Toast.LENGTH_LONG).show();

                            }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Driver Not start Sharing Location Yet",Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

    private void getlocationupdates() {
        if(manager!=null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, mintime, distance, (LocationListener) this);
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, mintime, distance, (LocationListener) this);
                } else {
                    Toast.makeText(this, "No provider available", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            }
        }
    }
    public Double calculatedistance(LatLng stratP,LatLng EndP)
    {
        int radius=6371; //earth radius in km
        double lat1=stratP.latitude;
        double lat2=EndP.latitude;
        double long1=stratP.longitude;
        double long2=EndP.longitude;
        double distancelat=Math.toRadians(lat2-lat1);
        double distancelon=Math.toRadians(long2-long1);
        double a=Math.sin(distancelat/2)*Math.sin(distancelat/2)+Math.cos(Math.toRadians(lat1))
                *Math.cos(Math.toRadians(lat2))*Math.sin(distancelon/2)*Math.sin(distancelon/2);
        double c=2*Math.asin(Math.sqrt(a));
        double valueresult=radius*c;
        double km=valueresult/1;
        DecimalFormat format=new DecimalFormat("####");
        int kmInDec = Integer.parseInt(format.format(km));
        double meter=valueresult%1000;
        int meterindec=Integer.valueOf(format.format(meter));
        Log.i("Radius Value", "" + valueresult + "   KM  " + kmInDec
                + " Meter   " + meterindec);
        return  radius*c;

    }
}