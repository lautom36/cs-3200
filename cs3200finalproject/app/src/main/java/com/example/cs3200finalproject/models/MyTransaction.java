package com.example.cs3200finalproject.models;

import android.media.Image;
import android.view.SurfaceControl;

public class MyTransaction {
    private String userId;
    private Image image;
    private int amount;
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

    public MyTransaction (String userId, String  amount, String type, String description) {
        this.userId = userId;
        this.amount = Integer.parseInt(amount);
        this.type = type;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setAmount(int amount) {
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

    public Object getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
