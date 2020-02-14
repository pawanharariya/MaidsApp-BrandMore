package com.example.partnerspanel.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.partnerspanel.R;
import com.example.partnerspanel.adapters.notificationAdapter;
import com.example.partnerspanel.models.NotificationItem;
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
        final ArrayList<NotificationItem> notificationsList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String pincode = "1";//TODO changes here
        String dbPath = "partnersPanel/maids/instant_maids_requirement";
        CollectionReference colRef = db.collection(dbPath);
        colRef.whereEqualTo("location", "1")
                .whereEqualTo("confirmStatus", false)
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
                            String name = doc.getString("customerName");
                            String sex = doc.getString("sex");
                            String address = doc.getString("address");
                            String info = "A need for instant maid for " + time + " with INR " + amount;
                            NotificationItem current = new NotificationItem(id, info, name, sex, address, 0);
                            notificationsList.add(current);
                        }
                        if (getContext() != null) {
                            notificationAdapter adapter = new notificationAdapter(getContext(), R.layout.list_item_notifi, notificationsList);
                            ListView listView = rootView.findViewById(R.id.list_notifi);
                            listView.setAdapter(adapter);
                        }
                    }
                });
        String dbPath2 = "partnersPanel/maids/maids24_7";
        CollectionReference colRef2 = db.collection(dbPath2);
        colRef2.whereEqualTo("location", "1")
                .whereEqualTo("confirmStatus", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            String salary = String.valueOf(doc.get("salary"));
                            String comment = ". The user also says: " + doc.getString("comment");
                            String category = doc.getString("category");
                            String name = doc.getString("customerName");
                            String sex = doc.getString("sex");
                            String address = doc.getString("address");
                            String info;
                            String id = doc.getId();
                            if (doc.getString("comment").equals("")) {
                                info = "A need for 24X7 maid for " + category + " purpose with salary INR " + salary;
                            } else {
                                info = "A need for 24X7 maid for " + category + " purpose with salary INR " + salary + comment;
                            }
                            NotificationItem current = new NotificationItem(id, info, name, sex, address, 1);
                            notificationsList.add(current);
                        }
                        if (getContext() != null) {
                            notificationAdapter adapter = new notificationAdapter(getContext(), R.layout.list_item_notifi, notificationsList);
                            ListView listView = rootView.findViewById(R.id.list_notifi);
                            listView.setAdapter(adapter);
                        }
                    }
                });
        return rootView;
    }
}
