package com.application.database.models;

public class BeachModel {
    private int beachId;
    private String name;
    private String location;
    private String info;
    private String imagePath;

    public BeachModel(int beachId, String name, String location, String info, String imagePath) {
        this.beachId = beachId;
        this.name = name;
        this.location = location;
        this.info = info;
        this.imagePath = imagePath;
    }

    public int getBeachId() {
        return beachId;
    }

    public void setBeachId(int beachId) {
        this.beachId = beachId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "BeachModel{" +
                "beachId=" + beachId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", info='" + info + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}


