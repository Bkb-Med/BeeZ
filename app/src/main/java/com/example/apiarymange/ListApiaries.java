package com.example.apiarymange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.apiarymange.Adapter.MyAdapter;
import com.example.apiarymange.Model.Apiary;
import com.example.apiarymange.Model.Frame;
import com.example.apiarymange.Model.ListFrames;
import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.Model.Traffic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListApiaries extends AppCompatActivity{
    FloatingActionButton btnAddnewApiary,btnSync;
    Button deletebtn;
    List<Apiary> apiaries = new ArrayList<>();
    List<Temperature> temperatures = new ArrayList<>();
    List<Traffic> traffics = new ArrayList<>();
    List<ListFrames> listFrames = new ArrayList<>();
    ProgressBar mainProgress;
    MyAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("apiaries");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapiaries);
        mainProgress = (ProgressBar) findViewById(R.id.mainProgressBar);
        setTemp();
        setTraffics();
        setFrames();
        btnAddnewApiary =findViewById(R.id.addnewApiary);
        deletebtn =findViewById(R.id.deleteApiary);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ap reference: "+MyAdapter.IdApiary);
                ref.child(MyAdapter.IdApiary).removeValue();
               // Toast.makeText(this, "position is :"+MyAdapter.IdApiary, Toast.LENGTH_LONG).show();
                SyncDB();
            }
        });
        btnSync=findViewById(R.id.SyncDB);
        btnAddnewApiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListApiaries.this, Addappiary.class);
                startActivity(intent);
            }
        });
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SyncDB();
            }
        });

    }
    private void SyncDB(){
        mainProgress.setVisibility(View.VISIBLE);
        apiaries.clear();
        temperatures.clear();
        traffics.clear();
        listFrames.clear();
        adapter.notifyDataSetChanged();
        setTemp();
        setTraffics();
        setFrames();
        LinearLayout menuLayout = findViewById(R.id.menulayout);
        menuLayout.setVisibility(View.GONE);

    }
    private void setTemp(){
        final String[] apReference = new String[1];

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    apReference[0] = ds.getKey();
                    final Temperature[] temp = new Temperature[1];
                    DatabaseReference refTemp = database.getReference("apiaries").child(apReference[0]).child("Temperature");
                    refTemp.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot ds, String s) {
                            String idTemp = ds.getKey();
                            String TempDate = ds.child("TempDate").getValue(String.class);
                            String TempTime = ds.child("TempTime").getValue(String.class);
                            String Tempvalue = ds.child("Tempvalue").getValue().toString();
                            temp[0] = new Temperature(idTemp,Tempvalue,TempDate,TempTime);
                            temperatures.add(temp[0]);

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
    private  void setTraffics() {
        final String[] apReference = new String[1];

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    apReference[0] = ds.getKey();

                    final Traffic[] tf = new Traffic[1];
                    DatabaseReference refTrf = database.getReference("apiaries").child(apReference[0]).child("Traffic");
                    refTrf.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot ds, String s) {
                            String idTf = ds.getKey();
                            String TfDate = ds.child("TfDate").getValue(String.class);
                            String TfTime = ds.child("TfTime").getValue(String.class);
                            String Tfvalue = ds.child("Tfvalue").getValue().toString();
                            tf[0] = new Traffic(idTf, Tfvalue, TfDate, TfTime);
                            traffics.add(tf[0]);

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setFrames(){
        final String[] apReference = new String[1];

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    apReference[0] = ds.getKey();
                    String Id = ds.child("apID").getValue(String.class);
                    String location = ds.child("apLocation").getValue(String.class);
                    String Date = ds.child("apDate").getValue(String.class);
                    String Time = ds.child("apTime").getValue(String.class);
                    apiaries.add(new Apiary(location, Id, apReference[0], Date, Time));

                    DatabaseReference refTrf = database.getReference("apiaries").child(apReference[0]).child("frames");
                    refTrf.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot ds, String s) {

                            Long c1 = ds.child("C1progress").getValue(Long.class);
                            Long c2 = ds.child("C2progress").getValue(Long.class);
                            Long c3 = ds.child("C3progress").getValue(Long.class);
                            Long c4 = ds.child("C4progress").getValue(Long.class);
                            String c1ref = ds.child("C1progress").getKey();
                            String c2ref = ds.child("C2progress").getKey();
                            String c3ref = ds.child("C3progress").getKey();
                            String c4ref = ds.child("C4progress").getKey();
                            Frame f1 = new Frame(c1ref,c1);
                            Frame f2 = new Frame(c2ref,c2);
                            Frame f3 = new Frame(c3ref,c3);
                            Frame f4 = new Frame(c4ref,c4);
                            List<Frame> frames = new ArrayList<>();
                            frames.add(f1);
                            frames.add(f2);
                            frames.add(f3);
                            frames.add(f4);
                            listFrames.add(new ListFrames(frames));



                            if (listFrames.size() > 0) {
                                setAdapter();
                                mainProgress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setAdapter(){

                RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
                recycler.setLayoutManager(new LinearLayoutManager(ListApiaries.this));
                adapter = new MyAdapter(ListApiaries.this,apiaries,temperatures,traffics,listFrames);
                recycler.setAdapter(adapter);

        }

}


       /* adapter.setLoadMore(new ILoadMore() {
            @Override
            public void OnLoadMore() {
                if(items.size()<=50){
                    adapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            adapter.notifyItemRemoved(items.size());

                            int index = items.size();
                            int end = index + 10;
                            for(int i=index;i<end;i++){
                                String name = "APX023XDF";
                                Item item = new Item(name,name.length());
                                items.add(item);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },1000);
                }else{
                    Toast.makeText(ListApp.this,"Load data completed !",Toast.LENGTH_SHORT).show();
                }
            }
        });*/