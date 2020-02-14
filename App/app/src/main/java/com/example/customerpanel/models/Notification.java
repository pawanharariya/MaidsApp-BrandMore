package com.example.customerpanel.models;

public class Notification {
    String id;
    String info;
    String confirmedId;
    int type; //0 for instantMaids; 1 for 24X7 maids;

    public Notification(String id, String info, String confirmedId, int type) {
        this.id = id;
        this.info = info;
        this.confirmedId = confirmedId;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getConfirmedId() {
        return confirmedId;
    }

    public int getType() {
        return type;
    }

}
