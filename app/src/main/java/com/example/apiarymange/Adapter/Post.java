package com.example.apiarymange.Adapter;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {
    public String apID;
    public String apDate;
    public String apTime;
    public String apLocation;
    public String apRefrence;


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post( String apId,String apDate, String apTime, String apLocation,String apRefrence) {
        this.apID=apId;
        this.apRefrence = apRefrence;
        this.apDate = apDate;
        this.apTime = apTime;
        this.apLocation = apLocation;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("apDate", apDate);
        result.put("apID", apID);
        result.put("apLocation",apLocation);
        result.put("apTime", apTime);
        return result;
    }

}