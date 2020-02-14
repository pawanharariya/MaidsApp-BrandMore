package com.example.customerpanel.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.customerpanel.Activities.Bookings;
import com.example.customerpanel.Activities.Chat;
import com.example.customerpanel.Activities.Location;
import com.example.customerpanel.Activities.MainActivity;
import com.example.customerpanel.R;
import com.example.customerpanel.adapters.viewPager;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class home extends Fragment {
    ViewPager viewpager;
    viewPager adapter;
    TextView chat, home, profile, dashboard, cart, location, servingArea, bookings, notifications;
    View rootView;
    //    ArrayList<String> imageUrls = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int currentPage = 0;
    private int NUM_PAGES = 4;

    public home() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        sharedPreferences = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ImageView back_instant = rootView.findViewById(R.id.back_instant);
        back_instant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout view = rootView.findViewById(R.id.instant1);
                view.setVisibility(View.VISIBLE);
                RelativeLayout bill = rootView.findViewById(R.id.bill);
                bill.setVisibility(View.GONE);
                ImageView back_instant = rootView.findViewById(R.id.back_instant);
                back_instant.setVisibility(View.GONE);
            }
        });
        final ImageView notificationButton = rootView.findViewById(R.id.notification);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifications notificationFragment = new notifications();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentHolder, notificationFragment)
                        .addToBackStack("tag")
                        .commit();
            }
        });
//        if (sharedPreferences.getBoolean("images", false)) {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            DocumentReference docRef = db.collection("customerPanel/").document("bannerImages");
//            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            Log.e("images", document.toString());
//                            imageUrls = (ArrayList<String>) document.get("images");
//                            editor.putString("image1", imageUrls.get(0));
//                            editor.putString("image2", imageUrls.get(1));
//                            editor.putString("image3", imageUrls.get(2));
//                            editor.putString("image4", imageUrls.get(3));
//                            editor.putBoolean("images",true);
//                            editor.commit();
//                            setViewpager();
//                        } else
//                            Log.e("document", "document does not exists");
//                    } else Log.e("tasK", "task not successful");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.e("error", e.toString() + e.getCause());
//                }
//            });
//        } else {
//            imageUrls.add(sharedPreferences.getString("image1", null));
//            imageUrls.add(sharedPreferences.getString("image2", null));
//            imageUrls.add(sharedPreferences.getString("image3", null));
//            imageUrls.add(sharedPreferences.getString("image4", null));
//            setViewpager();
//        }
        getMenuUI();
        setViewpager();
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Chat.class);
                startActivity(i);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Location.class);
                startActivity(i);
            }
        });
        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Bookings.class);
                startActivity(i);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationButton.performClick();
            }
        });


        return rootView;
    }

    public void getMenuUI() {
        chat = rootView.findViewById(R.id.chat);
        home = rootView.findViewById(R.id.home);
        profile = rootView.findViewById(R.id.profile);
        cart = rootView.findViewById(R.id.cart);
        dashboard = rootView.findViewById(R.id.dashboard);
        location = rootView.findViewById(R.id.location);
        notifications = rootView.findViewById(R.id.notifications);
        servingArea = rootView.findViewById(R.id.serving_area);
        bookings = rootView.findViewById(R.id.bookings);
    }

    private void setViewpager() {
        viewpager = rootView.findViewById(R.id.pager);
        final int[] imageUrls = {R.drawable.image1, R.drawable.image2, R.drawable.image1, R.drawable.image2};
        adapter = new viewPager((MainActivity) getContext(), imageUrls);
        if (getContext() != null)                                 //Context checking required here
            viewpager.setAdapter(adapter);
        CircleIndicator indicator = rootView.findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
        adapter.notifyDataSetChanged();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                viewpager.setCurrentItem(currentPage, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = imageUrls.length;
                    if (currentPage == 0) {
//                        viewpager.setCurrentItem(pageCount - 1, false);
                    } else if (currentPage == pageCount - 1) {
//                        viewpager.setCurrentItem(0, false);
                    }
                }
            }
        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {

            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                adapter.notifyDataSetChanged();
                viewpager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 5000);
    }
}
