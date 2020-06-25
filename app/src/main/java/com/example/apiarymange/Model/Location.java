package com.example.apiarymange.Model;



public class Location {
    private String id;
    private String apLocation;

    public Location() {
    }

    public Location(String id,String apLocation) {
        this.apLocation = apLocation;
        this.id =id;
    }

    public Location(String locRefrence) {
        this.apLocation = locRefrence;
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
