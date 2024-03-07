package com.example.cherninbagrutproj.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.cherninbagrutproj.R;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    Animation fadeIn;
    TextView tvAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        fadeIn= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        tvAbout = findViewById(R.id.textViewAbout);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_1,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuExit:
                Intent intent = new Intent(Intent.ACTION_MAIN);             //system.out didnt worked correct so i used this way
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            case R.id.mnuRead:
                tvAbout.startAnimation(fadeIn);
                speak();
                return true;
            case R.id.mnuStop:
                onPause();
                return true;
        }
        return true;
    }

    private void speak() {
        String text = "Lingvo90 was created to help you study new words in foreign languages by the 90 seconds system which was developed by russian specialist Anton Brejestovski. \n" +
                "This is the process of learning a word:\n" +
                "1. Having met a new word, you create a sentence and you write all the sentence where you found the new word in the “sentence” line and the word itself in the “word”. Very Important that the letters in the word have to be exactly like in the sentence. \n" +
                "2. Within 7 days, the application will send you Notification to practice. Your task is to read the sentence in the “today” page 1-2 times. At the same time, you need to understand what the word means. \n" +
                "3. After 7 days, there is a week break, after which the application sends you this notification again for you to read it again. \n" +
                "4. And finally, after another two weeks of rest, you have to read it for the last time.\n" +
                "If you take into account that you spent 10 seconds per reading, then you will spend a total of 10 * 7 + 10 + 10 = 90 seconds learning a new word.\n" +
                "\n" +
                "In “today” page there are all the sentences which you have to read today. When you read a sentence, put a vi in the checkbox so that the program understands that you read the sentence. If you want to see the translation (if you made it) click on the translate button.\n" +
                "In “all groups” page you can create a group. Then Click on the group. Now you are in the group. Here you can see all the sentences including old sentences that you have finished, and here you can create new sentences. \n" +
                "The translation line is optional.\n" +
                "\n" +
                "Created by Daniel Chernin 2022.\n\n\n_______";
        float pitch = 1;
        float speed = (float) 1;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    protected void onPause() {
        if (mTTS != null || mTTS.isSpeaking()){
            //if it is speaking then stop
            mTTS.stop();
        }
        super.onPause();
    }
}