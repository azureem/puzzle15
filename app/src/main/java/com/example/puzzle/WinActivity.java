package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class WinActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        int value = getIntent().getIntExtra("moveKey",-1);
        long elapsedMillis = getIntent().getLongExtra("time", 0);
        @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(elapsedMillis),
                TimeUnit.MILLISECONDS.toMinutes(elapsedMillis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsedMillis)),
                TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedMillis)));

        TextView tv = findViewById(R.id.countMove);
        tv.setText(String.valueOf(value));
        TextView timeView = findViewById(R.id.total_time);
        timeView.setText(formattedTime);

        findViewById(R.id.go_menu).setOnClickListener(v->{
            finish();
        });
    }
}