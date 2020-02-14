package com.example.customerpanel.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customerpanel.R;
import com.example.customerpanel.adapters.reviewAdapter;
import com.example.customerpanel.models.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class reviews extends Fragment {
    public reviews() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.reviews, container, false);
        final ArrayList reviewList = new ArrayList<Review>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //TODO changes for category here:
        String dbPath = "partnersPanel/maids/reviews";
        CollectionReference colRef = db.collection(dbPath);
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        Review review = doc.toObject(Review.class);
                        reviewList.add(review);
                        if (getContext() != null) {
                            reviewAdapter adapter = new reviewAdapter(getContext(), R.layout.review_list_item, reviewList);
                            ListView list = rootView.findViewById(R.id.review_list);
                            list.setAdapter(adapter);
                        }
                    }
                }
            }
        });
        return rootView;
    }
}
