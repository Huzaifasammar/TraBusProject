package com.example.trabus.Student_Home_Activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.MapsActivity;
import com.example.trabus.R;
import com.example.trabus.Student_Home;
import com.example.trabus.models.Mylocation;
import com.example.trabus.models.Rating;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class TrackBuses extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private  GoogleMap mMap;
    String BusNo,id;
    Query query;
    Button EndRoute;
    SimpleRatingBar ratingBar;
    TextView textView,busSpeed,arrivalTime;
    Marker marker,marker1;
    Dialog rating;
    Button btnreview,btnskip;
    double distenace;
    MarkerOptions options;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Mylocation location;
    private final int mintime=1000;
    private final int distance=1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    LatLng latlng1,latlng2,latLng;
    double longitude,latitude;
    DatabaseReference reference;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.Green));
        setContentView(R.layout.maps_activity);
        Initilization();
        onClick();
        fetchLocation();
        getlocationupdates();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        latLng=new LatLng(33,73);
        marker1=mMap.addMarker(options.position(latLng).title("Location Checking"));
        marker=mMap.addMarker(options.position(latLng));
        latlng1 = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        marker.setPosition(latlng1);
        marker.setTitle("You are Here");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng1));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 10.0f));
        mMap.addCircle(new CircleOptions().fillColor(Color.argb(150,100,150,100)).radius(3.0).center(latlng1).strokeWidth(2f));

        readchanges();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
                getlocationupdates();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.removeUpdates(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.removeUpdates(this);
    }

    @Override
        public void onLocationChanged(@NonNull Location location) {

            readchanges();
            getlocationupdates();
        }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(getApplicationContext(),"Please Check your Internet Connection",Toast.LENGTH_LONG).show();

    }
    // Initialize all fields

        public void Initilization()
        {
            database=FirebaseDatabase.getInstance();
            auth=FirebaseAuth.getInstance();
            options=new MarkerOptions();
            rating=new Dialog(TrackBuses.this);
            rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
            rating.setContentView(R.layout.review);
            btnreview=rating.findViewById(R.id.btnrate);
            btnskip=rating.findViewById(R.id.btnskip);
            ratingBar=rating.findViewById(R.id.ratingbar);
            textView=rating.findViewById(R.id.ratingtext);
            EndRoute=findViewById(R.id.endroute);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            manager=(LocationManager)getSystemService(LOCATION_SERVICE);
            BusNo=getIntent().getStringExtra("busno");
            id=getIntent().getStringExtra("id");
            reference= FirebaseDatabase.getInstance().getReference();
            query=reference.child("User").child("Drivers").child("Location").child(id);
            busSpeed=findViewById(R.id.speedcal);
            arrivalTime=findViewById(R.id.arrivalTime);
        }
        // click Listeners

        public void onClick()
        {
            // Ratingbar Change Listener

            ratingBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                    textView.setText(" "+rating);
                }
            });

            // Skip Button Listener

            btnskip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TrackBuses.this,Student_Home.class));
                    finish();
                }
            });

            // End Route Listener

            EndRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rating.show();
                    onPause();
                }
            });

            // Review Button Listener

            btnreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TrackBuses.this,"Your response has been recorded",Toast.LENGTH_SHORT).show();
                    float text= Float.parseFloat(textView.getText().toString());
                    FirebaseUser CurrentUser=FirebaseAuth.getInstance().getCurrentUser();
                    Rating helper=new Rating(text);
                    reference.child("User").child("Drivers").child("Rating").child(id).child(CurrentUser.getUid()).push().setValue(helper);
                    startActivity(new Intent(TrackBuses.this,Student_Home.class));
                    finish();

                }
            });
        }

     // Fetch Student Location

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

    // get Driver Location from database

        public void readchanges() {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint({"DefaultLocale", "SetTextI18n"})
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                            location =snapshot.getValue(Mylocation.class);
                            assert location != null;
                            busSpeed.setText(String.valueOf(location.getSpeed())+" km/hr");



                            if (location != null) {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                latlng2 = new LatLng(latitude, longitude);
                                marker1.setVisible(true);
                                marker1.setPosition(latlng2);
                                marker1.setTitle(BusNo);
                                marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
                                distenace=calculatedistance(latlng1,latlng2);
                                double Time = (distenace / location.getSpeed()) * 60;
                                double tempTime=distenace/0.1;
                                if(location.getSpeed()==0) {
                                    arrivalTime.setText(String.format("%.0f", tempTime) + " minute");
                                }
                                else {

                                    arrivalTime.setText(String.format("%.0f", Time) + " minute");
                                }

                                mMap.addPolyline(new PolylineOptions().add(latlng1).add(latlng2).color(Color.argb(150,100,150,100)).width(4f));
                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @SuppressLint("DefaultLocale")
                                    @Override
                                    public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
                                        if (latlng2 != null) {
                                            Toast.makeText(TrackBuses.this, "You are " + String.format("%.0f", distenace) + " km away from bus", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(TrackBuses.this,"Driver End Their Location",Toast.LENGTH_SHORT).show();
                                        }
                                        return false;
                                    }
                                });


                            }

                    }
                    else
                    {
                        marker1.setVisible(false);
                        mMap.addPolyline(new PolylineOptions().visible(false));
                        Toast.makeText(TrackBuses.this,"Driver not start sharing their location yet",Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

// Get Location updates

    private void getlocationupdates() {
        if(manager!=null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, mintime, distance,this);
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, mintime, distance,this);
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

    // Calculate Distanace Between Two Latlng

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