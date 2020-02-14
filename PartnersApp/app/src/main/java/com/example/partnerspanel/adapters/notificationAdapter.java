package com.example.partnerspanel.adapters;

import android.content.Context;
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

import com.example.partnerspanel.R;
import com.example.partnerspanel.fragments.notifications;
import com.example.partnerspanel.models.NotificationItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class notificationAdapter extends ArrayAdapter {

    public notificationAdapter(Context context, int list_item_notifi, ArrayList<NotificationItem> notificationsList) {
        super(context, list_item_notifi, notificationsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null)
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_notifi, parent, false);
        final NotificationItem current = (NotificationItem) getItem(position);
        final String firebaseId = "firebaseId12";
        TextView info = listView.findViewById(R.id.info);
        TextView customerName = listView.findViewById(R.id.customer_name);
        TextView address = listView.findViewById(R.id.address);
        customerName.setText("Customer's Name: " + current.getCustomerName() + "(" + current.getSex() + ")");
        address.setText("Address: " + current.getAddress());
        info.setText(current.getInfo());
        Button accept = listView.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("confirmedId", firebaseId);
                data.put("confirmStatus", true);
                //data.put("category", "maids");//TODO changes here
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = null;
                if (current.getType() == 0)
                    docRef = db.collection("/partnersPanel/maids/instant_maids_requirement/").document(current.getId());
                else if (current.getType() == 1)
                    docRef = db.collection("/partnersPanel/maids/maids24_7/").document(current.getId());

                docRef.set(data, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notifications notifications = new notifications();
                                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                fm.beginTransaction()
                                        .replace(R.id.fragment_holder, notifications)
                                        .commit();
//                                notifications.fetchData();
                                //TODO necessary changes here for deletion
                                Toast.makeText(getContext(), "Your accept request is sent.", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Unknown Error.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        notifyDataSetChanged();
        return listView;
    }
}
