package com.application.sunbedreservation;

import java.util.List;

public class Reservation {
    private String userId;
    private String beachId;
    private String reservationDate;
    private List<String> sunbedIds;

    public Reservation() {
        // Default constructor required for Firebase
    }

    public Reservation(String userId, String beachId, String reservationDate, List<String> sunbedIds) {
        this.userId = userId;
        this.beachId = beachId;
        this.reservationDate = reservationDate;
        this.sunbedIds = sunbedIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBeachId() {
        return beachId;
    }

    public void setBeachId(String beachId) {
        this.beachId = beachId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public List<String> getSunbedIds() {
        return sunbedIds;
    }

    public void setSunbedIds(List<String> sunbedIds) {
        this.sunbedIds = sunbedIds;
    }
}

