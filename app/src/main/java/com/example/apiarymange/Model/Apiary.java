package com.example.apiarymange.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Apiary extends Location {

    private String apID;
    private String AppReference;
    private String apDate;
    private String apTime;

    public Apiary(){

    }

    public Apiary(String locRefrence, String apID, String appReference, String appDate, String appTime) {
        super(locRefrence);
        this.apID = apID;
        AppReference = appReference;
        apDate = appDate;
        apTime = appTime;
    }

    public String getApID() {
        return apID;
    }

    public void setApID(String apID) {
        this.apID = apID;
    }

    public String getAppReference() {
        return AppReference;
    }

    public void setAppReference(String appReference) {
        AppReference = appReference;
    }

    public String getApDate() {
        return apDate;
    }

    public void setApDate(String apDate) {
        this.apDate = apDate;
    }

    public String getApTime() {
        return apTime;
    }

    public void setApTime(String apTime) {
        this.apTime = apTime;
    }
}