package com.example.cherninbagrutproj;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodayAdapter extends ArrayAdapter<Sentence> implements View.OnClickListener {
    Context context;
    List<Sentence> objects;
    List<Coordinates> coordinates;

    private float mScaleVal = 1.0f, mFloorScale = 1.0f;

    //for the intent
    //Sentence lastChosen;
    public TodayAdapter(Context context, int resource, int textViewResourceId, List<Sentence> objects, List<Coordinates> coordinates) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
        this.coordinates = coordinates;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.new_todays_layout,parent,false);

        CheckBox cbDone = (CheckBox)view.findViewById(R.id.cbDone);
        cbDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cbDone.startAnimation(AnimationUtils.loadAnimation(context,R.anim.shrink_checkbox));
                SentenceManager sentenceManager = new SentenceManager(context);
                ArrayList<SentenceGroup> arrBig = new ArrayList<>();
                arrBig.addAll(sentenceManager.loadSentence(context));
                Sentence sentence = SentenceManager.findSentence(arrBig,coordinates.get(position));
                if (cbDone.isChecked()){
                    sentence.changeDay();
                }
                else{
                    sentence.returnDays();  //if the user clicked mistakley cbDone this line returns the days as they was
                    }
                sentenceManager.saveSentence(arrBig, context);
            }

        });
        TextView sentenceTv =  (TextView)view.findViewById(R.id.sentenceTv);
        Sentence temp = objects.get(position);

        String text = temp.beforeWord + temp.word + temp.afterWord;
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        //ss.setSpan(boldSpan, temp.beforeWord.length(),
        //        temp.beforeWord.length()+temp.word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Typeface myTypeface = Typeface.create(ResourcesCompat.getFont(context, R.font.open_sans_bold),
                Typeface.NORMAL);
        ss.setSpan(new TypefaceSpan(myTypeface), temp.beforeWord.length(), temp.beforeWord.length()+temp.word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sentenceTv.setText(ss);


        ImageView sentence_translate =  (ImageView)view.findViewById(R.id.sentence_translate1);
        sentence_translate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (temp.translate.length() > 0)
                    Toast.makeText(context, ""+temp.translate, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {

    }

}

