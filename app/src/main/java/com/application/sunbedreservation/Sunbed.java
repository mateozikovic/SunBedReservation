package com.application.sunbedreservation;

// Model for saving a subdocument where it contains positions of the sunbeds

public class Sunbed {
    private String id;
    private int row;
    private boolean isTaken;

    public Sunbed() {}

    public Sunbed(String id, int row, boolean isTaken) {
        this.id = id;
        this.row = row;
        this.isTaken = isTaken;
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

    public void setTaken(boolean isTaken) { this.isTaken = isTaken; };

    public boolean getTaken() { return isTaken; };
}
