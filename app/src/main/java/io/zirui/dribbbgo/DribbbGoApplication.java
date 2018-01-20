package io.zirui.dribbbgo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class DribbbGoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
