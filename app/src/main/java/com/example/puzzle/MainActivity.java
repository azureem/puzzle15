package com.example.puzzle;

import static java.util.Collections.shuffle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private MySharedPreference myShar;

    Point currentPoint;
    int ppp = 0;
    private long timePeriod;

    private long pausedTime;
    private int music1=0;
    private long totalElapsedTime;

    AppCompatButton pauseBtn;
    AppCompatButton onBtn;
    boolean music = true;
    Button currentButton;
    private TextView tv;
    List newList = new ArrayList<>();

    private MediaPlayer mediaPlayer;
    private AppCompatImageView cloud1;
    private AppCompatImageView cloud2;
    private Button[][] myButtons = new Button[4][4];
    private List<String> values = new ArrayList<>();
    int emptyX = 3;
    int musicState = 0;
    int emptyY = 3;
    private boolean chronometerRunning = true;
    private boolean enabling = false;
    private Chronometer chronometer;
    private int count;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();

        loadData();

        sharedPref = this.getSharedPreferences("SHARED", Context.MODE_PRIVATE);
        chronometer = findViewById(R.id.chron_id);
        myShar = MySharedPreference.getInstance();

        //from here
        long elapsedTime = sharedPref.getLong("Chrono", 0);
        if (elapsedTime != 0) {
            chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        chronometer.start();

        findViewById(R.id.refresh).setOnClickListener(this::refresh);
        pausedTime = 0;




        //o'yin vaqtida pause_game buttonini click qilish
        findViewById(R.id.pause_game).setOnClickListener(v -> {
            onClickPause(v);

        });
//        musicState=sharedPref.getInt("ASHULA",-1);
//        if (musicState==0){
//            onBtn.setVisibility(View.VISIBLE);
//            onBtn.setVisibility(View.INVISIBLE);
//            mediaPlayer.start();
//
//        }else {
//            onBtn.setVisibility(View.INVISIBLE);
//            onBtn.setVisibility(View.VISIBLE);
//            mediaPlayer.pause();
//        }
        findViewById(R.id.continues).setOnClickListener(v -> {
            onClickContinue(v);
        });

        //o'yin vaqtida exit bosilsa
        findViewById(R.id.exit_game).setOnClickListener(v -> {
            onClickExit(v);
        });

        //no bosilsa
        findViewById(R.id.no).setOnClickListener(v -> {
            onClickNo(v);

        });

        // yes bosilsa
        findViewById(R.id.yes).setOnClickListener(v -> {
            findViewById(R.id.second_world).setVisibility(View.GONE);
            findViewById(R.id.block_exit).setVisibility(View.GONE);
//            myShar.removeClickedState();
//            enabling = false;
//            findViewById(R.id.exit_game).setBackgroundResource(R.drawable.small_buttons);
//            findViewById(R.id.pause_game).setBackgroundResource(R.drawable.small_buttons);
            myShar.removeClickedState();
            finish();

        });


        // media works
        mediaPlayer = MediaPlayer.create(this, R.raw.sosos);
        mediaPlayer.start();

        //MUSIC
        onBtn = findViewById(R.id.onBtn);
        pauseBtn = findViewById(R.id.pauseBtn);


        //musici off qilmoqchi bolsak
        onBtn.setOnClickListener(v -> {
            onBtn.setVisibility(View.INVISIBLE);
            pauseBtn.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
            musicState = 1;
            music1=1;
            //music = false;
        });
        //musicni on qilmoqchi bolsak
        pauseBtn.setOnClickListener(v -> {
            pauseBtn.setVisibility(View.INVISIBLE);
            onBtn.setVisibility(View.VISIBLE);
            mediaPlayer.start();
            musicState = 0;
            music1=0;
           // music = true;
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        chronometer.start();
        chronometer.setBase(SystemClock.elapsedRealtime());

    }

    @Override
    protected void onResume() {

//       if (enabling){
//           findViewById(R.id.pause_game).setBackgroundResource(R.drawable.enable);
//           findViewById(R.id.exit_game).setBackgroundResource(R.drawable.enable);
//       }else {
//           findViewById(R.id.pause_game).setBackgroundResource(R.drawable.small_buttons);
//           findViewById(R.id.exit_game).setBackgroundResource(R.drawable.small_buttons);
//       }
//       findViewById(R.id.pause_game).setBackgroundResource(myShar.getBackgroundResource());
//        findViewById(R.id.exit_game).setBackgroundResource(myShar.getBackgroundResource());
//        if (music) {
//            mediaPlayer.start();
//        } else {
//            mediaPlayer.pause();
//        }

        // myShar.removeMusic();
        String clickedState = myShar.getClickedState();
        if (clickedState.equals("pause")) {
            Log.d("MMM", "pause holattttt ");
            findViewById(R.id.puzzle_board).setVisibility(View.GONE);
            findViewById(R.id.pause_sun).setVisibility(View.VISIBLE);
            findViewById(R.id.second_world).setVisibility(View.VISIBLE);
            findViewById(R.id.block).setVisibility(View.VISIBLE);
            findViewById(R.id.board_cloud_img).setVisibility(View.GONE);
            chronometer.stop();
        } else if (clickedState.equals("exit")) {
            Log.d("MMM", "exit holat ");

            findViewById(R.id.puzzle_board).setVisibility(View.GONE);
            findViewById(R.id.board_cloud_img).setVisibility(View.GONE);
            findViewById(R.id.exit_sun).setVisibility(View.VISIBLE);
            findViewById(R.id.second_world).setVisibility(View.VISIBLE);
            findViewById(R.id.block_exit).setVisibility(View.VISIBLE);
            chronometer.stop();
        } else {
            Log.d("MMM", "clean holattttt ");
            findViewById(R.id.puzzle_board).setVisibility(View.VISIBLE);
            findViewById(R.id.pause_sun).setVisibility(View.GONE);
            findViewById(R.id.board_cloud_img).setVisibility(View.VISIBLE);
            chronometer.stop();
        }


        TextView countContainer = findViewById(R.id.countContainer);
        countContainer.setText(String.valueOf(sharedPref.getInt("Moving", 0)));
        count = sharedPref.getInt("Moving", 0);

        tv.setText("moves: " + count);

        mediaPlayer.pause();
        chronometer.setBase(SystemClock.elapsedRealtime() - sharedPref.getLong("Chrono", 0));
        chronometer.start();
        chronometer.setBase(sharedPref.getLong("Chrono", 0) + SystemClock.elapsedRealtime());
        chronometer.start();


        matrixPrefResume();

        super.onResume();
        int res = myShar.getMusicState();
         music1 = sharedPref.getInt("music1", -1);
        if (music1 == 1) {
            onBtn.setVisibility(View.INVISIBLE);
            pauseBtn.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
            Log.d("OOO", "OFF ");

        } else {
            Log.d("OOO", String.valueOf(res));
            onBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.INVISIBLE);
            Log.d("OOO", "ONN ");
            mediaPlayer.start();
        }
        mediaPlayer.seekTo(10_000);
//        if (music) {
//            mediaPlayer.start();
//        } else {
//            mediaPlayer.pause();
//        }
        mediaPlayer.setLooping(true);
    }


    @Override
    protected void onPause() {
        mediaPlayer.pause();
        myShar.musicState(musicState);
        sharedPref.edit().putInt("music1", music1).apply();


        matrixPrefPause();
        sharedPref.edit().putInt("Moving", count).apply();
        sharedPref.edit().putLong("Chrono", chronometer.getBase() - SystemClock.elapsedRealtime()).apply();
        chronometer.stop();
        super.onPause();
    }

    private boolean isScreenInPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.getCurrentPosition();
    }


    private void initViews() {
        RelativeLayout containerButtons = findViewById(R.id.buttonContainer);
        tv = findViewById(R.id.countContainer);
        for (int i = 0; i < containerButtons.getChildCount(); i++) {
            int currentX = i / 4;
            int currentY = i % 4;
            currentButton = (Button) containerButtons.getChildAt(i);
            myButtons[currentX][currentY] = currentButton;
            currentButton.setOnClickListener(this::onClick);
            currentButton.setTag(new Point(currentX, currentY));
        }
    }

    private void onClick(View view) {

        Button clickedButton = (Button) view;
        Point currentPoint = (Point) clickedButton.getTag();

        boolean canMove = (Math.abs(currentPoint.getX() - emptyX) == 1 && Math.abs(currentPoint.getY() - emptyY) == 0)
                || (Math.abs(currentPoint.getX() - emptyX) == 0 && Math.abs(currentPoint.getY() - emptyY) == 1);

//            MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.click);
//            mediaPlayer2.start();

        if (canMove) {
            myButtons[emptyX][emptyY].setText(clickedButton.getText());
            myButtons[emptyX][emptyY].setBackground(ContextCompat.getDrawable(this, R.drawable.button_frame));
            clickedButton.setText("");
            clickedButton.setBackground(ContextCompat.getDrawable(this, R.drawable.empty_button_color));

            emptyX = currentPoint.getX();
            emptyY = currentPoint.getY();

            count++;
            if (emptyX == 3 && emptyY == 3) {
                checkWin();
            }
        }
        tv.setText("moves:" +count);

    }

    private void checkWin() {
        RelativeLayout containerButtons = findViewById(R.id.buttonContainer);
        for (int i = 1; i < 16; i++) {
            Button currentButton = (Button) containerButtons.getChildAt(i - 1);
            if (!currentButton.getText().equals(String.valueOf(i))) return;
        }


//                int bestSaved = myShar.getFirst();
//                if (bestSaved == 0 || count < bestSaved) {
//                    myShar.saveFirst(count);
//                }


//        int topFirst = myShar.getTopMoves()[0];
//        int topSecond = myShar.getTopMoves()[1];
//        int topThird = myShar.getTopMoves()[2];
//        if (count < topFirst) {
//            myShar.saveTopMoves(count);
//        } else if (count < topSecond && count > topFirst) {
//            myShar.saveTopMoves(count);
//        } else if (count < topThird && count > topSecond) {
//            myShar.saveTopMoves(count);
//        }
        int[] topMoves = myShar.getTopMoves();
        int topFirst = topMoves[0];


        if (topFirst == 0 || count < topFirst) {
            Log.d("SSS", "lallalalalalal");
            myShar.saveTopMoves(count);
        }


        AppCompatTextView tv = findViewById(R.id.countContainer);
        Intent intent3 = new Intent(this, WinActivity.class);
        intent3.putExtra("moveKey", count);
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        intent3.putExtra("time", elapsedMillis);
        startActivity(intent3);
        chronometer.setBase(SystemClock.elapsedRealtime());
        count = 0;
        finish();
    }


    private void initData() {
        for (int i = 1; i < 16; i++) {
            values.add(String.valueOf(i));
            shuffle(values);
        }
        values.add("");

    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        for (int i = 0; i < 16; i++) {
            myButtons[i / 4][i % 4].setText(values.get(i));
            myButtons[i / 4][i % 4].setBackground(ContextCompat.getDrawable(this, R.drawable.button_frame));
        }
        myButtons[emptyX][emptyY].setBackground(ContextCompat.getDrawable(this, R.drawable.empty_button_color));

        tv.setText("moves: " + count);
    }


    private void refresh(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        values.clear();
        for (int i = 1; i < 16; i++) {
            values.add(String.valueOf(i));

        }
        shuffle(values);
        values.add("");
        emptyX = 3;
        emptyY = 3;
        loadData();
        count = 0;

        TextView textView = findViewById(R.id.countContainer);
        textView.setText("moves:  " + count);
    }

    public void onClickPause(View view) {
        // Pause game logic
        chronometer.stop();
//        findViewById(R.id.exit_game).setBackgroundResource(R.drawable.enable);
//        findViewById(R.id.pause_game).setBackgroundResource(R.drawable.enable);
        myShar.saveTheClickedState("pause");
        enabling = true;
        findViewById(R.id.puzzle_board).setVisibility(View.GONE);
        findViewById(R.id.pause_sun).setVisibility(View.VISIBLE);
        findViewById(R.id.board_cloud_img).setVisibility(View.GONE);
        pausedTime = SystemClock.elapsedRealtime();
        findViewById(R.id.second_world).setVisibility(View.VISIBLE);
        findViewById(R.id.block).setVisibility(View.VISIBLE);
    }


    public void onClickContinue(View view) {
        // Continue game logic
        findViewById(R.id.puzzle_board).setVisibility(View.VISIBLE);
        findViewById(R.id.pause_sun).setVisibility(View.GONE);
        findViewById(R.id.board_cloud_img).setVisibility(View.VISIBLE);
        myShar.removeClickedState();
        enabling = false;
//        findViewById(R.id.exit_game).setBackgroundResource(R.drawable.small_buttons);
//        findViewById(R.id.pause_game).setBackgroundResource(R.drawable.small_buttons);
        if (pausedTime != 0) {
            long elapsedTime = SystemClock.elapsedRealtime() - pausedTime;
            chronometer.setBase(chronometer.getBase() + elapsedTime);
            pausedTime = 0;
        }
        chronometer.start();
        findViewById(R.id.second_world).setVisibility(View.GONE);
        findViewById(R.id.block).setVisibility(View.GONE);
    }

    public void onClickExit(View view) {
        // Exit game logic
        myShar.saveTheClickedState("exit");
//        findViewById(R.id.exit_game).setBackgroundResource(R.drawable.enable);
//        findViewById(R.id.pause_game).setBackgroundResource(R.drawable.enable);
        findViewById(R.id.puzzle_board).setVisibility(View.GONE);
        findViewById(R.id.board_cloud_img).setVisibility(View.GONE);
        findViewById(R.id.exit_sun).setVisibility(View.VISIBLE);
        chronometer.stop();
        enabling = true;
        pausedTime = SystemClock.elapsedRealtime();
        findViewById(R.id.second_world).setVisibility(View.VISIBLE);
        findViewById(R.id.block_exit).setVisibility(View.VISIBLE);
    }

