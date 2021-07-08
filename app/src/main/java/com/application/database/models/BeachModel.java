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

    public int getBeachId() {
        return beachId;
    }

    public void setBeachId(int beachId) {
        this.beachId = beachId;
    }

    public String getName() {
        return beachName;
    }

    public void setName(String name) {
        this.beachName = name;
    }

    public String getLocation() {
        return beachLocation;
    }

    public void setLocation(String location) {
        this.beachLocation = location;
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


