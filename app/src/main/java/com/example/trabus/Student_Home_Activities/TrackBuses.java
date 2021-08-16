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

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Objects;

public class TrackBuses extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private  GoogleMap mMap;
    String BusNo,id;
    Query query;
    RatingBar ratingBar;
    TextView textView;
    Marker marker,marker1;
    Dialog rating;
    Button btnreview;
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
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        options=new MarkerOptions();
        rating=new Dialog(TrackBuses.this);
        rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rating.setContentView(R.layout.review);
        btnreview=rating.findViewById(R.id.btnrate);
        ratingBar=rating.findViewById(R.id.ratingbar);
        textView=rating.findViewById(R.id.ratingtext);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textView.setText(" "+rating);
            }
        });
        BusNo=getIntent().getStringExtra("busno");
        id=getIntent().getStringExtra("id");
        reference= FirebaseDatabase.getInstance().getReference();
        query=reference.child("User").child("Drivers").child("Location").child(id);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        manager=(LocationManager)getSystemService(LOCATION_SERVICE);
        fetchLocation();
        getlocationupdates();
        Button EndRoute;
        EndRoute=findViewById(R.id.endroute);
        EndRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.show();
                onPause();
            }
        });
        btnreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrackBuses.this,"Your response has been recorded",Toast.LENGTH_SHORT).show();
                String text= textView.getText().toString().trim();
                FirebaseUser CurrentUser=FirebaseAuth.getInstance().getCurrentUser();
                Rating helper=new Rating(text);
                reference.child("User").child("Drivers").child("Rating").child(id).child(CurrentUser.getUid()).setValue(helper);
                startActivity(new Intent(TrackBuses.this,Student_Home.class));
                finish();

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        latLng=new LatLng(33,73);
        marker1=mMap.addMarker(options.position(latLng).title("Location Checking"));
        marker=mMap.addMarker(options.position(latLng));
        latlng1 = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(options.position(latlng1).title("you are Here!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng1));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 18.0f));
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
                                marker1.setVisible(true);
                                marker1.setPosition(latlng2);
                                marker1.setTitle(BusNo);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng2, 18.0f));
                                marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus));
                                distenace=calculatedistance(latlng1,latlng2);
                                mMap.addPolyline(new PolylineOptions().add(latlng1).add(latlng2).color(Color.argb(150,100,150,100)).width(4f));
                                mMap.addCircle(new CircleOptions().fillColor(Color.argb(150,100,150,100)).radius(5.0).center(latlng1).strokeWidth(3f));
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
                        Toast.makeText(TrackBuses.this,"Driver not start sharing their location yet",Toast.LENGTH_SHORT).show();
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