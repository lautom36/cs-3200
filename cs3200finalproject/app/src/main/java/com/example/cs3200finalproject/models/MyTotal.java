package com.example.cs3200finalproject.models;

public class MyTotal {
    String totalType;
    String uid;
    int total;

    public MyTotal() {}

    public MyTotal(String totalType, int total, String uid) {
        this.totalType = totalType;
        this.total = total;
        this.uid = uid;
    }

    public int getTotal() {
        return total;
    }

    public String getTotalType() {
        return totalType;
    }

    public String getUid() {
        return uid;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTotalType(String totalType) {
        this.totalType = totalType;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
