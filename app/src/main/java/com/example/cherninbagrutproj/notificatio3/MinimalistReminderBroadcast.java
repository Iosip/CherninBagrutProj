package com.example.cherninbagrutproj.notificatio3;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.example.cherninbagrutproj.Constants;

public class MinimalistReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ReminderManager reminderManager = new ReminderManager(context);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Log.d("MinimalistReminderBroadcast", "broadcast was opened");

        if (intent.getAction().equals(Constants.REMIND_HOUR_LATER_ACTION)) {
            Log.d("MinimalistReminderBroadcast", "REMIND_HOUR_LATER_ACTION");
            //reminderManager.createLaterNotificationChannel();

            reminderManager.setLaterReminder();
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Constants.NOTIFICATION_ID);
        } else if (intent.getAction().equals(Constants.OPEN_THE_APP_ACTION)) {
            Log.d("MinimalistReminderBroadcast", "OPEN_THE_APP_ACTION");
            reminderManager.setPermanentReminder();
            reminderManager.showNotification(intent.getStringExtra(Constants.CHANNEL_ID_EXTRA));
        }
    }
}
