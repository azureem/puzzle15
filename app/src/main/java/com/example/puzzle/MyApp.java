package com.example.puzzle;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySharedPreference.init(this);
    }
}
