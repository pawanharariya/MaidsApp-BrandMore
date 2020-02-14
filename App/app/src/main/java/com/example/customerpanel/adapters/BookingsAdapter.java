package com.example.customerpanel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customerpanel.R;
import com.example.customerpanel.models.User;

import java.util.ArrayList;

public class BookingsAdapter extends ArrayAdapter {
    User currentUser;
    TextView name, contact;
    Button rate;

    public BookingsAdapter(@NonNull Context context, int resource, ArrayList<User> bookingsList) {
        super(context, resource, bookingsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null)
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_bookings, parent, false);
        currentUser = (User) getItem(position);
        name = listView.findViewById(R.id.name);
        contact = listView.findViewById(R.id.contact);
        name.setText(currentUser.getName());
        rate = listView.findViewById(R.id.rate_servant);
        contact.setText(currentUser.getContact());
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servantFirebaseId = currentUser.getFirebaseId();

            }
        });
        return listView;
    }
}
