package com.example.apiarymange;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apiarymange.Adapter.DateInputMask;
import com.example.apiarymange.Adapter.Post;
import com.example.apiarymange.Model.Apiary;
import com.example.apiarymange.Model.Location;
import com.example.apiarymange.Model.Temperature;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Addappiary extends AppCompatActivity {

    EditText appRef,appDate,appTime;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();;
    Spinner appLocation;
    Button buttonAdd,buttonCancel;
    List<String> locationList = new ArrayList<>();

    DatabaseReference DBAppiaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapiaries);

        fillSpinner();
        //getting views
        appRef = (EditText) findViewById(R.id.txtreference);
        buttonCancel = findViewById(R.id.btn_cancel);
        appLocation =  findViewById(R.id.txtlocation);
        appDate = (EditText) findViewById(R.id.txtDate);
        DateInputMask dtip =new DateInputMask(appDate);
        appTime = (EditText) findViewById(R.id.txtTime);

        buttonAdd = (Button) findViewById(R.id.btn_Add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addAppiary() ;

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Addappiary.this, ListApiaries.class);
                startActivity(intent);
                finish();

            }
        });
    }
    private void fillSpinner(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");

        locationList.add("Location :");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String location = ds.child("name").getValue(String.class);
                        locationList.add(location);
                }
                if(locationList.size()>0){
                       ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_items_location, locationList){
                            // Disable click item < month current
                            @Override
                            public boolean isEnabled(int position) {
                                // TODO Auto-generated method stub
                                if (position ==0) {
                                    return false;
                                }
                                return true;
                            }};

                        dataAdapter.setDropDownViewResource(R.layout.spinner_items_location);


                        appLocation.setAdapter(dataAdapter);}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }

    private void addAppiary() {
        DBAppiaries = FirebaseDatabase.getInstance().getReference(fAuth.getCurrentUser().getUid()+"/"+"apiaries");
        final String reference = appRef.getText().toString().trim();
        final String location = appLocation.getSelectedItem().toString();
        final String date = appDate.getText().toString().trim();
        final String time = appTime.getText().toString().trim();
        final List<String> refrences = new ArrayList<>();

        if (!TextUtils.isEmpty(reference)) {
            DBAppiaries.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String apRefrence = ds.getKey();
                        refrences.add(apRefrence);
                        }
                    if(refrences.contains(reference)){


                        Toast.makeText(getApplicationContext(), "apiary already exist", Toast.LENGTH_LONG).show();

                    }else {

                        String id = DBAppiaries.push().getKey();

                        Post post = new Post(id,date,time,location,reference);
                        Map<String, Object> postValues = post.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(reference, postValues);

                        DBAppiaries.updateChildren(childUpdates);
                        childUpdates.clear();
                        childUpdates.put(reference+"/Temperature/"+"0x000"+"/"+"tempvalue", "-");
                        DBAppiaries.updateChildren(childUpdates);
                        childUpdates.clear();
                        childUpdates.put(reference+"/Traffic/"+"0x000"+"/"+"Tfvalue", 0);
                        DBAppiaries.updateChildren(childUpdates);
                        childUpdates.clear();
                        childUpdates.put(reference+"/frames/"+"0x000"+"/"+"C1progress", 0);
                        childUpdates.put(reference+"/frames/"+"0x000"+"/"+"C2progress", 0);
                        childUpdates.put(reference+"/frames/"+"0x000"+"/"+"C3progress", 0);
                        childUpdates.put(reference+"/frames/"+"0x000"+"/"+"C4progress", 0);
                        DBAppiaries.updateChildren(childUpdates);
                        Toast.makeText(getApplicationContext(), "appiary added", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Addappiary.this, ListApiaries.class);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


            appRef.setText("");
            appLocation.setSelection(0);
            appDate.setText("");
            appTime.setText("");
            //displaying a success toast

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a reference", Toast.LENGTH_LONG).show();

        }

    }
}

