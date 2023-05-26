package com.application.sunbedreservation;

import java.util.List;

public class Sunbed {
    private String id;
    private int row;
    private List<String> reservedDates;

    public Sunbed() {
        // Required empty public constructor for Firebase
    }

    public Sunbed(String id, int row) {
        this.id = id;
        this.row = row;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<String> getReservedDates() {
        return reservedDates;
    }

    public void setReservedDates(List<String> reservedDates) {
        this.reservedDates = reservedDates;
    }
}