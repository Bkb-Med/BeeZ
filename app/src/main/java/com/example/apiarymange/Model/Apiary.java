package com.example.apiarymange.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Apiary extends Location {

    private String AppId;
    private String AppReference;
    private String AppDate;
    private String AppTime;

    public Apiary(){

    }

    public Apiary(String locRefrence, String appId, String appReference, String appDate, String appTime) {
        super(locRefrence);
        AppId = appId;
        AppReference = appReference;
        AppDate = appDate;
        AppTime = appTime;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAppReference() {
        return AppReference;
    }

    public void setAppReference(String appReference) {
        AppReference = appReference;
    }

    public String getAppDate() {
        return AppDate;
    }

    public void setAppDate(String appDate) {
        AppDate = appDate;
    }

    public String getAppTime() {
        return AppTime;
    }

    public void setAppTime(String appTime) {
        AppTime = appTime;
    }
}