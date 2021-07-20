package com.application.database.models;

public class BeachModel {
    private int beachId;
    private String beachName;
    private String beachLocation;
    private String beachInfo;

    public BeachModel(int beachId, String name, String location, String info, String imagePath) {
        this.beachId = beachId;
        this.beachName = name;
        this.beachLocation = location;
        this.beachInfo = info;
    }

    public BeachModel() {
    }

    public int getBeachId() {
        return beachId;
    }

    public void setBeachId(int beachId) {
        this.beachId = beachId;
    }

    public String getBeachName() {
        return beachName;
    }

    public void setBeachName(String beachName) {
        this.beachName = beachName;
    }

    public String getBeachLocation() {
        return beachLocation;
    }

    public void setBeachLocation(String beachLocation) {
        this.beachLocation = beachLocation;
    }

    public String getBeachInfo() {
        return beachInfo;
    }

    public void setBeachInfo(String beachInfo) {
        this.beachInfo = beachInfo;
    }

    @Override
    public String toString() {
        return "BeachModel{" +
                "beachId=" + beachId +
                ", name='" + beachName + '\'' +
                ", location='" + beachLocation + '\'' +
                ", info='" + beachInfo + '\'' +
                '}';
    }
}


