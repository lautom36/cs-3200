package com.example.assn3.models;

import java.util.Date;

public class Note {
    private String userId;
    private String title;
    private String body;
    private Long dateCreated;

    public  Note() {}

    public  Note (String userId, String title, String body, Long dateCreated) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.dateCreated = dateCreated;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public long getDateCreated() {
         return dateCreated;
    }

}
