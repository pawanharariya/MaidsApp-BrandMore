package com.example.customerpanel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customerpanel.R;
import com.example.customerpanel.models.Review;

import java.util.ArrayList;

public class reviewAdapter extends ArrayAdapter<Review> {

    public reviewAdapter(@NonNull Context context, int resource, ArrayList<Review> reviewList) {
        super(context, resource, reviewList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View reviewListView = convertView;
        if (reviewListView == null)
            reviewListView = LayoutInflater.from(getContext()).inflate(R.layout.review_list_item, parent, false);

        Review currentReview = getItem(position);

        TextView name = reviewListView.findViewById(R.id.name);
        TextView city = reviewListView.findViewById(R.id.city);
        TextView date = reviewListView.findViewById(R.id.date);
        TextView comment = reviewListView.findViewById(R.id.comment);
        TextView rating = reviewListView.findViewById(R.id.rating);

        name.setText(currentReview.getName());
        city.setText(currentReview.getCity());
        date.setText("" + currentReview.geDate());
        comment.setText(currentReview.getReviews());
        rating.setText("" + currentReview.getRating());

        return reviewListView;
    }
}
