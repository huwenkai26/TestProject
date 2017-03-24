package com.example.huwenkai.testproject.Application;

import android.app.Application;

public class MyApplication extends Application {
    private ActivityManager activityManager = null; // activity管理类
    public void onCreate() {
        activityManager = ActivityManager.getInstance(); // 获得实例
        super.onCreate();
    }
    public ActivityManager getActivityManager() {
        return activityManager;
    }

}


