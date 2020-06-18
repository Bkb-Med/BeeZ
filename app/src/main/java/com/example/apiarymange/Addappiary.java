package com.example.apiarymange;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apiarymange.Model.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Addappiary extends AppCompatActivity {
    public static final String ARTIST_NAME = "net.simplifiedcoding.firebasedatabaseexample.appid";
    public static final String ARTIST_ID = "net.simplifiedcoding.firebasedatabaseexample.appreference";
    EditText appRef,appLocation,appDate,appTime;
    Spinner spinnerGenre;
    Button buttonAdd;
    ListView listViewArtists;

    //a list to store all the artist from firebase database
    List<Item> items;

    //our database reference object
    DatabaseReference DBAppiaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapiaries);

        //getting the reference of artists node
        DBAppiaries = FirebaseDatabase.getInstance().getReference("items");

        //getting views
        appRef = (EditText) findViewById(R.id.txtreference);
        appLocation = (EditText) findViewById(R.id.txtlocation);
        appDate = (EditText) findViewById(R.id.txtDate);
        appTime = (EditText) findViewById(R.id.txtTime);
        //spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        //listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        buttonAdd = (Button) findViewById(R.id.btn_Add);

        //list to store artists
        items = new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addAppiary();
            }
        });
    }

    private void addAppiary() {
        //getting the values to save
        String reference = appRef.getText().toString().trim();
        String location = appLocation.getText().toString().trim();
        String date = appDate.getText().toString().trim();
        String time = appTime.getText().toString().trim();
        //checking if the value is provided
        if (!TextUtils.isEmpty(reference)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = DBAppiaries.push().getKey();

            //creating an Artist Object
           // Appiary appiary = new Appiary(id,reference,location,date,time);
            Item item = new Item(reference,location.length());
            //Saving the Artist
            DBAppiaries.child(id).setValue(item);

            //setting edittext to blank again
            appRef.setText("");
            appLocation.setText("");
            appDate.setText("");
            appTime.setText("");
            //displaying a success toast
            Toast.makeText(this, "appiary added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a reference", Toast.LENGTH_LONG).show();
        }
    }
}

