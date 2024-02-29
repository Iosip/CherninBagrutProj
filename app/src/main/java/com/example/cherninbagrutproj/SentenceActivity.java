package com.example.cherninbagrutproj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SentenceActivity extends AppCompatActivity implements View.OnClickListener {
ListView lv;
Button btnPLusSen;
    ArrayList<SentenceGroup> arrBig;
    int posOfTheGroup;          //the position of the sntg in the arrBig
    SentenceManager sentenceManager;
    public static final int UPDATE_CODE = 1;
    public static final int ADD_CODE = 0;
    public static final int DELETE_CODE = 2;
    ArrayList<Sentence> sentenceList;
    SentenceAdapter sentenceAdapter;
    TextView todayTv, allGroupsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence);

        todayTv = findViewById(R.id.todayTv);
        allGroupsTv = findViewById(R.id.allGroupsTv);
        todayTv.setOnClickListener(this);
        allGroupsTv.setOnClickListener(this);

        arrBig = new ArrayList<>();
        sentenceManager = new SentenceManager(this);
        arrBig.addAll(sentenceManager.loadSentence());
        sentenceList = new ArrayList<>();

        sentenceAdapter =new SentenceAdapter(this,0,0,sentenceList);

        lv=(ListView)findViewById(R.id.listview);
        lv.setAdapter(sentenceAdapter);

        Intent intent = getIntent();
        if (intent.getIntExtra("key_group_id",-1) > -1){
            posOfTheGroup = intent.getIntExtra("key_group_id",-1);
            sentenceList.addAll(arrBig.get(posOfTheGroup).getList());
        }


        btnPLusSen = findViewById(R.id.btnPlusSen);
        btnPLusSen.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    public void onClick(View view) {
        if (view == btnPLusSen) {
            Intent intent = new Intent(SentenceActivity.this, CreateSentenceActivity.class);
            startActivityForResult(intent, ADD_CODE);
        }
        if (view == todayTv){
            Intent intent = new Intent(SentenceActivity.this, SentenceTodayActivity.class);
            startActivity(intent);
        }
        if (view == allGroupsTv){
            Intent intent = new Intent(SentenceActivity.this, SentenceGroupsListActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK) {
            if (requestCode==UPDATE_CODE) {
                if (data.getStringExtra("key_delete") != null) {            //if user wants to delete
                    int pos = data.getIntExtra("key_position", -1);
                    if (pos > -1) {
                        sentenceList.remove(pos);
                        arrBig.get(posOfTheGroup).setList(sentenceList);
                    }
                }else {
                    int pos = data.getIntExtra("key_position", -1);
                    Sentence update = sentenceList.get(pos);
                    update.setAll(data.getStringExtra("key_sentence"), data.getStringExtra("key_word"), data.getStringExtra("key_translate"));
                    sentenceList.set(pos, update);
                    arrBig.get(posOfTheGroup).setList(sentenceList);
                }
            } else if (requestCode == ADD_CODE) {
                String sentence = data.getStringExtra("key_sentence"), word = data.getStringExtra("key_word"), translate = data.getStringExtra("key_translate");
                sentenceList.add(0, new Sentence(sentence,word,translate));
                arrBig.get(posOfTheGroup).setList(sentenceList);

            }
        }
            sentenceAdapter.notifyDataSetChanged();        //sinchronisatipn
            sentenceManager.saveSentence(arrBig);
        }


}