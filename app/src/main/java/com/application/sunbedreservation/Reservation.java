package com.application.sunbedreservation;

public class Reservation {
    private String userId;
    private String sunbedId;
    private String reservationDate;

    public Reservation() {
    }

    public Reservation(String userId, String sunbedId, String reservationDate) {
        this.userId = userId;
        this.sunbedId = sunbedId;
        this.reservationDate = reservationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSunbedId() {
        return sunbedId;
    }

    public void setSunbedId(String sunbedId) {
        this.sunbedId = sunbedId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
}