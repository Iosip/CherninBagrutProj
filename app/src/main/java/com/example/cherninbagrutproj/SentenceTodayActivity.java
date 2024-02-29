package com.example.cherninbagrutproj;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SentenceTodayActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lv;
    TodayAdapter todayAdapter;
    SentenceManager sentenceManager;
    ArrayList<Sentence> sentences;
    TextView todayTv, allGroupsTv;
    ArrayList<Coordinates> coordinates;

    BroadcastBattery broadcastBattery;          //battery

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
        arrBig.addAll(sentenceManager.loadSentence());
        sentences = new ArrayList<>();

       // arrBig.get(0).getList().get(0).setComplete(1);

        for (SentenceGroup sg: arrBig){
            sentences.addAll(sg.getToday());
        }
        for (int i = 0 ; i < arrBig.size(); i++){
            ArrayList<Integer> ind = arrBig.get(i).getTodayIndexes();
            for (int m = 0; m < ind.size(); m++){
                coordinates.add(new Coordinates(i, ind.get(m)));        //all the sentences which is shown in this place will have a coordinates in this list which will be saved in the same index in "coordinates"  as their index in  "sentences"
            }
        }


        todayAdapter =new TodayAdapter(this,0,0,sentences,coordinates);

        lv=(ListView)findViewById(R.id.listview);
        lv.setAdapter(todayAdapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        broadcastBattery = new BroadcastBattery();
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

    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastBattery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastBattery);
    }


}