//    public void onClickNo(View view) {
//        // No button logic
//        findViewById(R.id.puzzle_board).setVisibility(View.VISIBLE);
//        findViewById(R.id.board_cloud_img).setVisibility(View.VISIBLE);
//        findViewById(R.id.exit_sun).setVisibility(View.GONE);
//        //  enabling = false;
//        findViewById(R.id.exit_game).setBackgroundResource(R.drawable.small_buttons);
//        findViewById(R.id.pause_game).setBackgroundResource(R.drawable.small_buttons);
//        myShar.removeClickedState();
//
//        if (pausedTime != 0) {
//            long elapsedTime = SystemClock.elapsedRealtime() - pausedTime;
//            chronometer.setBase(chronometer.getBase() + elapsedTime);
//            pausedTime = 0;
//        }
//        chronometer.start();
//        findViewById(R.id.second_world).setVisibility(View.GONE);
//        findViewById(R.id.block_exit).setVisibility(View.GONE);
//    }

    public void onClickNo(View view){
        findViewById(R.id.second_world).setVisibility(View.GONE);
        findViewById(R.id.block_exit).setVisibility(View.GONE);
//            myShar.removeClickedState();
//            enabling = false;
//            findViewById(R.id.exit_game).setBackgroundResource(R.drawable.small_buttons);
//            findViewById(R.id.pause_game).setBackgroundResource(R.drawable.small_buttons);
        findViewById(R.id.refresh).callOnClick();
        myShar.removeClickedState();
        finish();
    }

//

    private void matrixPrefResume(){
        String[] arr = sharedPref.getString("Matrix", "").split("#");


        if (arr.length == 16) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("0")) {
                    myButtons[i / 4][i % 4].setText("");
                    myButtons[i / 4][i % 4].setBackgroundResource(R.drawable.empty_button_color);
                    emptyX = i / 4;
                    emptyY = i % 4;
                } else {
                    myButtons[i / 4][i % 4].setText(arr[i]);
                    myButtons[i / 4][i % 4].setBackgroundResource(R.drawable.button_frame);
                }
            }
        }

    }

    private void matrixPrefPause(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            if (myButtons[i / 4][i % 4].getText().equals("")) {
                sb.append("0").append("#");
            } else {
                sb.append(myButtons[i / 4][i % 4].getText()).append("#");
            }
        }
        // myShar.saveBackgroundResource(R.drawable.enable);
        sharedPref.edit().putString("Matrix", sb.toString()).apply();
    }

}