package com.example.cherninbagrutproj.activities;



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cherninbagrutproj.R;

public class CreateSentenceActivity extends AppCompatActivity implements View.OnClickListener {
EditText etSentence, etNewWord, etTranslate;
Button btnOk,btnCancel,btnDelete;


    int position;                           //the index of the sentence if it is update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sentence);
        etSentence = findViewById(R.id.etSentence);
        etNewWord = findViewById(R.id.etNewWord);
        etTranslate = findViewById(R.id.etTranslate);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        btnOk.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        Intent intent = getIntent();
        if ( intent.getStringExtra("key_sentence") != null)
        {
           // update = true;
            position = intent.getIntExtra("key_position",0);
            etSentence.setText(intent.getStringExtra("key_sentence"));
            etNewWord.setText(intent.getStringExtra("key_word"));
            etTranslate.setText(intent.getStringExtra("key_translate"));
        }
        else
            position = -1;

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    @Override
    public void onClick(View view) {
        if (btnDelete == view ){
            if (position != -1) {
                Intent intent = new Intent(CreateSentenceActivity.this, SentenceActivity.class);
                intent.putExtra("key_position",position);
                intent.putExtra("key_delete", "a");   //we send any value and then we will check if key_delete is not delete
                setResult(RESULT_OK, intent);
                finish();
            }
            else{                   //if the sentence wasnt still created
                setResult(RESULT_CANCELED,null);
                finish();
            }
        }
        if (btnCancel == view){
            setResult(RESULT_CANCELED,null);
            finish();
        }
        if (btnOk == view){
            if (etSentence.getText().toString().length() > 0 && etNewWord.getText().toString().length()>0 &&
                    etSentence.getText().toString().contains(etNewWord.getText().toString()) &&
                    !etSentence.getText().toString().contains("#") && !etSentence.getText().toString().contains("&&") && !etSentence.getText().toString().contains("\n") &&
                    !etNewWord.getText().toString().contains("#") && !etNewWord.getText().toString().contains("&&") && !etNewWord.getText().toString().contains("\n")&&
                    !etTranslate.getText().toString().contains("#") && !etTranslate.getText().toString().contains("&&") && !etTranslate.getText().toString().contains("\n")) {
                Intent intent = new Intent(CreateSentenceActivity.this, SentenceActivity.class);
                intent.putExtra("key_sentence", etSentence.getText().toString());
                intent.putExtra("key_word",etNewWord.getText().toString());
                intent.putExtra("key_translate",etTranslate.getText().toString());
                intent.putExtra("key_position",position);
                if (etTranslate.getText().toString().length()>0)
                    intent.putExtra("key_translate", etTranslate.getText().toString());
                else
                    intent.putExtra("key_translate", "");           //if there is no translate, i will put empty string, and in the other side of the intent i will set translate as null
                setResult(RESULT_OK, intent);
                finish();

            }
            else
                Toast.makeText(getApplicationContext(), "You must input the word and the sentence, the sentence must contain the word and not contain the symbols # or &&", Toast.LENGTH_LONG).show();
        }
    }


}