package com.bitsgroove.corona.fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitsgroove.corona.extra.AlarmReciever;
import com.bitsgroove.corona.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.bitsgroove.corona.extra.NotificationAPP.CHANNEL_REMINDER;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

    ImageView[] img_notice = new ImageView[5];
    Boolean[] isSelected = new Boolean[5];
    AlarmManager[] drinkAlarms = new AlarmManager[5];
    AlarmManager [] breathAlarm = new AlarmManager[2];
    AlarmManager [] sanitizeAlarm = new AlarmManager[4];
    ArrayList<PendingIntent> drinkingIntents = new ArrayList<>();
    ArrayList<PendingIntent> breathIntents = new ArrayList<>();
    ArrayList<PendingIntent> sanitizeIntents = new ArrayList<>();
    private AdView mAdView2;

    private NotificationManagerCompat notificationManager;

    private static final String TAG = "NoticeFragment - ";
    public static final String FRAGMENT_TITLE = "Notification";
    public static final String NOTI_TITLE = "Reminder";
    public static final String NOTI_1 = "Wash Your Hands If You Just Came Home";
    public static final String NOTI_2 = "Drink Water To Stay Hydrated";
    public static final String NOTI_3 = "Lets Take A Breathing Test";
    public static final String NOTI_4 = "Use Sanitizer";
    public static final String NOTI_5 = "Use Mask If You Are Leaving Your House";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switch1";
    public static final String SWITCH2 = "switch2";
    public static final String SWITCH3 = "switch3";
    public static final String SWITCH4 = "switch4";
    public static final String SWITCH5 = "switch5";
    private int STORAGE_PERMISSION_CODE = 123;

    private WifiManager wifiManager;

    public NoticeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notice, container, false);

        getActivity().setTitle(FRAGMENT_TITLE);
        notificationManager = NotificationManagerCompat.from(getContext());

        img_notice[0] = v.findViewById(R.id.noticeHome);
        img_notice[1] = v.findViewById(R.id.noticeWater);
        img_notice[2] = v.findViewById(R.id.noticeBreath);
        img_notice[3] = v.findViewById(R.id.noticeWash);
        img_notice[4] = v.findViewById(R.id.noticeMask);

        // Initialise ads
        mAdView2 = v.findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest); //End

        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        loadData();

        img_notice[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSelected[0]) {
                    img_notice[0].setImageResource(R.drawable.ic_disable);
                    isSelected[0] = false;
                    getActivity().unregisterReceiver(wifiONReceiver);

                } else {
                    img_notice[0].setImageResource(R.drawable.ic_enable);
                    isSelected[0] = true;
                    Log.d(TAG, " ---- Hand CLICKED ---- ");
                    IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
                    getActivity().registerReceiver(wifiONReceiver, intentFilter);

                }
                saveData();
            }
        });
        img_notice[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected[1]) {
                    img_notice[1].setImageResource(R.drawable.ic_disable);
                    isSelected[1] = false;
                    cancelDrinkAlarm();

                } else {
                    img_notice[1].setImageResource(R.drawable.ic_enable);
                    isSelected[1] = true;
                    Log.d(TAG, " ---- DRINK CLICKED ---- ");
                    createDrinkAlarm(2, NOTI_2);
                }
                saveData();
            }
        });
        img_notice[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected[2]) {
                    img_notice[2].setImageResource(R.drawable.ic_disable);
                    isSelected[2] = false;
                    cancelBreathAlarm();
                } else {
                    img_notice[2].setImageResource(R.drawable.ic_enable);
                    isSelected[2] = true;
                    Log.d(TAG, " ---- Breath CLICKED ---- ");
                    createBreathAlarm(3, NOTI_3);
                }
                saveData();
            }
        });
        img_notice[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected[3]) {
                    img_notice[3].setImageResource(R.drawable.ic_disable);
                    isSelected[3] = false;
                    cancelSanitizeAlarm();
                } else {
                    img_notice[3].setImageResource(R.drawable.ic_enable);
                    isSelected[3] = true;
                    Log.d(TAG, " ---- Sanitizer CLICKED ---- ");
                    createSanitizeAlarm(4, NOTI_4);
                }
                saveData();

            }
        });
        img_notice[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected[4]) {
                    img_notice[4].setImageResource(R.drawable.ic_disable);
                    isSelected[4] = false;
                    getActivity().unregisterReceiver(wifiOFFReceiver);
                } else {
                    img_notice[4].setImageResource(R.drawable.ic_enable);
                    isSelected[4] = true;
                    Log.d(TAG, " ---- Mask CLICKED ---- ");
                    IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
                    getActivity().registerReceiver(wifiOFFReceiver, intentFilter);
                }
                saveData();

            }
        });

        return v;
    }

    private void setMask() {
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_REMINDER)
                .setSmallIcon(R.drawable.ic_enable)
                .setContentTitle(NOTI_TITLE)
                .setContentText(NOTI_5)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(5, notification);
        Log.d(TAG, "--------- NOTIFICATION MASK ---------");
    }

    private void setHandWash() {

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_REMINDER)
                            .setSmallIcon(R.drawable.ic_enable)
                            .setContentTitle(NOTI_TITLE)
                            .setContentText(NOTI_1)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();

                    notificationManager.notify(1, notification);
                    Log.d(TAG, "--------- NOTIFICATION HAND ---------");
    }

    private BroadcastReceiver wifiONReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    setHandWash();
                    break;
            }
        }
    };

    private BroadcastReceiver wifiOFFReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_DISABLED:
                    setMask();
                    break;
            }
        }
    };

    public void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SWITCH1, isSelected[0]);
        editor.putBoolean(SWITCH2, isSelected[1]);
        editor.putBoolean(SWITCH3, isSelected[2]);
        editor.putBoolean(SWITCH4, isSelected[3]);
        editor.putBoolean(SWITCH5, isSelected[4]);

        editor.apply();

        Log.d(TAG, " ------ Saved to shared preferences ------ ");
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        isSelected[0] = sharedPreferences.getBoolean(SWITCH1, false);
        isSelected[1] = sharedPreferences.getBoolean(SWITCH2, false);
        isSelected[2] = sharedPreferences.getBoolean(SWITCH3, false);
        isSelected[3] = sharedPreferences.getBoolean(SWITCH4, false);
        isSelected[4] = sharedPreferences.getBoolean(SWITCH5, false);
        update();

    }

    public void registerWifi() {
        if (isSelected[0]) {
            IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
            getActivity().registerReceiver(wifiONReceiver, intentFilter);
        }
        if (isSelected[4]) {
            IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
            getActivity().registerReceiver(wifiOFFReceiver, intentFilter);
        }
    }

    public void update(){
        for (int i = 0; i < 5; i++) {
            if (isSelected[i]){
                img_notice[i].setImageResource(R.drawable.ic_enable);
            }
        }
        registerWifi();
    }

    private void cancelDrinkAlarm() {
        if(drinkingIntents.size()>0){
            for(int i=0; i<drinkingIntents.size(); i++){
                drinkAlarms[i].cancel(drinkingIntents.get(i));
            }
            drinkingIntents.clear();
        }
        Log.d(TAG, "----- Canceled ---- ");
    }

    private void createDrinkAlarm(int noti_id, String noti_text) {
        Calendar[] calendars = new Calendar[5];
        for (int i = 0; i < 5; i++) {
            calendars[i] = Calendar.getInstance();
        }

        calendars[0].set(Calendar.HOUR_OF_DAY, 9);
        calendars[0].set(Calendar.MINUTE,0);
        calendars[1].set(Calendar.HOUR_OF_DAY, 12);
        calendars[1].set(Calendar.MINUTE, 0);
        calendars[2].set(Calendar.HOUR_OF_DAY, 15);
        calendars[2].set(Calendar.MINUTE, 0);
        calendars[3].set(Calendar.HOUR_OF_DAY, 18);
        calendars[3].set(Calendar.MINUTE, 0);
        calendars[4].set(Calendar.HOUR_OF_DAY, 21);
        calendars[4].set(Calendar.MINUTE, 0);
        for (int f = 0; f < 5; f++) {
            Intent intent = new Intent(getContext(), AlarmReciever.class);
            intent.putExtra("NotiID", noti_id);
            intent.putExtra("NotiMsg", noti_text);
            intent.putExtra("msg", calendars[f].toString());
            PendingIntent pi = PendingIntent.getBroadcast(getContext(), f*10,intent, 0);

            drinkAlarms[f] = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
            drinkAlarms[f].setRepeating(AlarmManager.RTC_WAKEUP, calendars[f].getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

            drinkingIntents.add(pi);
        }

    }

    private void createBreathAlarm(int noti_id, String noti_text) {
        Calendar[] calendars = new Calendar[2];
        for (int i = 0; i < 2; i++) {
            calendars[i] = Calendar.getInstance();
        }
        calendars[0].set(Calendar.HOUR_OF_DAY, 10);
        calendars[0].set(Calendar.MINUTE,0);
        calendars[1].set(Calendar.HOUR_OF_DAY, 20);
        calendars[1].set(Calendar.MINUTE, 0);
        for (int f = 0; f < 2; f++) {
            Intent intent = new Intent(getContext(), AlarmReciever.class);
            intent.putExtra("NotiID", noti_id);
            intent.putExtra("NotiMsg", noti_text);
            intent.putExtra("msg", calendars[f].toString());
            PendingIntent pi = PendingIntent.getBroadcast(getContext(), f*100,intent, 0);

            breathAlarm[f] = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
            breathAlarm[f].setRepeating(AlarmManager.RTC_WAKEUP, calendars[f].getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

            breathIntents.add(pi);
        }
    }

    private void cancelBreathAlarm() {
        if(breathIntents.size()>0){
            for(int i=0; i<breathIntents.size(); i++){
                breathAlarm[i].cancel(breathIntents.get(i));
            }
            breathIntents.clear();
        }
        Log.d(TAG, "----- Canceled ---- ");
    }

    private void createSanitizeAlarm(int noti_id, String noti_text) {
        Calendar[] calendars = new Calendar[4];
        for (int i = 0; i < 4; i++) {
            calendars[i] = Calendar.getInstance();
        }

        calendars[0].set(Calendar.HOUR_OF_DAY, 9);
        calendars[0].set(Calendar.MINUTE,30);
        calendars[1].set(Calendar.HOUR_OF_DAY, 13);
        calendars[1].set(Calendar.MINUTE, 0);
        calendars[2].set(Calendar.HOUR_OF_DAY, 16);
        calendars[2].set(Calendar.MINUTE, 0);
        calendars[3].set(Calendar.HOUR_OF_DAY, 19);
        calendars[3].set(Calendar.MINUTE, 0);

        for (int f = 0; f < 4; f++) {
            Intent intent = new Intent(getContext(), AlarmReciever.class);
            intent.putExtra("NotiID", noti_id);
            intent.putExtra("NotiMsg", noti_text);
            intent.putExtra("msg", calendars[f].toString());

            PendingIntent pi = PendingIntent.getBroadcast(getContext(), f*1000,intent, 0);
            sanitizeAlarm[f] = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
            sanitizeAlarm[f].setRepeating(AlarmManager.RTC_WAKEUP, calendars[f].getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

            sanitizeIntents.add(pi);
        }
    }

    private void cancelSanitizeAlarm(){
        if(sanitizeIntents.size()>0){
            for(int i=0; i<sanitizeIntents.size(); i++){
                sanitizeAlarm[i].cancel(sanitizeIntents.get(i));
            }
            sanitizeIntents.clear();
        }
        Log.d(TAG, "----- Canceled ---- ");
    }
}
