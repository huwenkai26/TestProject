package com.klytech.huwenkai.crossfood.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.klytech.huwenkai.crossfood.Application.ActivityManager;
import com.klytech.huwenkai.crossfood.Application.MyApplication;
import com.klytech.huwenkai.crossfood.Views.ProgressDialog;

import butterknife.ButterKnife;

/**
 * hepeng Created on 2016/10/8.
 * 类描述： activity的超类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected ProgressDialog mDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) getApplication();
        application.getActivityManager().pushActivity(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 屏幕竖屏
        setContentView(initContentView());
        ButterKnife.bind(this);
        initActionBar();
        if (mDialog == null) {
//            View progressBar = LayoutInflater.from(this).inflate(R.layout.progress_bar,null);
            mDialog = ProgressDialog.createDialog(this);
//            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            mDialog.setContentView(progressBar);
//            mDialog.setTitleText("加载中...");
            mDialog.setCancelable(true);
        }
       initUi();
        initData();
    }

    protected  void initActionBar(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置view
     */
    public abstract int initContentView();


    /**
     * init UI && Listener
     */
    public abstract void initUi();

    /**
     * init Data
     */
    public abstract void initData();



    /**
     * 跳转一个界面不传递数据
     *
     * @param clazz 要启动的Activity
     */
    protected void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.popActivity(this);
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        super.onDestroy();
    }




}