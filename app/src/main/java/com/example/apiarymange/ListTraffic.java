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

import com.example.apiarymange.Adapter.TempAdapter;
import com.example.apiarymange.Adapter.TrafficAdapter;
import com.example.apiarymange.Model.Traffic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTraffic extends AppCompatActivity {
    FloatingActionButton backbtn;
    TextView viewrefrence;
    String apref;

    List<Traffic> traffics = new ArrayList<>();

    ProgressBar mainProgress;
    TrafficAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapweights);
        Intent intent = getIntent();
        apref = intent.getStringExtra("apreference");
        setTemp();


        backbtn=findViewById(R.id.backTraffic);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTraffic.this, ListApiaries.class);
                startActivity(intent);
                finish();
            }
        });
        viewrefrence = findViewById(R.id.aprefrenceTraffic);
        viewrefrence.setText(apref);

    }


    private void setTemp(){

        DatabaseReference refTemp = database.getReference("apiaries").child(apref).child("Traffic");
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String idTraffic = ds.getKey();
                    String TfDate = ds.child("TfDate").getValue(String.class);
                    String TfTime = ds.child("TfTime").getValue(String.class);
                    String Tfvalue = ds.child("Tfvalue").getValue().toString();
                    traffics.add(new Traffic(idTraffic,Tfvalue,TfDate,TfTime));


                }

                if (traffics.size() > 0) {
                    Collections.reverse(traffics);
                    setAdapter();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void setAdapter(){
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recyclerTraffic);
        recycler.setLayoutManager(new LinearLayoutManager(ListTraffic.this));
        adapter = new TrafficAdapter(ListTraffic.this, traffics);
        recycler.setAdapter(adapter);
    }

}