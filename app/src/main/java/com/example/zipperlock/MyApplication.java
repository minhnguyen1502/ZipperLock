package com.example.zipperlock;

import android.app.Application;

public class MyApplication extends Application {
    public boolean lockScreenShow = false;
    public int notificationId = 1989;

    @Override
    public void onCreate() {
        super.onCreate();

    }

}

