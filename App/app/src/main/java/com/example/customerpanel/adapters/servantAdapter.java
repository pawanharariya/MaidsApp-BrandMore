package com.example.customerpanel.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customerpanel.R;
import com.example.customerpanel.fragments.cart;
import com.example.customerpanel.models.Servant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class servantAdapter extends ArrayAdapter<Servant> {
    Servant currentServant;
    private int mType;
    private ArrayList<Servant> mAddedToCart;

    public servantAdapter(@NonNull Context context, int resource, ArrayList<Servant> list, int type) {
        super(context, resource, list);
        mType = type;
        this.mAddedToCart = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (mType == 1) {
            if (listItemView == null)
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.servant_list_item, parent, false);
            currentServant = getItem(position);
            TextView name = listItemView.findViewById(R.id.name);
            TextView age = listItemView.findViewById(R.id.age);
            TextView avail = listItemView.findViewById(R.id.availability);
            TextView rate = listItemView.findViewById(R.id.rating);
            TextView cost = listItemView.findViewById(R.id.cost);
            TextView distance = listItemView.findViewById(R.id.distance);
            TextView eta = listItemView.findViewById(R.id.eta);
            name.setText(currentServant.getName());
            age.setText("Age: " + currentServant.getAge());
            avail.setText("Available S,M,T,W,T,F,S 8-12 12-4 4-8");
            rate.setText("" + currentServant.getRating());
            distance.setText("Distance: 25km");
            cost.setText("Rs. 400/hr");
            eta.setText("ETA: 25min");

            return listItemView;
        } else if (mType == 2) {
            if (listItemView == null)
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.cart_list_item, parent, false);
            final Servant currentServant = getItem(position);
            final TextView category = listItemView.findViewById(R.id.category);
            TextView name = listItemView.findViewById(R.id.name);
            TextView ageSex = listItemView.findViewById(R.id.age_sex);
            TextView avail = listItemView.findViewById(R.id.availability);
            TextView cost = listItemView.findViewById(R.id.cost);
            Button delete = listItemView.findViewById(R.id.delete_from_cart);
            delete.setTag(position);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) v.getTag();
                    String path = "customerPanel/cart/" + "firebaseId10";
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(path).document(currentServant.getId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                }
                            });
                    mAddedToCart.remove(id);
                    notifyDataSetChanged();
                    cart.listStateChanged();
                }
            });
            category.setText("Added " + currentServant.getCategory());
            name.setText(currentServant.getName());
            ageSex.setText("Age: " + currentServant.getAge());
            avail.setText("Available");
            cost.setText("Rs. 400/hr");

            return listItemView;
        }
        return listItemView;
    }
}
