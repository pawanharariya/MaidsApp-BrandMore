package com.example.partnerspanel.models;

import java.io.Serializable;

public class Servant implements Serializable {

    private String mId;
    private String mName;
    private String mNameOther;
    private int mMobile;
    private int mAge;
    private boolean mAvailability;
    private double mCost;
    private String mCategory;
    private String mImage;
    private double mRating;
    private String mAbout;
    private String mLocation;
    private String mSex;
    private double mDistance;
    private double mEta;


    public Servant() {

    }

    public Servant(String id, String name, int age, String sex, double cost, boolean availability, String category, String  pincode) {
        mId = id;
        mLocation = pincode;
        mName = name;
        mAge = age;
        mSex = sex;
        mCost = cost;
        mAvailability = availability;
        mCategory = category;
    }

    public Servant(String name, String nameOther, double rating, int age, String sex, String about) {
        mName = name;
        mRating = rating;
        mNameOther = nameOther;
        mAge = age;
        mAbout = about;
        mSex = sex;
    }

    public void setname(String name) {
        this.mName = name;
    }

    public void setid(String id) {
        this.mId = id;
    }

    public void setage(int age) {
        this.mAge = age;
    }

    public void setsex(String sex) {
        this.mSex = sex;
    }

    public void setabout(String about) {
        this.mAbout = about;
    }

    public void setnameOther(String nameOther) {
        this.mNameOther = nameOther;
    }

    public void setrating(double rating) {
        this.mRating = rating;
    }

    public void setLocation(String pincode) {
        this.mLocation = pincode;
    }

    public String  getLocation() {
        return mLocation;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getNameOther() {
        return mNameOther;
    }

    public int getMobile() {
        return mMobile;
    }

    public int getAge() {
        return mAge;
    }

    public boolean isAvailability() {
        return mAvailability;
    }

    public double getCost() {
        return mCost;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getImage() {
        return mImage;
    }

    public double getRating() {
        return mRating;
    }

    public double getDistance() {
        return mDistance;
    }

    public double getEta() {
        return mEta;
    }

    public String getSex() {
        return mSex;
    }

    public String getAbout() {
        return mAbout;
    }

}
