package com.bitsgroove.corona.extra;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bitsgroove.corona.MainActivity;
import com.bitsgroove.corona.R;

import static com.bitsgroove.corona.extra.NotificationAPP.CHANNEL_REMINDER;

public class AlarmReciever extends BroadcastReceiver {

    private static final String TAG = "AlarmReciever - ";
    String message = "", msg = "";
    int id = 0;
    private NotificationManager notificationManager;
    public static final String NOTI_TITLE = "Reminder";
    @SuppressLint("ServiceCast")
    @Override
    public void onReceive(Context context, Intent intent) {

        id = intent.getIntExtra("NotiID", 0);
        message = intent.getStringExtra("NotiMsg");
        msg = intent.getStringExtra("msg");

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d(TAG, "--------- NOTIFICATION --------- " + msg +" ---- " + message);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_REMINDER)
                .setSmallIcon(R.drawable.ic_enable)
                .setContentTitle(NOTI_TITLE)
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(id, notification);
    }
}
