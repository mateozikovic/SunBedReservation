package com.application.sunbedreservation;

public class Reservation {
    private String userId;
    private String sunbedId;
    private String reservationDate;
    private String beachId;

    public Reservation() {
        // Default constructor required for Firebase
    }

    public Reservation(String userId, String sunbedId, String reservationDate, String beachId) {
        this.userId = userId;
        this.sunbedId = sunbedId;
        this.reservationDate = reservationDate;
        this.beachId = beachId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSunbedId() {
        return sunbedId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getBeachId() {
        return beachId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSunbedId(String sunbedId) {
        this.sunbedId = sunbedId;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setBeachId(String beachId) {
        this.beachId = beachId;
    }
}
