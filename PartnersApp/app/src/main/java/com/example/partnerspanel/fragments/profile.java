package com.example.partnerspanel.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.partnerspanel.R;
import com.example.partnerspanel.models.Servant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class profile extends Fragment {
    TextView save;
    View rootView;
    EditText nameText, ageText, pincodeText, workRateText;
    String pincode;
    private String category = "maids", name;
    private int age;
    private double workRate;

    public profile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        getUI();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                if (ageText.getText().toString().equals("")) age = 0;
                else
                    age = Integer.parseInt(ageText.getText().toString());
                if (workRateText.getText().toString().equals("")) workRate = 0;
                else
                    workRate = Double.parseDouble(workRateText.getText().toString());
//                if (pincodeText.getText().toString().equals("")) pincode = 0;
//                else
                pincode = (pincodeText.getText().toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String firebaseId = "firebaseId12";
                Servant newServant = new Servant(firebaseId, name, age, "M", workRate, true, category, pincode);
                String dbpath = "partnersPanel/" + category + "/details/";
                db.collection(dbpath).document(firebaseId)
                        .set(newServant)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Profile created", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        return rootView;
    }

    public void getUI() {
        save = rootView.findViewById(R.id.save);
        nameText = rootView.findViewById(R.id.name);
        ageText = rootView.findViewById(R.id.age);
        workRateText = rootView.findViewById(R.id.workRate);
        pincodeText = rootView.findViewById(R.id.pincode);
    }
}
