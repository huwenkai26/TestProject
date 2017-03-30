package com.klytech.huwenkai.crossfood.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.klytech.huwenkai.crossfood.R;
import com.klytech.huwenkai.crossfood.Service.MessageServier;
import com.klytech.huwenkai.crossfood.Utils.ConstantValue;
import com.klytech.huwenkai.crossfood.Utils.LogUtils;
import com.klytech.huwenkai.crossfood.Utils.SpUtil;
import com.klytech.huwenkai.crossfood.Utils.ThreadUtils;


public class SplashActivity extends BaseActivity {

    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

    @Override
    public int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initUi() {

    }

    @Override
    public void initData() {

    }

    private void strartMessageServier() {
        it = new Intent(this, MessageServier.class);

        this.startService(it);

    }





}
