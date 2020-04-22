package com.bitsgroove.corona.extra;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationAPP extends Application {

    public static final String CHANNEL_REMINDER = "Reminder";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel reminder_channel = new NotificationChannel(
                    CHANNEL_REMINDER,
                    "Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            reminder_channel.setDescription("This is Reminder");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(reminder_channel);
        }
    }
}
