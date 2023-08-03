package com.application.sunbedreservation;

import java.util.Date;

public class News {
    private String title;
    private String description;
    private String location;
    private String imageURL;
    private long date;

    // Required empty constructor for Firestore serialization
    public News() {}

    public News(String title, String description, String location, String imageURL, long date) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.imageURL = imageURL;
        this.date = date;
    }

    // Getters and setters (You can use Android Studio's "Generate" feature to generate these)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
