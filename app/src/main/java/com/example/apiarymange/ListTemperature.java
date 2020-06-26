package com.example.apiarymange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.Adapter.LocationAdapter;
import com.example.apiarymange.Adapter.TempAdapter;
import com.example.apiarymange.Model.Temperature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTemperature extends AppCompatActivity {
    FloatingActionButton backbtn;
    TextView viewrefrence;
    String apref;

    List<Temperature> temperatures = new ArrayList<>();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ProgressBar mainProgress;
    TempAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtemp);
        Intent intent = getIntent();
        apref = intent.getStringExtra("apreference");
        setTemp();


        backbtn=findViewById(R.id.backTemp);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTemperature.this, ListApiaries.class);
                startActivity(intent);
                finish();
            }
        });
        viewrefrence = findViewById(R.id.aprefrenceTemp);
        viewrefrence.setText(apref);

    }


    private void setTemp(){

        DatabaseReference refTemp = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"apiaries").child(apref).child("Temperature");
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String idTemp = ds.getKey();
                    String TempDate = ds.child("tempDate").getValue(String.class);
                    String TempTime = ds.child("tempTime").getValue(String.class);
                    String Tempvalue = ds.child("tempvalue").getValue().toString();
                    temperatures.add(new Temperature(idTemp,Tempvalue,TempDate,TempTime));


                }

                if (temperatures.size() > 0) {
                    Collections.reverse(temperatures);
                    setAdapter();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void setAdapter(){
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recyclerTemp);
        recycler.setLayoutManager(new LinearLayoutManager(ListTemperature.this));
        adapter = new TempAdapter(ListTemperature.this,temperatures);
        recycler.setAdapter(adapter);
    }

}