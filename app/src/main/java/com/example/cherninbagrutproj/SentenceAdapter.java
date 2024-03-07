package com.example.cherninbagrutproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cherninbagrutproj.activities.CreateSentenceActivity;

import java.util.List;

public class SentenceAdapter extends ArrayAdapter<Sentence> {
    Context context;
    List<Sentence> objects;

    //for the intent
    //Sentence lastChosen;
    public SentenceAdapter(Context context, int resource, int textViewResourceId, List<Sentence> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sntg_layout,parent,false);

        TextView sentenceTv =  (TextView)view.findViewById(R.id.nameTv);
        Sentence temp = objects.get(position);

        String text = temp.beforeWord + temp.word + temp.afterWord;
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, temp.beforeWord.length(), temp.beforeWord.length()+temp.word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sentenceTv.setText(ss);

        sentenceTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (temp.translate.length() > 0)
                    Toast.makeText(context, ""+temp.translate, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView editSentence = view.findViewById(R.id.ivEdit);
        editSentence.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateSentenceActivity.class);
                Sentence lastChosen = objects.get(position);
                intent.putExtra("key_sentence", lastChosen.getBeforeWord()+lastChosen.word+lastChosen.getAfterWord());
                intent.putExtra("key_word", lastChosen.getWord());
                intent.putExtra("key_translate",lastChosen.translate);
                intent.putExtra("key_position",position);

                ((Activity)context).startActivityForResult(intent,1);
            }
        });

        return view;
    }
}


