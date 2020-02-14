package com.example.customerpanel.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.customerpanel.R;
import com.example.customerpanel.adapters.servantAdapter;
import com.example.customerpanel.models.Servant;
import com.example.customerpanel.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class cart extends Fragment {
    static ListView listView;//TOdo think of some changes here
    View rootView;
    Servant servant;
    Button bookNow;
    LinearLayout cartEmpty;

    public cart() {
    }

    public static void listStateChanged() {
        setListViewHeightBasedOnChildren(listView);
    }

    public static void setListViewHeightBasedOnChildren
            (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cart_fragment, container, false);
        cartEmpty = rootView.findViewById(R.id.cart_empty);
        bookNow = rootView.findViewById(R.id.book_now);
        final ArrayList<Servant> cartList = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String pincode = "1";//TODO changes here
        final String firebasId = "firebaseId10";
        String dbPath = "customerPanel/cart/" + firebasId;
        CollectionReference colRef = db.collection(dbPath);
        colRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cartList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Servant cartItem = document.toObject(Servant.class);
                                cartList.add(cartItem);
                            }
                            if (getContext() != null) {
                                servantAdapter adapter = new servantAdapter(getContext(), R.layout.servant_list_item, cartList, 2);
                                listView = rootView.findViewById(R.id.list_cart);
                                listView.setAdapter(adapter);
                                setListViewHeightBasedOnChildren(listView);
//                                if(listView.getCount()==0) {
//                                    cartEmpty.setVisibility(View.VISIBLE);
//
//                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching items from cart
                String dbPath = "customerPanel/cart/" + firebasId;
                CollectionReference colRef = db.collection(dbPath);
                colRef.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        servant = document.toObject(Servant.class);
                                        makeBooking(servant);
                                        deleteFromCart(servant);
                                    }
                                    cart cart = new cart();
                                    Toast.makeText(getContext(), "All Bookings Done", Toast.LENGTH_SHORT).show();
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    fm.beginTransaction()
                                            .replace(R.id.fragmentHolder, cart)
                                            .commit();

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        return rootView;
    }

    public void makeBooking(Servant servant) {
        //post in Partners Panel
        String firebaseId = "firebase10";
        String customerName = "Pawan Singh";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("partnersPanel/bookings/" + servant.getId()).document();
        User customer = new User(firebaseId, customerName, "124454", "no address", true); //TODO necessary changes
        docRef.set(customer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
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
        User servantBooked = new User(servant.getId(), servant.getName(), servant.getMobile(), "no address", true);
        docRef2.set(servantBooked)
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
    }

    public void deleteFromCart(Servant servant) {
        String firebaseId = "firebaseId10";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("customerPanel/cart/" + firebaseId).document(servant.getId());
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
}