package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        findViewById(R.id.start).setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        });
        findViewById(R.id.info).setOnClickListener(v->{
            Intent intent1 = new Intent(this, InfoActivity.class);
            startActivity(intent1);

        });

        findViewById(R.id.statistics).setOnClickListener(v->{
            Intent intent2 =  new Intent(this, StatisticsActivity.class);
            startActivity(intent2);

        });

//        findViewById(R.id.exit_game).setOnClickListener(v->{
//            finish();
//        });
    }
}