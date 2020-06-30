package com.example.apiarymange;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.Adapter.AgentAdapter;
import com.example.apiarymange.Adapter.LocationAdapter;
import com.example.apiarymange.Model.Agent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ListAgent extends AppCompatActivity {
    FloatingActionButton back, addnewagent;

    List<Agent> agents = new ArrayList<>();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ProgressBar lsProgress;
    AgentAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagent);
        lsProgress = findViewById(R.id.lsProgressBaragent);
        lsProgress.setVisibility(View.VISIBLE);
        setAgents();
        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAgent.this, MainDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        addnewagent = findViewById(R.id.addagent);
        addnewagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAgent.this, AddAgent.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void setAgents() {

        DatabaseReference refTemp = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"agents");
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.getKey();
                    String fname = ds.child("fname").getValue(String.class);
                    String adresse = ds.child("adresse").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    String phone = ds.child("phone").getValue(String.class);
                    String imgUrl = ds.child("imageUrl").getValue(String.class);
                    agents.add(new Agent(id,fname,adresse,email,phone, imgUrl));


                }

                if (agents.size() > 0) {

                    setAdapter();
                    lsProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void setAdapter() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerAgent);
        recycler.setLayoutManager(new LinearLayoutManager(ListAgent.this));
        adapter = new AgentAdapter(ListAgent.this, agents);
        recycler.setAdapter(adapter);
    }

}

