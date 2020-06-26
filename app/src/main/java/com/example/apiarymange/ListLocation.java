package com.example.apiarymange;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.Adapter.LocationAdapter;
import com.example.apiarymange.Model.Location;
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

public class ListLocation extends AppCompatActivity {
    FloatingActionButton back, addnewloc;
    EditText txtloc;
    List<Location> locations = new ArrayList<>();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ProgressBar mainProgress;
    LocationAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listlocations);

        setLocation();
        back = findViewById(R.id.backlocation);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListLocation.this, MainDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        txtloc =findViewById(R.id.txtaddlocation);
        addnewloc = findViewById(R.id.addnewlocation);
        addnewloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addlocation();
            }
        });
    }

    public void Addlocation(){
        final DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");
        final String loc = txtloc.getText().toString().trim();

        if (!TextUtils.isEmpty(loc)) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (LocationAdapter.idLoc == null) {
                        String id = ref.push().getKey();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(id + "/name", loc);

                        ref.updateChildren(childUpdates);
                        locations.clear();
                        setLocation();
                        Toast.makeText(getApplicationContext(), "location added", Toast.LENGTH_LONG).show();
                    }else {
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(LocationAdapter.idLoc + "/name", loc);

                        ref.updateChildren(childUpdates);
                        locations.clear();
                        setLocation();
                        Toast.makeText(getApplicationContext(), "location Updated", Toast.LENGTH_LONG).show();
                        LocationAdapter.idLoc = null;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            }); }


    }
    private void setLocation() {

        DatabaseReference refTemp = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.getKey();
                    String loc = ds.child("name").getValue(String.class);
                    locations.add(new Location(id,loc));


                }

                if (locations.size() > 0) {

                    setAdapter();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void setAdapter() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerlocation);
        recycler.setLayoutManager(new LinearLayoutManager(ListLocation.this));
        adapter = new LocationAdapter(ListLocation.this, locations,txtloc);
        recycler.setAdapter(adapter);
    }

}

