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
import com.example.customerpanel.adapters.notificationAdapter;
import com.example.customerpanel.models.Notification;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class notifications extends Fragment {
    View rootView;

    public notifications() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        final ArrayList<Notification> notificationsList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ListView listView = rootView.findViewById(R.id.list_notifi);
        String pincode = "1";//TODO changes here
        String firebaseId = "firebase10";
        String dbPath = "partnersPanel/maids/instant_maids_requirement";
        CollectionReference colRef = db.collection(dbPath);
        colRef.whereEqualTo("confirmStatus", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            String amount = doc.getString("amount");
                            String time = doc.getString("time");
                            String id = doc.getId();
                            String confirmedId = doc.getString("confirmedId");
                            String info = "Your post for requirement of instant maid for " + time + " with INR " + amount + " is accepted by a maid.";
                            Notification current = new Notification(id, info, confirmedId, 0);
                            notificationsList.add(current);
                        }
                        if (getContext() != null) {
                            notificationAdapter adapter = new notificationAdapter(getContext(), R.layout.list_item_notifi, notificationsList);
                            listView.setAdapter(adapter);
                        }
                    }
                });

        String dbPath2 = "partnersPanel/maids/maids24_7/";
        CollectionReference colRef2 = db.collection(dbPath2);
        colRef2.whereEqualTo("confirmStatus", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            Long salary = (Long) doc.get("salary");
                            String category = doc.getString("category");
                            String id = doc.getId();
                            String confirmedId = doc.getString("confirmedId");
                            String info = "Your post for requirement of 24X7 maid for " + category + " purpose with salary amount INR " + salary + " is accepted by a maid.";
                            Notification current = new Notification(id, info, confirmedId, 1);
                            notificationsList.add(current);
                        }
                        if (getContext() != null) {
                            notificationAdapter adapter = new notificationAdapter(getContext(), R.layout.list_item_notifi, notificationsList);
                            listView.setAdapter(adapter);
                        }
                    }
                });
        return rootView;
    }
}
