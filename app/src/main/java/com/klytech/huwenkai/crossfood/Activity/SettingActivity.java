package com.klytech.huwenkai.crossfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klytech.huwenkai.crossfood.Application.ActivityManager;
import com.klytech.huwenkai.crossfood.Application.MyApplication;
import com.klytech.huwenkai.crossfood.R;
import com.klytech.huwenkai.crossfood.Service.MessageServier;
import com.klytech.huwenkai.crossfood.Utils.ConstantValue;
import com.klytech.huwenkai.crossfood.Utils.LogUtils;
import com.klytech.huwenkai.crossfood.Utils.SpUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @Bind(R.id.cb_mesg)
    CheckBox cbMesg;
    @Bind(R.id.cb_Vol)
    CheckBox cbVol;
    @Bind(R.id.tv_vol_note)
    TextView tvVolNote;
    @Bind(R.id.cb_vib)
    CheckBox cbVib;
    @Bind(R.id.rl_mesg)
    RelativeLayout rlmesg;
    @Bind(R.id.rlvol)
    LinearLayout rlvol;
    @Bind(R.id.rlvib)
    LinearLayout rlvib;
    private Toolbar setting_toolbar;
    private Intent it;
    private int userId;
    private boolean volIsOpen;
    private boolean vibIsOPen;
    private LinearLayout llLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initUi() {

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


    @Override
    public void initData() {

    }

    @OnClick(R.id.cb_vib)
    public void onClick() {
    }

    @OnClick({R.id.cb_mesg, R.id.cb_Vol, R.id.cb_vib,R.id.rl_mesg,R.id.rlvib,R.id.rlvol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mesg:
                rlmesgonclick();
                break;
            case R.id.cb_mesg:
                mesgonclick();
                break;
            case R.id.cb_Vol:
                VolOnClick();
                break;
            case R.id.rlvol:
                rlVolOnClick();
                break;
            case R.id.cb_vib:
                VibOnClick();
                break;
            case R.id.rlvib:
                rlVibOnClick();
                break;

        }
    }

    private void rlVibOnClick() {
        if(cbMesg.isChecked()){
        boolean checked = cbVib.isChecked();
        cbVib.setChecked(!checked);
        VibOnClick();}
    }

    private void rlVolOnClick() {
        if(cbMesg.isChecked()){
        boolean checked = cbVol.isChecked();
        cbVol.setChecked(!checked);
        VolOnClick();}
    }

    private void rlmesgonclick() {
        boolean checked = cbMesg.isChecked();
        cbMesg.setChecked(!checked);
        mesgonclick();
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

    private void stopMessageServier() {
        if(it!=null){

            stopService(it);
        }else {
            it = new Intent(this, MessageServier.class);
            stopService(it);
        }
    }

}
