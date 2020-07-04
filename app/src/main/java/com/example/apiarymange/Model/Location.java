package com.example.apiarymange.Model;



public class Location {
    private String id;
    private String apLocation;
    private double mlat;
    private double mlong;
    public Location() {
    }

    public Location(String id,String apLocation) {
        this.apLocation = apLocation;
        this.id =id;
    }
    public Location(String id,String apLocation, double mlat, double mlong) {
        this.apLocation = apLocation;
        this.mlat =mlat;
        this.mlong = mlong;
        this.id =id;
    }
    public Location(String locRefrence) {
        this.apLocation = locRefrence;
    }

    public double getMlat() {
        return mlat;
    }

    public void setMlat(double mlat) {
        this.mlat = mlat;
    }

    public double getMlong() {
        return mlong;
    }

    public void setMlong(double mlong) {
        this.mlong = mlong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApLocation() {
        return apLocation;
    }

    public void setApLocation(String apLocation) {
        this.apLocation = apLocation;
    }
}
