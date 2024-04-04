package com.example.cherninbagrutproj.notificatio3;

import static android.content.Context.ALARM_SERVICE;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cherninbagrutproj.Constants;
import com.example.cherninbagrutproj.R;
import com.example.cherninbagrutproj.Sentence;
import com.example.cherninbagrutproj.SentenceGroup;
import com.example.cherninbagrutproj.SentenceManager;
import com.example.cherninbagrutproj.activities.SentenceTodayActivity;
//import com.example.cherninbagrutproj.notification2.ReminderBroadcast;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderManager {

    private final Context context;
    private SentenceManager sentenceManager;

    public ReminderManager(Context context) {
        this.context = context;
    }

    public void createDailyNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LingvoDailyReminderChannel";
            String description = "Channel for lingvo reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.DAILY_NOTIFICATION_CHANNEL, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Log.d("ReminderManager", "Daily Channel was created");
        }
    }

    public void createLaterNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LingvoLaterReminderChannel";
            String description = "Channel for lingvo remind later button";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.LATER_NOTIFICATION_CHANNEL, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Log.d("ReminderManager", "Later Channel was created");
        }
    }

    public void setPermanentReminder() {
        //if right now is the hour of daily remaind, put alarm to next day
        boolean isNearestDate = !((Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                == Constants.DAILY_NOTIFICATION_HOUR) && (Calendar.getInstance().get(Calendar.MINUTE)
                == Constants.DAILY_NOTIFICATION_MINUTE));

        Intent intent = new Intent(context, MinimalistReminderBroadcast.class);
        intent.setAction(Constants.OPEN_THE_APP_ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.CHANNEL_ID_EXTRA, Constants.DAILY_NOTIFICATION_CHANNEL);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Constants.REQUEST_CODE_PERMANENT , intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Constants.DAILY_NOTIFICATION_HOUR);
        calendar.set(Calendar.MINUTE, Constants.DAILY_NOTIFICATION_MINUTE);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.compareTo(Calendar.getInstance()) <= 0){
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Log.d("ReminderManager", "setPermanentReminder activated to " +
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
                + " - " + ((calendar.getTimeInMillis() - System.currentTimeMillis() ) / 1000));
    }

    public void setLaterReminder() {
        Intent intent = new Intent(context, MinimalistReminderBroadcast.class);
        intent.setAction(Constants.OPEN_THE_APP_ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.CHANNEL_ID_EXTRA, Constants.LATER_NOTIFICATION_CHANNEL);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Constants.REQUEST_CODE_LATER , intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, Constants.SEND_LATER_MINUTES);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("ReminderManager", "setLaterReminder activated to " +
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
                 + " - " + ((calendar.getTimeInMillis() - System.currentTimeMillis() ) / 1000));
    }

    public void showNotification(String ChannelID) {
        ArrayList<SentenceGroup> arrBig = new ArrayList<>();
        sentenceManager = new SentenceManager(context);
        arrBig.addAll(sentenceManager.loadSentence(context));
        ArrayList<Sentence> todaySentences = new ArrayList<>();
        for (SentenceGroup sg : arrBig) {
            todaySentences.addAll(sg.getToday());
        }

        Intent intentToOpenApp = new Intent(context, SentenceTodayActivity.class);
        intentToOpenApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToOpenApp,
                PendingIntent.FLAG_IMMUTABLE);

        Intent remindLaterIntent = new Intent(context, com.example.cherninbagrutproj.notificatio3.MinimalistReminderBroadcast.class);
        remindLaterIntent.setAction(Constants.REMIND_HOUR_LATER_ACTION);
        PendingIntent remindLaterPendingIntent =
                PendingIntent.getBroadcast(context, 0, remindLaterIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ChannelID)
                .setSmallIcon(R.drawable.bw_icon)
                .setContentTitle("Time To Train Words")
                .setContentText("You have " + todaySentences.size() + " sentences today")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_stat_name, "Remind Me 1 Hour Later", remindLaterPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            //todo here permmision request
//                        ActivityCompat.requestPermissions(context,
//                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            return;
        }
        Log.d("ReminderManager","notification can pop, channel: " + ChannelID);
        if (todaySentences.size() > 0)
            notificationManagerCompat.notify(Constants.NOTIFICATION_ID, builder.build());

    }
}