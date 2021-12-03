package com.example.cs3200finalproject.models;

public class MyTypes {
    public String type;
    public String userId;
    private Long dateCreated;

    public MyTypes() {}

    public MyTypes(String userID, String type, Long dateCreated) {
            this.userId = userID;
            this.type = type;
            this.dateCreated = dateCreated;
    }

    public String getUserId() { return userId; }

    public Long getDateCreated() { return dateCreated; }

    public void setType(String typeName) {
                this.type = typeName;
    }

        public String getType() { return type; }
}
