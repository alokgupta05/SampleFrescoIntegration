package com.example.fresco.myapplication;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by  on 8/26/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
