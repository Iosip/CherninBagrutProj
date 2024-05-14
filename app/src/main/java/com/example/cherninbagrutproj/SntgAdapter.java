package com.example.cherninbagrutproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cherninbagrutproj.activities.CreateSentenceGroupActivity;

import java.util.List;

public class SntgAdapter extends ArrayAdapter<SentenceGroup> {
    Context context;
    List<SentenceGroup> objects;
    public SntgAdapter(Context context, int resource, int textViewResourceId, List<SentenceGroup> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.new_sntg_layout,parent,false);

        TextView sntgTv = (TextView)view.findViewById(R.id.nameTv);

        SentenceGroup temp = objects.get(position);


        sntgTv.setText(temp.getName());

        ImageView sntg_settings = (ImageView)view.findViewById(R.id.ivEdit);
        sntg_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateSentenceGroupActivity.class);
                SentenceGroup lastChosen = objects.get(position);
                intent.putExtra("key_group_name", lastChosen.getName());
                intent.putExtra("key_position",position);


                ((Activity)context).startActivityForResult(intent,1);
            }
        });


        return view;
    }
}


