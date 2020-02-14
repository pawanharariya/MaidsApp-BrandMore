package com.example.customerpanel.models;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.customerpanel.Activities.MainActivity;
import com.example.customerpanel.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NotifyService extends Service {
    final String ACTION = "NotifyServiceAction";
    BroadcastReceiver receiver;
    Intent intents;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intents = intent;
            }
        };
        super.onCreate();
        sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String pincode = "1";//TODO changes here
        String firebaseId = "firebase10";
        String dbPath = "partnersPanel/maids/instant_maids_requirement";
        CollectionReference colRef = db.collection(dbPath);
        colRef.whereEqualTo("confirmStatus", true)
                .whereEqualTo("firebaseId", firebaseId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        Boolean firebaseChanges;
                        for (QueryDocumentSnapshot doc : value) {
                            firebaseChanges = doc.getBoolean("confirmStatus");
                            if (firebaseChanges) {
                                if (sharedPreferences.getBoolean("seen", false) == false) {
                                    editor.putBoolean("seen", true);
                                    editor.commit();
                                    onStart(intents, 0, 0);
                                }
                            }
                        }
                    }
                });

        String dbPath2 = "partnersPanel/maids/maids24_7";
        CollectionReference colRef2 = db.collection(dbPath2);
        colRef2.whereEqualTo("confirmStatus", true)
                .whereEqualTo("firebaseId", firebaseId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        Boolean firebaseChanges;
                        for (QueryDocumentSnapshot doc : value) {
                            firebaseChanges = doc.getBoolean("confirmStatus");
                            if (firebaseChanges == true) {
                                if (sharedPreferences.getBoolean("seen2", false) == false) {
                                    editor.putBoolean("seen2", true);
                                    editor.commit();
                                    onStart(intents, 0, 0);
                                }
                            }
                        }
                    }
                });
//                addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    return;
//                }
//                if (sharedPreferences.getBoolean("seen", false) == false) {
//                    Boolean confirmStatus = documentSnapshot.getBoolean("confirmStatus");
//                    Toast.makeText(getApplicationContext(), "confirm " + confirmStatus, Toast.LENGTH_SHORT).show();
//                    if (confirmStatus == true) {
//                        editor.putBoolean("seen", true);
//                        editor.commit();
//                        Toast.makeText(getApplicationContext(), "seen  " + sharedPreferences.getBoolean("seen", false), Toast.LENGTH_SHORT).show();
//                        onStart(intents, 0, 0);
//                    }
//                }
//            }
//        });

//
    }

    public void onStart(Intent intent, int flags, int startId) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(receiver, intentFilter);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "instantMaidNotification");
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("notification", "true");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.info);
        mBuilder.setContentTitle("User App");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mBuilder.setContentText("You have a new notification.");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());

        //  return super.onStartCommand(intent, flags, startId);
    }
}
