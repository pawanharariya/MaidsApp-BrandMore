package com.example.customerpanel.Activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.customerpanel.R;
import com.example.customerpanel.adapters.BookingsAdapter;
import com.example.customerpanel.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Bookings extends AppCompatActivity {
    Drawable selectedBg, unSelectedBg;
    Button currentBookings, pastBookings;
    Boolean activeStatus;
    ListView bookingsListView;
    ArrayList<User> bookingsList;
    Context context;
    String firebaseId;
    BookingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        context = Bookings.this;
        bookingsList = new ArrayList<>();
        currentBookings = findViewById(R.id.current_bookings);
        pastBookings = findViewById(R.id.previous_bookings);
        bookingsListView = findViewById(R.id.bookings_list);
        selectedBg = ContextCompat.getDrawable(Bookings.this, R.drawable.selected_blue_bg);
        unSelectedBg = ContextCompat.getDrawable(Bookings.this, R.drawable.green_corner);
        firebaseId = "firebase10";//TODO changes here for user firebaseID
        currentBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookings.setBackground(selectedBg);
                pastBookings.setBackground(unSelectedBg);
                activeStatus = true;
                bookingsList.clear();
                getList();
                bookingsListView.deferNotifyDataSetChanged();
            }
        });
        pastBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastBookings.setBackground(selectedBg);
                currentBookings.setBackground(unSelectedBg);
                activeStatus = false;
                bookingsList.clear();
                getList();
            }
        });
        currentBookings.performClick();
    }

    private void getList() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("/customerPanel/bookings/" + firebaseId);
        colRef.whereEqualTo("activeStatus", activeStatus)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot doc : task.getResult()) {
                        User user = doc.toObject(User.class);
                        bookingsList.add(user);
                    }
                if (context != null) {
                    adapter = new BookingsAdapter(context, R.layout.list_item_bookings, bookingsList);
                    bookingsListView.setAdapter(adapter);
                }
            }
        });

    }
}
