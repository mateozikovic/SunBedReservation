package com.application.database.models;

public class NewsModel {
    private int newsID;
    private String newsTitle;
    private String newsText;
    private String date;
    private String image;


    public NewsModel(int newsID, String newsTitle, String newsText, String date, String image) {
        this.newsID = newsID;
        this.newsTitle = newsTitle;
        this.newsText = newsText;
        this.date = date;
        this.image = image;
    }

    public NewsModel() {
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "newsID=" + newsID +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsText='" + newsText + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
