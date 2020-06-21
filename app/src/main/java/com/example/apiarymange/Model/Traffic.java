package com.example.apiarymange.Model;

public class Traffic {
    private String trafficId;
    private String tfvalue;
    private String tfDate;
    private String tfTime;

    public Traffic() {
    }

    public Traffic(String trafficId, String tfvalue, String date, String tfTime) {
        this.trafficId = trafficId;
        this.tfvalue = tfvalue;
        this.tfDate = date;
        this.tfTime = tfTime;
    }

    @Override
    public String toString() {
        return "Traffic{" +
                "trafficId='" + trafficId + '\'' +
                ", value='" + tfvalue + '\'' +
                ", date='" + tfDate + '\'' +
                ", time='" + tfTime + '\'' +
                '}';
    }

    public String getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(String trafficId) {
        this.trafficId = trafficId;
    }

    public String getTfvalue() {
        return tfvalue;
    }

    public void setTfvalue(String tfvalue) {
        this.tfvalue = tfvalue;
    }

    public String getTfDate() {
        return tfDate;
    }

    public void setTfDate(String tfDate) {
        this.tfDate = tfDate;
    }

    public String getTfTime() {
        return tfTime;
    }

    public void setTfTime(String tfTime) {
        this.tfTime = tfTime;
    }
}
