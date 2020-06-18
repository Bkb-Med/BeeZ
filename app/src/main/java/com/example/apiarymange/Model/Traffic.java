package com.example.apiarymange.Model;

public class Traffic {
    private String trafficId;
    private String value;
    private String date;
    private String time;

    public Traffic() {
    }

    public Traffic(String trafficId, String value, String date, String time) {
        this.trafficId = trafficId;
        this.value = value;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Traffic{" +
                "trafficId='" + trafficId + '\'' +
                ", value='" + value + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(String trafficId) {
        this.trafficId = trafficId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
