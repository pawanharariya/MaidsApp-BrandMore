package com.example.customerpanel.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.customerpanel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Maids24_7 extends AppCompatActivity {

    Button postRequirement, elderlyCare, cooking, cleaning, babySitting;
    String category = null, comment;
    int salary = 0;
    EditText salaryText, commentText;
    Drawable selectedBg, unSelectedBg;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maids24_7);
        getUI();
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        selectedBg = ContextCompat.getDrawable(Maids24_7.this, R.drawable.selected_blue_bg);
        unSelectedBg = ContextCompat.getDrawable(Maids24_7.this, R.drawable.green_corner);
        elderlyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Elderly Care";
                elderlyCare.setBackground(selectedBg);
                cooking.setBackground(unSelectedBg);
                babySitting.setBackground(unSelectedBg);
                cleaning.setBackground(unSelectedBg);

            }
        });
        cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Cooking";
                elderlyCare.setBackground(unSelectedBg);
                cooking.setBackground(selectedBg);
                babySitting.setBackground(unSelectedBg);
                cleaning.setBackground(unSelectedBg);
            }
        });
        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Cleaning";
                elderlyCare.setBackground(unSelectedBg);
                cooking.setBackground(unSelectedBg);
                babySitting.setBackground(unSelectedBg);
                cleaning.setBackground(selectedBg);
            }
        });
        babySitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Baby Sitting";
                elderlyCare.setBackground(unSelectedBg);
                cooking.setBackground(unSelectedBg);
                babySitting.setBackground(selectedBg);
                cleaning.setBackground(unSelectedBg);
            }
        });
        postRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentText.getText().toString();
                if (category == null) {
                    Toast.makeText(Maids24_7.this, "Please select required category.", Toast.LENGTH_SHORT).show();
                } else if (salaryText.getText().toString().equals("")) {
                    Toast.makeText(Maids24_7.this, "Please enter expected salary you can pay.", Toast.LENGTH_SHORT).show();
                } else {
                    salary = Integer.parseInt(salaryText.getText().toString());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String firebaseId = "firebase10";
                    String name = "XYZ";
                    String sex = "M";
                    String address = " Near so and so, Himachal Pradesh";
                    String pincode = "1";
                    Map<String, Object> details = new HashMap<>();
                    details.put("firebaseId", firebaseId);
                    details.put("location", pincode);
                    details.put("category", category);
                    details.put("confirmedId", null);
                    details.put("customerName", name);
                    details.put("sex", sex);
                    details.put("address", address);
                    details.put("confirmStatus", false);
                    details.put("salary", salary);
                    details.put("comment", comment);
                    db.collection("/partnersPanel/maids/maids24_7/").document()
                            .set(details)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    editor.putBoolean("seen2", false);
                                    editor.commit();
                                    Toast.makeText(Maids24_7.this, "Requirement posted successfully", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Maids24_7.this, "Unknown Error!", Toast.LENGTH_LONG).show();
                                }
                            });
                    Intent i = new Intent(Maids24_7.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        });
    }

    private void getUI() {
        postRequirement = findViewById(R.id.post_requirement_button);
        elderlyCare = findViewById(R.id.elderly_care);
        cooking = findViewById(R.id.cooking);
        cleaning = findViewById(R.id.cleaning);
        babySitting = findViewById(R.id.baby_sitting);
        salaryText = findViewById(R.id.salary);
        commentText = findViewById(R.id.comment);
    }
}
