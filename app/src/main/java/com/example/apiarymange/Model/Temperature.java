package com.example.apiarymange.Model;

public class Temperature {
    private String TempId;
    private String Tempvalue;
    private String TempDate;
    private String TempTime;

    public Temperature() {
    }

    public Temperature(String tempId, String tempvalue, String TempDate, String TempTime) {
        TempId = tempId;
        Tempvalue = tempvalue;
        this.TempDate = TempDate;
        this.TempTime = TempTime;
    }

    public String getTempId() {
        return TempId;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "TempId='" + TempId + '\'' +
                ", Value='" + Tempvalue + '\'' +
                ", date='" + TempDate + '\'' +
                ", time='" + TempTime + '\'' +
                '}';
    }

    public void setTempId(String tempId) {
        TempId = tempId;
    }

    public String getTempvalue() {
        return Tempvalue;
    }

    public void setTempvalue(String tempvalue) {
        Tempvalue = tempvalue;
    }

    public String getTempDate() {
        return TempDate;
    }

    public void setTempDate(String tempDate) {
        this.TempDate = tempDate;
    }

    public String getTempTime() {
        return TempTime;
    }

    public void setTempTime(String tempTime) {
        this.TempTime = tempTime;
    }
}
