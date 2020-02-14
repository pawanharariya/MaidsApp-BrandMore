package com.example.customerpanel.models;

public class Review {

    String mName;
    Double mRating;
    String mReviews;
    String mDate;
    String mCity;

    public Review(String name, Double rating, String reviews, String date, String city) {
        mName = name;
        mRating = rating;
        mReviews = reviews;
        mDate = date;
        mCity = city;
    }

    public String getName() {
        return mName;
    }

    public Double getRating() {
        return mRating;
    }

    public String getReviews() {
        return mReviews;
    }

    public String geDate() {
        return mDate;
    }

    public String getCity() {
        return mCity;
    }

}
