package com.example.cherninbagrutproj.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cherninbagrutproj.Coordinates;
import com.example.cherninbagrutproj.R;
import com.example.cherninbagrutproj.Sentence;
import com.example.cherninbagrutproj.SentenceGroup;
import com.example.cherninbagrutproj.SentenceManager;
import com.example.cherninbagrutproj.TodayAdapter;
import com.example.cherninbagrutproj.notificatio3.ReminderManager;

import java.util.ArrayList;

public class SentenceTodayActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lv;
    TodayAdapter todayAdapter;
    SentenceManager sentenceManager;
    ArrayList<Sentence> sentences;
    TextView todayTv, allGroupsTv;
    ArrayList<Coordinates> coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_today);

        todayTv = findViewById(R.id.todayTv);
        allGroupsTv = findViewById(R.id.allGroupsTv);
        todayTv.setOnClickListener(this);
        allGroupsTv.setOnClickListener(this);

        coordinates = new ArrayList<>();
        ArrayList<SentenceGroup> arrBig = new ArrayList<>();
        sentenceManager = new SentenceManager(this);
        arrBig.addAll(sentenceManager.loadSentence(getApplicationContext()));
        sentences = new ArrayList<>();

        for (SentenceGroup sg: arrBig){
            sentences.addAll(sg.getToday());
        }
        for (int i = 0 ; i < arrBig.size(); i++){
            ArrayList<Integer> ind = arrBig.get(i).getTodayIndexes();
            for (int m = 0; m < ind.size(); m++){
                coordinates.add(new Coordinates(i, ind.get(m)));        //all the sentences which is shown in this place will have a coordinates in this list which will be saved in the same index in "coordinates"  as their index in  "sentences"
            }
        }

        //ask permision send notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permmision is not allowed", Toast.LENGTH_SHORT).show();
              //  ActivityCompat.requestPermissions(SentenceTodayActivity.this,
              //          new String[]{Manifest.permission.POST_NOTIFICATIONS}, 99);
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 99);
            }
        }

        ReminderManager reminderManager = new ReminderManager(this);
        reminderManager.createDailyNotificationChannel();
        reminderManager.createLaterNotificationChannel();
        reminderManager.setPermanentReminder();
//        createNotificationChannel();
//        Intent intent = new Intent(SentenceTodayActivity.this, ReminderBroadcast.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(SentenceTodayActivity.this,
//                0 , intent, PendingIntent.FLAG_IMMUTABLE);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
//        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
//        calendar.set(Calendar.SECOND, 0);
//
//         //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        Toast.makeText(this, calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
//                 + " - " + ((calendar.getTimeInMillis() - System.currentTimeMillis() ) / 1000), Toast.LENGTH_LONG).show();
//
//        //long time = calendar.getTimeInMillis();
//      //  alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//      //          AlarmManager.INTERVAL_DAY, pendingIntent);
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);

        todayAdapter =new TodayAdapter(this,0,0,sentences,coordinates);

        lv=(ListView)findViewById(R.id.listview);
        lv.setAdapter(todayAdapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onClick(View view) {
        if (view == todayTv){
//            Intent intent = new Intent(SentenceTodayActivity.this, SentenceGroupsListActivity.class);
//            startActivity(intent);
        }
        if (view == allGroupsTv){
            Intent intent = new Intent(SentenceTodayActivity.this, SentenceGroupsListActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LingvoReminderChannel";
            String description = "Channel for lingvo reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLingvo", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}