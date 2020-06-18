package com.example.apiarymange;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    CardView manageApp;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
          Toolbar toolbar = (Toolbar) findViewById(R.id.menudashboard);
          setSupportActionBar(toolbar);

          ActionBar actionBar = getSupportActionBar();
          actionBar.setDisplayHomeAsUpEnabled(true);
          actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        manageApp = findViewById(R.id.manageApiary);
        manageApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ListApiaries.class);
                startActivity(intent);
            }
        });
    }


}
