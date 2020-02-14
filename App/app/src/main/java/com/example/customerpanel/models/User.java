package com.example.customerpanel.models;

public class User {
    String name;
    String contact;
    String address;
    Boolean activeStatus;
    String firebaseId;

    public User() {

    }

    public User(String firebaseId, String name, String contact, String address, Boolean activeStatus) {
        this.activeStatus = activeStatus;
        this.contact = contact;
        this.name = name;
        this.address = address;
        this.firebaseId = firebaseId;
    }

//    public User(String firebaseId, String name, String contact, String address, Boolean activeStatus, FieldValue timestamp) {
//        this.activeStatus = activeStatus;
//        this.contact = contact;
//        this.name = name;
//        this.address = address;
//        this.firebaseId = firebaseId;
//        this.timestamp = timestamp;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}
