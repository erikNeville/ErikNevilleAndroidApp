package com.example.erik.eriknevilead340;

public class TrafficModel {
    private String description;
    private String imgURL;
    private String type;

    public TrafficModel(String description, String imgURL, String type) {
        this.description = description;
        this.imgURL = imgURL;
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public String getType() {
        return this.type;
    }
}
