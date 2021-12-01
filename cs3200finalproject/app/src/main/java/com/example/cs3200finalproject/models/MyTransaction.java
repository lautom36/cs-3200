package com.example.cs3200finalproject.models;

import android.media.Image;
import android.view.SurfaceControl;

public class MyTransaction {
    private String userId;
    private Image image;
    private String amount;
    private String type;
    private String description;
    private Long dateCreated;

    public MyTransaction() {}

//    public MyTransaction (String userId, Image image, int amount, String type, String description) {
//        this.userId = userId;
//        this.image = image;
//        this.amount = amount;
//        this.type = type;
//        this.description = description;
//    }

    public MyTransaction (String userId, String  amount, String type, String description, Long dateCreated) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public String getUserId() {
        return userId;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setAmount(String  amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
