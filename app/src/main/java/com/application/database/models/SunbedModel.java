package com.application.database.models;

public class SunbedModel extends BeachModel{

    private int sunbedID;
    private int beachID;
    private String sunbedSection;
    private int sunbedNumber;
    private int dailyPriceSeason;
    private int getDailyNonSeason;
    private boolean reserved;

    public SunbedModel(int beachId, String name, String location, String info, String imagePath, int sunbedID, int beachID, String sunbedSection, int sunbedNumber, int dailyPriceSeason, int getDailyNonSeason, boolean reserved) {
        super(beachId, name, location, info, imagePath);
        this.sunbedID = sunbedID;
        this.beachID = beachID;
        this.sunbedSection = sunbedSection;
        this.sunbedNumber = sunbedNumber;
        this.dailyPriceSeason = dailyPriceSeason;
        this.getDailyNonSeason = getDailyNonSeason;
        this.reserved = reserved;
    }

    public SunbedModel(){
    }

    public int getSunbedID() {
        return sunbedID;
    }

    public void setSunbedID(int sunbedID) {
        this.sunbedID = sunbedID;
    }

    public int getBeachID() {
        return beachID;
    }

    public void setBeachID(int beachID) {
        this.beachID = beachID;
    }

    public String getSunbedSection() {
        return sunbedSection;
    }

    public void setSunbedSection(String sunbedSection) {
        this.sunbedSection = sunbedSection;
    }

    public int getSunbedNumber() {
        return sunbedNumber;
    }

    public void setSunbedNumber(int sunbedNumber) {
        this.sunbedNumber = sunbedNumber;
    }

    public int getDailyPriceSeason() {
        return dailyPriceSeason;
    }

    public void setDailyPriceSeason(int dailyPriceSeason) {
        this.dailyPriceSeason = dailyPriceSeason;
    }

    public int getGetDailyNonSeason() {
        return getDailyNonSeason;
    }

    public void setGetDailyNonSeason(int getDailyNonSeason) {
        this.getDailyNonSeason = getDailyNonSeason;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}


