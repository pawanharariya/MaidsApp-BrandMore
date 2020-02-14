package com.example.customerpanel.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerpanel.Activities.Chat;
import com.example.customerpanel.R;
import com.example.customerpanel.adapters.reviewAdapter;
import com.example.customerpanel.models.Review;
import com.example.customerpanel.models.Servant;
import com.example.customerpanel.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class servantProfile extends Fragment {
    View rootView;
    TextView name, age, sex, rating, cost, availability, distance, eta, workingTime, nameOther, about, address;
    TextView overallRating, timeRating, taskRating, behaviourRating, communicationRating;
    Button previousInfo, feedback, addToCart, reviews, bookNow;
    Servant currentServant;
    SharedPreferences sharedPrefs;
    String category;
    int exists = 0;//0 for doesn't exist and 1 for exist
    private RelativeLayout referencesDialog, feedbackDialog;
    private String currentServantId;
    private String pincode;

    public servantProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPrefs = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        pincode = sharedPrefs.getString("pincode", null);
        pincode = "1";//TODO changes here
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        category = sharedPrefs.getString("category", null);
        rootView = inflater.inflate(R.layout.servant_profile, container, false);
        final FloatingActionButton chat = rootView.findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Chat.class);
                startActivity(i);
            }
        });
        currentServant = (Servant) getArguments().getSerializable("currentServant");
        Boolean instantRequirement = getArguments().getBoolean("instantRequirement");
        final String firebaseId = "firebase10";
        String address = "customer's address";
        final String customerName = "Pawan";
        getUI();
        currentServantId = currentServant.getId();
        name.setText(currentServant.getName());
        age.setText("" + currentServant.getAge());
        sex.setText(currentServant.getSex());
        rating.setText("" + currentServant.getRating());
        about.setText(currentServant.getAbout());

        if (instantRequirement == true) {
            bookNow.setVisibility(View.GONE);
            addToCart.setVisibility(View.GONE);
        }

        referencesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referencesDialog.setVisibility(View.GONE);
            }
        });
        previousInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList referencesList = new ArrayList<Review>();
                String dbPath = "partnersPanel/" + category + "/references";
                CollectionReference colRef = db.collection(dbPath);
                colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Review review = doc.toObject(Review.class);
                                referencesList.add(review);
                                if (getContext() != null) {
                                    reviewAdapter adapter = new reviewAdapter(getContext(), R.layout.review_list_item, referencesList);
                                    ListView list = rootView.findViewById(R.id.references_list);
                                    list.setAdapter(adapter);
                                    referencesDialog.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dbPath = "partnersPanel/" + category + "/ratings/";
                DocumentReference docRef = db.collection(dbPath).document(currentServantId);
                docRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        taskRating.setText("" + document.get("taskRating"));
                                        timeRating.setText("" + document.get("timeRating"));
                                        behaviourRating.setText("" + document.get("behaviourRating"));
                                        communicationRating.setText("" + document.get("communicationRating"));
                                        overallRating.setText("" + document.get("overallRating"));
                                    }
                                }
                            }
                        });
                feedbackDialog.setVisibility(View.VISIBLE);
            }
        });
        feedbackDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackDialog.setVisibility(View.GONE);
            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviews reviewFragment = new reviews();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, reviewFragment)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servant cartItem = new Servant(currentServant.getId(), currentServant.getName(), currentServant.getAge(), currentServant.getSex(), 400, true, currentServant.getCategory());
                String dbPath = "customerPanel/cart/" + "firebaseId10/";//TODO change code for firebase id
                db.collection(dbPath).document(currentServantId)
                        .set(cartItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Added to your cart", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post in Partners Panel
                DocumentReference docRef = db.collection("partnersPanel/bookings/" + currentServantId).document();
                User customer = new User(firebaseId, customerName, "124454", "no address", true); //TODO necessary changes
                docRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    if (doc.exists())
                                        exists = 1;
                                }
                            }
                        });
                if (exists == 0) {
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
                    User servant = new User(currentServantId, currentServant.getName(), currentServant.getMobile(), "no address", true);
                    docRef2.set(servant)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                Toast.makeText(getContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else if (exists == 1)
                    Toast.makeText(getContext(), "Already Booked!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void getUI() {
        name = rootView.findViewById(R.id.name);
        age = rootView.findViewById(R.id.age);
        sex = rootView.findViewById(R.id.sex);
        about = rootView.findViewById(R.id.about);
        rating = rootView.findViewById(R.id.rating);
        cost = rootView.findViewById(R.id.cost);
        availability = rootView.findViewById(R.id.availability);
        distance = rootView.findViewById(R.id.distance);
        previousInfo = rootView.findViewById(R.id.previous_info);
        referencesDialog = rootView.findViewById(R.id.references_dialog);
        feedbackDialog = rootView.findViewById(R.id.feedback_dialog);
        feedback = rootView.findViewById(R.id.feedback);
        overallRating = rootView.findViewById(R.id.overall_rating);
        timeRating = rootView.findViewById(R.id.time_rating);
        taskRating = rootView.findViewById(R.id.task_rating);
        communicationRating = rootView.findViewById(R.id.communication_rating);
        behaviourRating = rootView.findViewById(R.id.behaviour_rating);
        addToCart = rootView.findViewById(R.id.add_to_cart);
        bookNow = rootView.findViewById(R.id.book_button);
        reviews = rootView.findViewById(R.id.reviews);
    }
}
