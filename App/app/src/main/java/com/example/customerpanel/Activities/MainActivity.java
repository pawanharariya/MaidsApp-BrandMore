package com.example.customerpanel.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.customerpanel.R;
import com.example.customerpanel.fragments.cart;
import com.example.customerpanel.fragments.dashboard;
import com.example.customerpanel.fragments.home;
import com.example.customerpanel.fragments.listPeople;
import com.example.customerpanel.fragments.profile;
import com.example.customerpanel.models.NotifyService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    int color = 0xffD50000;
    int defaultColor = 0xff000000;
    ImageView home_button, profile_button, cart_button, dashboard_button;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    LinearLayout exitDialog;
    private int hour, min, billAmount;

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            exitDialog.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefs = getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final TextView cartItemsNumber = findViewById(R.id.cart_items_number);
        final TextView profile = findViewById(R.id.profileText);
        final TextView home = findViewById(R.id.homeText);
        final TextView dashboard = findViewById(R.id.dashboardText);
        final TextView cart = findViewById(R.id.cartText);
        profile_button = findViewById(R.id.profile_image);
        home_button = findViewById(R.id.home_image);
        dashboard_button = findViewById(R.id.dashboard_image);
        cart_button = findViewById(R.id.cart_image);
        exitDialog = findViewById(R.id.exit_dialog);

        final Drawable profile_image = ContextCompat.getDrawable(MainActivity.this, R.drawable.user);
        final Drawable home_image = ContextCompat.getDrawable(MainActivity.this, R.drawable.home);
        final Drawable dashboard_image = ContextCompat.getDrawable(MainActivity.this, R.drawable.dashboard);
        final Drawable cart_image = ContextCompat.getDrawable(MainActivity.this, R.drawable.cart);

        home.setTextColor(color);
        home_button.setImageDrawable(home_image);
        home_image.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_image.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                dashboard_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                home_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                cart_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                cart_button.setImageDrawable(cart_image);
                cart.setTextColor(defaultColor);
                profile_button.setImageDrawable(profile_image);
                home_button.setImageDrawable(home_image);
                dashboard_button.setImageDrawable(dashboard_image);
                profile.setTextColor(color);
                home.setTextColor(defaultColor);
                dashboard.setTextColor(defaultColor);

                profile profileFragment = new profile();
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i <= fragmentManager.getBackStackEntryCount(); i++)
                    fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, profileFragment)
                        .commit();

            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                dashboard_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                home_image.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                cart_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                cart_button.setImageDrawable(cart_image);
                cart.setTextColor(defaultColor);
                profile_button.setImageDrawable(profile_image);
                home_button.setImageDrawable(home_image);
                dashboard_button.setImageDrawable(dashboard_image);
                profile.setTextColor(defaultColor);
                home.setTextColor(color);
                dashboard.setTextColor(defaultColor);


                home homeFragment = new home();
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i <= fragmentManager.getBackStackEntryCount(); i++)
                    fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, homeFragment)
                        .commit();
            }
        });
        dashboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                dashboard_image.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                home_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                cart_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                cart_button.setImageDrawable(cart_image);
                cart.setTextColor(defaultColor);
                profile_button.setImageDrawable(profile_image);
                home_button.setImageDrawable(home_image);
                dashboard_button.setImageDrawable(dashboard_image);
                profile.setTextColor(defaultColor);
                home.setTextColor(defaultColor);
                dashboard.setTextColor(color);
                dashboard dashboardFragment = new dashboard();
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i <= fragmentManager.getBackStackEntryCount(); i++)
                    fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, dashboardFragment)
                        .commit();
            }
        });
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                dashboard_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                home_image.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.SRC_IN));
                cart_image.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                cart_button.setImageDrawable(cart_image);
                cart.setTextColor(color);
                profile_button.setImageDrawable(profile_image);
                home_button.setImageDrawable(home_image);
                dashboard_button.setImageDrawable(dashboard_image);
                profile.setTextColor(defaultColor);
                home.setTextColor(defaultColor);
                dashboard.setTextColor(defaultColor);

                cart cartFragment = new cart();
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i <= fragmentManager.getBackStackEntryCount(); i++)
                    fragmentManager.popBackStack();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, cartFragment)
                        .commit();
            }
        });

        Intent i = new Intent(getApplicationContext(), NotifyService.class);
        startService(i);

        home_button.performClick();

        TextView ignoreExit = findViewById(R.id.ignore_exit);
        ignoreExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.setVisibility(View.GONE);
            }
        });
        TextView confirmExit = findViewById(R.id.confirm_exit);
        confirmExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String pincode = "1";//TODO changes here
        String firebasId = "firebaseId10";
        String dbPath = "customerPanel/cart/" + firebasId;
        CollectionReference colRef = db.collection(dbPath);
        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                int cartItems = 0;
                for (QueryDocumentSnapshot doc : value) {
                    ++cartItems;
                }
                cartItemsNumber.setText("" + cartItems);
                if (cartItems == 0) {
                    cartItemsNumber.setText("");
                }

            }
        });

    }

    public void onClickMaids(View v) {
        editor.putString("title", "Maids near you");
        editor.putString("category", "maids");
        editor.commit();
        onButtonClicked();
    }

    public void onClickInstantMaids(View v) {
        LinearLayout view = findViewById(R.id.instant1);
        view.setVisibility(View.VISIBLE);
    }

    public void clearInstantMaids1(View v) {
        LinearLayout view = findViewById(R.id.instant1);
        view.setVisibility(View.GONE);
    }

    public void onClickJapaMaids(View v) {
        editor.putString("title", "Japa Maids near you");
        editor.putString("category", "japaMaids");
        editor.commit();
        onButtonClicked();
    }

    public void onClickMaids247(View v) {
        Intent i = new Intent(getApplicationContext(), Maids24_7.class);
        startActivity(i);
    }

    public void onClickOfficeBoys(View v) {
        editor.putString("title", "Office Boys near you");
        editor.putString("category", "officeBoys");
        editor.commit();
        onButtonClicked();
    }

    public void onButtonClicked() {
        listPeople peopleFragment = new listPeople();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHolder, peopleFragment)
                .addToBackStack("tag")
                .commit();
    }

    public void removeFilterDialog(View v) {
        RelativeLayout dialog = findViewById(R.id.filter_layout);
        dialog.setVisibility(View.GONE);
        Toast.makeText(this, "Filters not applied", Toast.LENGTH_SHORT).show();

    }

    public void getFilterDialog(View v) {
        RelativeLayout dialog = findViewById(R.id.filter_layout);
        dialog.setVisibility(View.VISIBLE);
    }

    public void onLocationClicked(View v) {
        Intent i = new Intent(getApplicationContext(), Location.class);
        startActivity(i);
    }

    public void onHomeClicked(View v) {
        home_button.performClick();
    }

    public void onCartClicked(View v) {
        cart_button.performClick();
    }

    public void onDashboardClicked(View v) {
        dashboard_button.performClick();
    }

    public void onProfileClicked(View v) {
        profile_button.performClick();
    }

    public void getBill(View v) {
        RelativeLayout dialog = findViewById(R.id.bill);
        ImageView back_instant = findViewById(R.id.back_instant);
        TextView bill = findViewById(R.id.instant_bill_amount);
        back_instant.setVisibility(View.VISIBLE);
        dialog.setVisibility(View.VISIBLE);
        Spinner spinner = findViewById(R.id.hours);
        String hours = spinner.getSelectedItem().toString();
        Spinner spinner2 = findViewById(R.id.minutes);
        String minutes = spinner2.getSelectedItem().toString();
        hour = Integer.parseInt(hours);
        min = Integer.parseInt(minutes);
        billAmount = (hour - 1) * 80 + 100 + min;
        bill.setText("INR: " + billAmount);

    }

    public void instantBooking(View v) {
        //Get information from sharedprefs
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String firebaseId = "firebase10";
        String name = "XYZ";
        String sex = "M";
        String address = " Near so and so, Himachal Pradesh";
        String pincode = "1";
        Map<String, Object> details = new HashMap<>();
        details.put("time", hour + " hours " + min + " minutes");
        details.put("location", pincode);
        details.put("customerName", name);
        details.put("sex", sex);
        details.put("address", address);
        details.put("firebaseId", firebaseId);
        details.put("amount", "" + billAmount);
        details.put("confirmedId", null);
        details.put("confirmStatus", false);
//        details.put("time", FieldValue.serverTimestamp());
        db.collection("/partnersPanel/maids/instant_maids_requirement/").document()
                .set(details)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Requirement posted successfully", Toast.LENGTH_LONG).show();
                        RelativeLayout dialog = findViewById(R.id.bill);
                        LinearLayout view = findViewById(R.id.instant1);
                        ImageView back_instant = findViewById(R.id.back_instant);
                        view.setVisibility(View.GONE);
                        editor.putBoolean("seen", false);
                        editor.commit();
                        back_instant.setVisibility(View.GONE);
                        dialog.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Unknown Error!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void applyFilters(View v) {
        RelativeLayout dialog = findViewById(R.id.filter_layout);
        dialog.setVisibility(View.GONE);
        Toast.makeText(this, "Filters applied", Toast.LENGTH_SHORT).show();

        //TODO apply code to filter searches here
    }

    public void getMenu(View v) {
        RelativeLayout menu = findViewById(R.id.menu_layout);
        menu.setVisibility(View.VISIBLE);
    }

    public void clearMenu(View v) {
        RelativeLayout menu = findViewById(R.id.menu_layout);
        menu.setVisibility(View.GONE);
    }

}