package com.example.cherninbagrutproj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SentenceGroupsListActivity extends AppCompatActivity implements View.OnClickListener {
ListView lv;
ArrayList<SentenceGroup> arrBig;
SntgAdapter sntgAdapter;
SentenceManager sentenceManager;
Button btnPLusSenGroup, btnAbout;
    public static final int UPDATE_CODE = 1;
    public static final int ADD_CODE = 0;
    TextView todayTv, allGroupsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_groups_list);


        arrBig = new ArrayList<>();

        sntgAdapter =new SntgAdapter(this,0,0,arrBig);

        lv=(ListView)findViewById(R.id.listview);
        lv.setAdapter(sntgAdapter);

        btnPLusSenGroup = findViewById(R.id.btnPlusSenGroup);
        btnPLusSenGroup.setOnClickListener(this);

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);

        todayTv = findViewById(R.id.todayTv);
        allGroupsTv = findViewById(R.id.allGroupsTv);
        todayTv.setOnClickListener(this);
        allGroupsTv.setOnClickListener(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {                   //go inside the sntg
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SentenceGroupsListActivity.this, SentenceActivity.class);
                     intent.putExtra("key_group_id", position);
                startActivity(intent);
            }
        });

        //sntgList.add(sg1);sntgList.add(sg2);
        sentenceManager = new SentenceManager(this);
        arrBig.addAll(sentenceManager.loadSentence());


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }

    @Override
    public void onClick(View v) {
        if (v == btnPLusSenGroup) {
            Intent intent = new Intent(SentenceGroupsListActivity.this, CreateSentenceGroupActivity.class);
            startActivityForResult(intent, ADD_CODE);
        }
        if (v == todayTv){
            Intent intent = new Intent(SentenceGroupsListActivity.this, SentenceTodayActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        }
        if (v == allGroupsTv){
           // Intent intent = new Intent(SentenceGroupsListActivity.this, SentenceTodayActivity.class);
             //startActivity(intent);
        }
        if (v == btnAbout){
            Intent intent = new Intent(SentenceGroupsListActivity.this,AboutActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK) {
            if (requestCode==UPDATE_CODE) {
                int pos = data.getIntExtra("key_position", 0);
                arrBig.get(pos).setName(data.getStringExtra("key_group_name"));

            } else if (requestCode == ADD_CODE) {
                arrBig.add(0, new SentenceGroup(data.getStringExtra("key_group_name"),0));
            }

            sntgAdapter.notifyDataSetChanged();        //sinchronisatipn
            sentenceManager.saveSentence(arrBig);


        }
    }


}