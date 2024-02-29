package com.example.cherninbagrutproj;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateSentenceGroupActivity extends AppCompatActivity implements View.OnClickListener {
EditText etGroupName;
Button btnOk,btnCancel;
int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sentence_group);

        etGroupName = findViewById(R.id.etGroupName);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        Intent intent = getIntent();
        if ( intent.getStringExtra("key_group_name") != null)
        {
            position = intent.getIntExtra("key_position",0);
            etGroupName.setText(intent.getStringExtra("key_group_name"));
        }
        else
            position = -1;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onClick(View view) {
        if (btnCancel == view){
            setResult(RESULT_CANCELED,null);
            finish();
        }
        if (btnOk == view){
            if (etGroupName.getText().toString().length() > 0 && !etGroupName.getText().toString().contains("#") && !etGroupName.getText().toString().contains("&&") && !etGroupName.getText().toString().contains("\n")) {
                Intent intent = new Intent(CreateSentenceGroupActivity.this, SentenceGroupsListActivity.class);
                intent.putExtra("key_group_name", etGroupName.getText().toString());
                intent.putExtra("key_position",position);

                setResult(RESULT_OK, intent);
                finish();

            }
            else
                Toast.makeText(getApplicationContext(), "You must input the name of the group and it can't contain symbols # or &&", Toast.LENGTH_LONG).show();
        }
    }
}