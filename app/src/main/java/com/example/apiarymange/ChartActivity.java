package com.example.apiarymange;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.Model.Traffic;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ChartActivity extends AppCompatActivity {
    FloatingActionButton backbtn;
    Spinner apReference;
    List<String> apiaries = new ArrayList<>();
    ArrayList<Entry> temperatures = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<BarEntry> traffics = new ArrayList<>();
    CombinedChart comChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataviz);
        apReference =  findViewById(R.id.vizspinner);
        fillSpinner();

        comChart = findViewById(R.id.combinedchart);
        comChart.getDescription().setEnabled(false);
        comChart.setGridBackgroundColor(Color.rgb(240,240,240));
        comChart.getAxisLeft().setDrawGridLines(false);
        comChart.getAxisRight().setDrawGridLines(false);
        comChart.getXAxis().setDrawGridLines(false);
      /*  XAxis x = comChart.getXAxis();
        x.setTextSize(14f);
        */



        comChart.setDrawGridBackground(false);
        comChart.setDrawBarShadow(false);







        backbtn=findViewById(R.id.vizback);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartActivity.this, MainDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        apReference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
             temperatures.clear();
             labels.clear();
             traffics.clear();
            SetItemVizTemp(apReference.getSelectedItem().toString());
            SetItemVizTraffic(apReference.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
                }



    private void SetItemVizTemp (String Apref){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refTemp = database.getReference("apiaries").child(Apref).child("Temperature");
        final float[] i = {0};
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    i[0]++;
                    String idTemp = ds.getKey();
                    String TempDate = ds.child("tempDate").getValue(String.class);
                    String TempTime = ds.child("tempTime").getValue(String.class);
                    Long Tempvalue = (Long) ds.child("tempvalue").getValue();
                    temperatures.add(new Entry(i[0],Tempvalue));
                    labels.add(TempDate+" | "+TempTime);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    private void SetItemVizTraffic(String Apref) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refTemp = database.getReference("apiaries").child(Apref).child("Traffic");
        final float[] i = new float[1];
        refTemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    i[0]++;

                    Long Tfvalue = (Long) ds.child("Tfvalue").getValue();
                    traffics.add(new BarEntry(i[0],Tfvalue));


                }


                XAxis xAxis = comChart.getXAxis();
                xAxis.setGranularity(1f);
              //xAxis.setCenterAxisLabels(true);
                xAxis.setLabelRotationAngle(-90);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                YAxis y = comChart.getAxisRight();
                y.setEnabled(false);

                LineDataSet lineDataSet = new LineDataSet(temperatures,"Temperature");
                LineData lineData = new LineData( lineDataSet);
                lineDataSet.setColor(Color.rgb(254, 16, 77));
                lineDataSet.setLineWidth(2.5f);
                lineDataSet.setCircleColor(Color.rgb(240, 238, 70));
                lineDataSet.setCircleRadius(5f);
                lineDataSet.setFillColor(Color.rgb(240, 238, 70));
                lineDataSet.setMode(LineDataSet.Mode.LINEAR);
                lineDataSet.setDrawValues(true);
                lineDataSet.setValueTextSize(20f);
                lineDataSet.setValueTextColor(Color.rgb(128, 0, 64));

                BarDataSet barDataset= new BarDataSet(traffics,"Traffic");
                barDataset.setColor(Color.rgb(0, 191, 165));
                barDataset.setValueTextColor(Color.rgb(128, 0, 64));
                barDataset.setValueTextSize(20f);
                BarData barData = new BarData(barDataset);
                float barWidth = 0.35f;
                barData.setBarWidth(barWidth);

                CombinedData data=new CombinedData();
                data.setData(lineData);
                data.setData(barData);
                comChart.setData(data);
                comChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
    private void fillSpinner(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("apiaries");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ref = ds.getKey();
                    apiaries.add(ref);
                }
                if(apiaries.size()>0){
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_items_viz, apiaries);
                    dataAdapter.setDropDownViewResource(R.layout.spinner_items_viz);
                    apReference.setAdapter(dataAdapter);
                    apReference.setSelected(true);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }

}