package com.example.customerpanel.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.customerpanel.R;
import com.example.customerpanel.fragments.notifications;
import com.example.customerpanel.fragments.servantProfile;
import com.example.customerpanel.models.Notification;
import com.example.customerpanel.models.Servant;
import com.example.customerpanel.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class notificationAdapter extends ArrayAdapter {
    Servant currentServant;
    Notification current;

    public notificationAdapter(Context context, int list_item_notifi, ArrayList<Notification> notificationsList) {
        super(context, list_item_notifi, notificationsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null)
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_notifi, parent, false);
        current = (Notification) getItem(position);
        final String confirmedId = current.getConfirmedId();
        TextView info = listView.findViewById(R.id.info);
        info.setText(current.getInfo());
        //get information from sharedPreferences
        final String firebaseId = "firebase10";
        String address = "customer's address";
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button bookNow = listView.findViewById(R.id.book_now);
        Button viewProfile = listView.findViewById(R.id.view_profile);

        DocumentReference docRef = db.collection("partnersPanel/maids/details").document(confirmedId);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null) {
                            currentServant = documentSnapshot.toObject(Servant.class);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Unknown Error.", Toast.LENGTH_SHORT).show();
                    }
                });

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servantProfile servantProfile = new servantProfile();
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, servantProfile)
                        .addToBackStack("tag")
                        .commit();
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentServant", currentServant);
                bundle.putBoolean("instantRequirement", true);
                servantProfile.setArguments(bundle);
            }
        });
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post in Partners Panel
                DocumentReference docRef = db.collection("partnersPanel/bookings/" + confirmedId).document();
                User customer = new User(firebaseId, "Pawan Singh", "124454", "no address", true);
                docRef.set(customer)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                            }
                        });

                //post in Customer Panel
                DocumentReference docRef2 = db.collection("customerPanel/bookings/" + firebaseId).document();
                User servant = new User(confirmedId, currentServant.getName(), currentServant.getMobile(), "no address", true);
                docRef2.set(servant)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

//                                Toast.makeText(getContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                                //Deleting document from requirements collection
                                int type = current.getType();
                                DocumentReference docRef3 = null;
                                if (type == 0) {
                                    docRef3 = db.collection("partnersPanel/maids/instant_maids_requirement").document(current.getId());
                                } else if (type == 1) {
                                    docRef3 = db.collection("partnersPanel/maids/maids24_7").document(current.getId());
                                }
                                if (docRef3 != null)
                                    docRef3.delete()
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            })
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                notifications notificationFragment = new notifications();
                                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                fm.beginTransaction()
                                        .replace(R.id.fragmentHolder, notificationFragment)
                                        .commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        return listView;
    }
}
