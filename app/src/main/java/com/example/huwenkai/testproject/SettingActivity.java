package com.example.huwenkai.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huwenkai.testproject.Application.ActivityManager;
import com.example.huwenkai.testproject.Application.MyApplication;
import com.example.huwenkai.testproject.Service.MessageServier;
import com.example.huwenkai.testproject.Utils.ConstantValue;
import com.example.huwenkai.testproject.Utils.LogUtils;
import com.example.huwenkai.testproject.Utils.SpUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends Activity {


    @Bind(R.id.cb_mesg)
    CheckBox cbMesg;
    @Bind(R.id.cb_Vol)
    CheckBox cbVol;
    @Bind(R.id.tv_vol_note)
    TextView tvVolNote;
    @Bind(R.id.cb_vib)
    CheckBox cbVib;
    private Toolbar setting_toolbar;
    private Intent it;
    private int userId;
    private boolean volIsOpen;
    private boolean vibIsOPen;
    private LinearLayout llLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.pushActivity(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        userId = intent.getIntExtra(ConstantValue.ID,0);
        LogUtils.e("SettingActivity_USer",userId+"");
        initUI();

    }

    private void initUI() {

        //回显用户之前的设置状态
        boolean IsMessageCheack = SpUtil.getBoolean(getApplicationContext(), ConstantValue.SENDMESSAGE, false);
        cbMesg.setChecked(IsMessageCheack);
        if(IsMessageCheack){

            cbVib.setEnabled(true);
            cbVol.setEnabled(true);
        }
        volIsOpen = SpUtil.getBoolean(getApplication(), ConstantValue.ISOPENVol, false);
        vibIsOPen = SpUtil.getBoolean(getApplication(), ConstantValue.ISOPENVIB, false);
        LogUtils.e( ConstantValue.ISOPENVIB,vibIsOPen+"");
        cbVol.setChecked(volIsOpen);
        cbVib.setChecked(vibIsOPen);

        llLogout = (LinearLayout) findViewById(R.id.llLogout);
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logout();
            }
        });
        setting_toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        setting_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });


    }

    @OnClick(R.id.cb_vib)
    public void onClick() {
    }

    @OnClick({R.id.cb_mesg, R.id.cb_Vol, R.id.cb_vib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_mesg:
                mesgonclick();
                break;
            case R.id.cb_Vol:
                VolOnClick();
                break;
            case R.id.cb_vib:
                VibOnClick();
                break;

        }
    }

    private void VibOnClick() {
        boolean Vibchecked = cbVol.isChecked();
        SpUtil.putBoolean(getApplication(),ConstantValue.ISOPENVIB,Vibchecked);
    }

    private void VolOnClick() {
        boolean Volchecked = cbVol.isChecked();
        SpUtil.putBoolean(getApplication(),ConstantValue.ISOPENVol,Volchecked);

    }

    private void Logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        SpUtil.putString(getApplicationContext(),ConstantValue.TOKEN,"");
        SpUtil.putBoolean(getApplicationContext(),ConstantValue.ISAUTOLOGIN,false);

        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.finishAllActivityExceptOne(LoginActivity.class);

    }



    void mesgonclick() {
        if(cbMesg.isChecked()){
            strartMessageServier();
            cbVib.setEnabled(true);
            cbVol.setEnabled(true);
            SpUtil.putBoolean(getApplicationContext(),ConstantValue.SENDMESSAGE,true);

        }else {
            stopMessageServier();
            cbVib.setEnabled(false);
            cbVol.setEnabled(false);
            SpUtil.putBoolean(getApplicationContext(),ConstantValue.SENDMESSAGE,false);
        }
    }

    private void strartMessageServier() {
        it = new Intent(this, MessageServier.class);

        this.startService(it);

    }

    protected void onDestroy() {
        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.popActivity(this);
        super.onDestroy();
    }
    private void stopMessageServier() {
        if(it!=null){

            stopService(it);
        }
    }

}
