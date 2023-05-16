package com.application.sunbedreservation;

public class Sunbed {
    private String id;
    private int row;
    private boolean taken;

    public Sunbed() {
        // Required empty public constructor for Firebase
    }

    public Sunbed(String id, int row, boolean taken) {
        this.id = id;
        this.row = row;
        this.taken = taken;
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

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}