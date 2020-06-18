package com.example.apiarymange.Model;

public class Temperature {
    private String TempId;
    private String Value;
    private String date;
    private String time;

    public Temperature() {
    }

    public Temperature(String tempId, String value, String date, String time) {
        TempId = tempId;
        Value = value;
        this.date = date;
        this.time = time;
    }

    public String getTempId() {
        return TempId;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "TempId='" + TempId + '\'' +
                ", Value='" + Value + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public void setTempId(String tempId) {
        TempId = tempId;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
