package com.example.puzzle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MySharedPreference {

    private SharedPreferences myShar;

    private static MySharedPreference instance;

    public static MySharedPreference getInstance() {
        if (instance == null) {
            instance = new MySharedPreference();
        }
        return instance;
    }

    public static void init(Application application) {
        if (instance == null) {
            instance = new MySharedPreference();
        }
        instance.myShar = application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }




    public  void musicState(int value){
        myShar.edit().putInt("MUSIC_STATE", value).apply();
    }

    public  int getMusicState(){
        return myShar.getInt("MUSIC_STATE", -1);
    }

    public void removeMusic(){
        myShar.edit().remove("MUSIC_STATE").apply();
    }



//    public  void finalMusicSave( boolean value){
//        myShar.edit().putBoolean("ASHULA", value).apply();
//    }
//
//    public boolean getFinalMusic(){
//        return myShar.getBoolean("ASHULA", false);
//    }
//



//    public void saveBackgroundResource(int resourceId) {
//        myShar.edit().putInt("BACKGROUND_RESOURCE_ID", resourceId).apply();
//    }
//
//    // Method to retrieve the saved background resource ID
//    public int getBackgroundResource() {
//        return myShar.getInt("BACKGROUND_RESOURCE_ID", R.drawable.small_buttons); // Default background resource ID
//    }
//    public void removeBackground() {
//         myShar.edit().remove("BACKGROUND_RESOURCE_ID").apply();// Default background resource Id
//    }
    public   void saveFirst(int moves) {
        myShar.edit().putInt("BEST", moves).apply();
    }

    public   void saveTheClickedState( String state){
        myShar.edit().putString("CLICKED_STATE_KEY", state).apply();
    }
    public String getClickedState() {
        return myShar.getString("CLICKED_STATE_KEY", "");
    }

    public int getFirst() {
        return myShar.getInt("BEST", 0);
    }



    public void removeClickedState() {
        myShar.edit().remove("CLICKED_STATE_KEY").apply();
    }
    public void saveTopMoves(int moves) {
        int currentFirst = myShar.getInt("MOVES1", 0);
        int currentSecond = myShar.getInt("MOVES2", 0);
        int currentThird = myShar.getInt("MOVES3", 0);

        if (currentFirst == 0 || moves < currentFirst) {
            myShar.edit()
                    .putInt("MOVES3", currentSecond)
                    .putInt("MOVES2", currentFirst)
                    .putInt("MOVES1", moves)
                    .apply();

        } else if (moves < currentSecond ) {
            myShar.edit()
                    .putInt("MOVES3", currentSecond)
                    .putInt("MOVES2", moves)
                    .apply();
        } else if (moves < currentThird) {
            myShar.edit()
                    .putInt("MOVES3", moves)
                    .apply();
        }
    }




    public int[] getTopMoves() {
        int[] topMoves = new int[3];

        topMoves[0] = myShar.getInt("MOVES1", 0);
        Log.d("SSS", "FIRST: " + topMoves[0]);

        topMoves[1] = myShar.getInt("MOVES2", 0);
        Log.d("SSS", "SECOND: " + topMoves[1]);
        topMoves[2] = myShar.getInt("MOVES3", 0);
        Log.d("SSS", "THIRD: " + topMoves[2]);

        return topMoves;
    }
}
