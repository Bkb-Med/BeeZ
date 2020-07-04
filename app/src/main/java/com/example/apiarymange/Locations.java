package com.example.apiarymange;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.apiarymange.Adapter.LocationAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Locations extends AppCompatActivity  {
    boolean clear_map=false;
    String IdLocation=null;
    FloatingActionButton back, addnewloc,delloc;
    EditText txtloc;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    List<com.example.apiarymange.Model.Location> locations = new ArrayList<>();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        loadLocations();
        txtloc =findViewById(R.id.txtaddloc);
        delloc = findViewById(R.id.dellocation);
        delloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IdLocation!=null){
                    DeleteShowDialog();
                }

            }
        });
        addnewloc = findViewById(R.id.addnewlocation);
        addnewloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });

    }


    private void DeleteShowDialog() throws Resources.NotFoundException {
        final DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage(
                        "Do you really want to remove this Location?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        android.R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ref.child(IdLocation).removeValue();
                                Toast.makeText(getApplicationContext(), "Location successfully removed ", Toast.LENGTH_LONG).show();
                                refresh();
                            }
                        })
                .setNegativeButton(
                        android.R.string.no,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                            }
                        }).show();
    }
    private void checkPermissions() {

        client = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(Locations.this,
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            UploadCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(Locations.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void UploadCurrentLocation() {
        final DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");
        final String loc = txtloc.getText().toString().trim();
        if(TextUtils.isEmpty(loc)){
            txtloc.setError("name of location is required !");
            return;
        }
        if (!TextUtils.isEmpty(loc)) {

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null){
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (IdLocation == null) {
                                String id = ref.push().getKey();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(id + "/name", loc);
                                childUpdates.put(id + "/lat", location.getLatitude());
                                childUpdates.put(id + "/long", location.getLongitude());

                                ref.updateChildren(childUpdates);

                                locations.clear();
                                loadLocations();
                                Toast.makeText(getApplicationContext(), "location added", Toast.LENGTH_LONG).show();
                            }else{
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(IdLocation + "/name", loc);

                                ref.updateChildren(childUpdates);

                                Toast.makeText(getApplicationContext(), "location Updated", Toast.LENGTH_LONG).show();
                                refresh();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });

                }
            }
        });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                UploadCurrentLocation();
            }
        }
    }

    private void loadLocations() {


        DatabaseReference refTemp = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.getKey();
                    String loc = ds.child("name").getValue(String.class);
                    double mlat = ds.child("lat").getValue(double.class);
                    double mlong = ds.child("long").getValue(double.class);
                    locations.add(new com.example.apiarymange.Model.Location(id,loc,mlat,mlong));


                }

                if (locations.size() > 0) {

                    mapSetting();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
    private void mapSetting(){

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = null;
                for(com.example.apiarymange.Model.Location loc :locations){
                    latLng = new LatLng(loc.getMlat(),
                            loc.getMlong());
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .title(loc.getApLocation());
                    googleMap.addMarker(options);
                }


                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        for (com.example.apiarymange.Model.Location loc : locations) {
                            if (loc.getApLocation().equals(marker.getTitle())) {
                                IdLocation = loc.getId();
                            }
                        }
                        //txtloc.setWidth(200);
                        delloc.setVisibility(View.VISIBLE);
                        txtloc.setText(marker.getTitle());
                        return false;
                    }
                });

            }

        });


    }
    private void refresh(){
        Intent intent = new Intent(Locations.this, Locations.class);
        startActivity(intent);
        finish();
    }


  /*   @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng benslimane = new LatLng(33.616833, -7.125711);
        map.addMarker(new MarkerOptions().position(benslimane).title("benslimane"));
        map.moveCamera(CameraUpdateFactory.newLatLng(benslimane));


                    mapFragment.getMapAsync(new OnMapReadyCallback(){
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                                    MarkerOptions options = new MarkerOptions()
                                            .position(latLng)
                                            .title("current location");
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                                    googleMap.addMarker(options);

                        }
                    });
    }*/
}
