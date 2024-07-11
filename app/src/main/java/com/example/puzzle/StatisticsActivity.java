package com.example.puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// StatisticsActivity.java
public class StatisticsActivity extends AppCompatActivity {
    private MySharedPreference myShar;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_screen);
        myShar = MySharedPreference.getInstance();

//       int best = myShar.getFirst();
//        TextView firstRecordTv = findViewById(R.id.firstRecord);
//        firstRecordTv.setText(String.valueOf(best));


//        int first = myShar.getTopMoves()[0];
//        int second = myShar.getTopMoves()[1];
//        int third = myShar.getTopMoves()[2];
//
//
//        TextView oneTv =  findViewById(R.id.firstRecord);
//        TextView twoTv =  findViewById(R.id.secondRecord);
//        TextView threeTv =  findViewById(R.id.thirdRecord);
//
//        oneTv.setText(String.valueOf(first));
//        twoTv.setText(String.valueOf(second));
//        threeTv.setText(String.valueOf(third));


        int[] topMoves = myShar.getTopMoves();

        // Check for empty array (optional)
        if (topMoves.length == 0) {
            // Set default text for no scores
            TextView oneTv = findViewById(R.id.firstRecord);
            oneTv.setText("saqlama");

        } else {
            int first = topMoves[0];
            int second = topMoves[1];
            int third = topMoves[2];

            TextView oneTv = findViewById(R.id.firstRecord);
            Log.d("TTT", "first: " + first);
            TextView twoTv = findViewById(R.id.secondRecord);
            Log.d("TTT", "second: " + second);

            TextView threeTv = findViewById(R.id.thirdRecord);
            Log.d("TTT", "third: " + third);


            oneTv.setText(String.valueOf(first));
            twoTv.setText(String.valueOf(second));
            threeTv.setText(String.valueOf(third));

            findViewById(R.id.back_win2).setOnClickListener(v -> {
                finish();
            });
        }
    }
}
