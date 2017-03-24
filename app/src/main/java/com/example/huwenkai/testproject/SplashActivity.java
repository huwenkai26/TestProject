package com.example.huwenkai.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.huwenkai.testproject.Application.ActivityManager;
import com.example.huwenkai.testproject.Application.MyApplication;
import com.example.huwenkai.testproject.Service.MessageServier;
import com.example.huwenkai.testproject.Utils.ConstantValue;
import com.example.huwenkai.testproject.Utils.LogUtils;
import com.example.huwenkai.testproject.Utils.SpUtil;
import com.example.huwenkai.testproject.Utils.ThreadUtils;


public class SplashActivity extends Activity {

    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.pushActivity(this);
        final long startTime = System.currentTimeMillis();
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                try {

                    //初始化数据
                    //版本更新检测
                    // 2017-03-10 打开服务

                    boolean IsSend = SpUtil.getBoolean(getApplication(), ConstantValue.SENDMESSAGE, false);
                    if (IsSend) {
                        strartMessageServier();
                    }


                    if (SpUtil.getBoolean(getApplicationContext(), ConstantValue.ISAUTOLOGIN, false)) {
                        String token = SpUtil.getString(getApplicationContext(), ConstantValue.TOKEN, "");
                        LogUtils.e("ReadToken", token);
                        // TODO: 2017-03-13 将user的token请求网络连接 通过后登录HOme页面
                        if (token != "") {
                            //// TODO: 2017-03-14 将使用token登录
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                            finish();
                        }else {
                            SpUtil.putBoolean(getApplicationContext(),ConstantValue.ISAUTOLOGIN,false);
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    } else {
                        LogUtils.e("没有token直接登录");
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                    long endTime = System.currentTimeMillis();
                    Thread.sleep(1000 - (endTime - startTime));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void strartMessageServier() {
        it = new Intent(this, MessageServier.class);

        this.startService(it);

    }

    @Override
    protected void onDestroy() {
        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.popActivity(this);
        super.onDestroy();
    }



}
