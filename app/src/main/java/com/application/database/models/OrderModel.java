package com.application.database.models;

public class OrderModel {
    private int orderID;
    private int userID;
    private int sunbedID;
    private String orderDate;
    private String reservationDateStart;
    private String reservationDateEnd;
    private double totalCost;

    public OrderModel(int orderID, int userID, int sunbedID, String orderDate, String reservationDateStart, String reservationDateEnd, double totalCost) {
        this.orderID = orderID;
        this.userID = userID;
        this.sunbedID = sunbedID;
        this.orderDate = orderDate;
        this.reservationDateStart = reservationDateStart;
        this.reservationDateEnd = reservationDateEnd;
        this.totalCost = totalCost;
    }

    public OrderModel() {
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSunbedID() {
        return sunbedID;
    }

    public void setSunbedID(int sunbedID) {
        this.sunbedID = sunbedID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getReservationDateStart() {
        return reservationDateStart;
    }

    public void setReservationDateStart(String reservationDateStart) {
        this.reservationDateStart = reservationDateStart;
    }

    public String getReservationDateEnd() {
        return reservationDateEnd;
    }

    public void setReservationDateEnd(String reservationDateEnd) {
        this.reservationDateEnd = reservationDateEnd;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", sunbedID=" + sunbedID +
                ", orderDate='" + orderDate + '\'' +
                ", reservationDateStart='" + reservationDateStart + '\'' +
                ", reservationDateEnd='" + reservationDateEnd + '\'' +
                ", totalCost=" + totalCost +
                '}';
    }
}
