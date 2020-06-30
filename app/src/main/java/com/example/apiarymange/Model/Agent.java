package com.example.apiarymange.Model;

public class Agent {
    private String AgentId;
    private String Fullname;
    private String Adresse;
    private String AgentEmail;
    private String AgentPhone;
    private String mImageUrl;
    public Agent() {
    }

    public Agent(String agentId, String fullname, String adresse, String agentEmail, String agentPhone,String mImageUrl) {
        AgentId = agentId;
        Fullname = fullname;
        Adresse = adresse;
        AgentEmail = agentEmail;
        AgentPhone = agentPhone;
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getAgentEmail() {
        return AgentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        AgentEmail = agentEmail;
    }

    public String getAgentPhone() {
        return AgentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        AgentPhone = agentPhone;
    }
}
