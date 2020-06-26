package com.example.apiarymange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apiarymange.Adapter.Post;
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

public class Editapiary extends AppCompatActivity {
    EditText appRef,appDate,appTime;
    private String id;
    Button SaveBtn,buttonCancel;
    List<String> locationList = new ArrayList<>();
    Spinner SpLocation;
    DatabaseReference DBAppiaries;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editapiary);
        Intent intent = getIntent();
        final String ref = intent.getStringExtra("apreference");
        buttonCancel = findViewById(R.id.EDbtn_cancel);
        appRef = (EditText) findViewById(R.id.EDtxtreference);
        SpLocation =  findViewById(R.id.EDtxtlocation);
        appDate = (EditText) findViewById(R.id.EDtxtDate);
        appTime = (EditText) findViewById(R.id.EDtxtTime);
        SaveBtn = (Button) findViewById(R.id.EDbtn_Save);
        setApiary(ref);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   saveApiary(ref) ;

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Editapiary.this, ListApiaries.class);
                startActivity(intent);
                finish();

            }
        });


    }
    private void fillSpinner(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String location = ds.child("name").getValue(String.class);
                    locationList.add(location);
                }
                if(locationList.size()>0){
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, locationList){
                        // Disable click item < month current
                        @Override
                        public boolean isEnabled(int position) {
                            // TODO Auto-generated method stub
                            if (position ==0) {
                                return false;
                            }
                            return true;
                        }};

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    SpLocation.setAdapter(dataAdapter);}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }
    public void setApiary(final String reference){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"apiaries").child(reference);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                id = ds.child("apID").getValue(String.class);
                String location = ds.child("apLocation").getValue(String.class);
                String Date = ds.child("apDate").getValue(String.class);
                String Time = ds.child("apTime").getValue(String.class);
                appRef.setText(reference);
                locationList.add("Actual  :"+location);
                appDate.setText(Date);
                appTime.setText(Time);
                fillSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});}

public void saveApiary(final String reference){
    DBAppiaries = FirebaseDatabase.getInstance().getReference(fAuth.getCurrentUser().getUid()+"/"+"apiaries").child(reference);

    final String location = SpLocation.getSelectedItem().toString();
    final String date = appDate.getText().toString().trim();
    final String time = appTime.getText().toString().trim();


    Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put("apLocation",location);
    childUpdates.put("apDate",date);
    childUpdates.put("apTime",time);
    DBAppiaries.updateChildren(childUpdates);

    Intent intent = new Intent(this, ListApiaries.class);
    startActivity(intent);
    finish();

    }



}
