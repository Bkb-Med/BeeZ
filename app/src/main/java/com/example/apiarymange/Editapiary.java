package com.example.apiarymange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apiarymange.Adapter.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editapiary extends AppCompatActivity {
    EditText appRef,appDate,appTime,appLocation;
    private String id;
    Button SaveBtn,buttonCancel;
    List<String> locationList = new ArrayList<>();

    DatabaseReference DBAppiaries;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editapiary);
        Intent intent = getIntent();
        String ref = intent.getStringExtra("apreference");

        appRef = (EditText) findViewById(R.id.EDtxtreference);
        appLocation =  findViewById(R.id.EDtxtlocation);
        appDate = (EditText) findViewById(R.id.EDtxtDate);
        appTime = (EditText) findViewById(R.id.EDtxtTime);
        SaveBtn = (Button) findViewById(R.id.EDbtn_Save);
        setApiary(ref);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   saveApiary() ;

            }
        });
    }
    public void setApiary(final String reference){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("apiaries").child(reference);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                id = ds.child("apID").getValue(String.class);
                String location = ds.child("apLocation").getValue(String.class);
                String Date = ds.child("apDate").getValue(String.class);
                String Time = ds.child("apTime").getValue(String.class);
                appRef.setText(reference);
                appLocation.setText(location);
                appDate.setText(Date);
                appTime.setText(Time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});}

public void saveApiary(){
    DBAppiaries = FirebaseDatabase.getInstance().getReference("apiaries");
    final String reference = appRef.getText().toString().trim();
    final String location = appLocation.getText().toString().toString();
    final String date = appDate.getText().toString().trim();
    final String time = appTime.getText().toString().trim();
    final List<String> refrences = new ArrayList<>();

    Post post = new Post(id,date,time,location,reference);
    Map<String, Object> postValues = post.toMap();

    Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put(reference, postValues);

    DBAppiaries.updateChildren(childUpdates);

    }



}
