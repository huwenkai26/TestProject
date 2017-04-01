package com.klytech.huwenkai.crossfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.klytech.huwenkai.crossfood.Bean.User;
import com.klytech.huwenkai.crossfood.R;
import com.klytech.huwenkai.crossfood.Utils.ConstantValue;
import com.klytech.huwenkai.crossfood.Utils.LogUtils;
import com.klytech.huwenkai.crossfood.Utils.MD5;
import com.klytech.huwenkai.crossfood.Utils.SpUtil;
import com.klytech.huwenkai.crossfood.Utils.ToastUtil;
import com.klytech.huwenkai.crossfood.Views.CleanEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import static android.view.View.OnClickListener;

/**
 * Created by JimCharles on 2016/11/27.
 */

public class LoginActivity extends BaseActivity implements OnClickListener {



    // 界面控件
    private CleanEditText accountEdit;
    private CleanEditText passwordEdit;
    private Button loginButton;



    private CheckBox autoLoginCb;
    private TextView tvHelp;
    private OkHttpClient okHttpClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
    }

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initUi() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);

        autoLoginCb = (CheckBox) findViewById(R.id.cb_autologin);
        tvHelp = (TextView) findViewById(R.id.tv_help);
        tvHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HelpActivity.class));
            }
        });
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
        accountEdit = (CleanEditText) this.findViewById(R.id.et_email_phone);
        accountEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        accountEdit.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());
        passwordEdit = (CleanEditText) this.findViewById(R.id.et_password);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordEdit.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    clickLogin();
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {

    }

    private void initIntent() {
        //                .addInterceptor(new LoggerInterceptor("TAG"))
//其他配置
//        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
                  okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(7000L, TimeUnit.MILLISECONDS)
                .readTimeout(7000L, TimeUnit.MILLISECONDS)

                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }



    private void clickLogin() {

        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();


        if (checkInput(account, password)) {
            Login(account, password);

        }
    }

    public void Login(String account, String passWord1) {
        String passWord = MD5.shaEncrypt(passWord1);
        accountEdit.setEnabled(false);
        passwordEdit.setEnabled(false);
        mDialog.setMessage("正在登录...");
        mDialog.show();

        Log.e("sha1", passWord);
        OkHttpUtils
                .post()
                .url(ConstantValue.URL + ConstantValue.LoginURL)
                .addParams("account", account)
                .addParams("password", passWord)
                .build()
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 ToastUtil.show(LoginActivity.this,
                                         "网络错误");

                                 accountEdit.setEnabled(true);
                                 passwordEdit.setEnabled(true);

                                 if (mDialog != null && mDialog.isShowing()) {
                                     mDialog.dismiss();
                                 }
                             }

                             @Override
                             public void onResponse(String response, int id) {

                                 accountEdit.setEnabled(true);
                                 passwordEdit.setEnabled(true);
                                 if (mDialog != null && mDialog.isShowing()) {

                                     mDialog.dismiss();
                                 }
                                 if (response.contains("code\":200")) {
                                     User user = new Gson().fromJson(response, User.class);

                                     ToastUtil.show(LoginActivity.this,
                                             "登录成功");
                                     if(autoLoginCb.isChecked()){
                                         LogUtils.e("token",user.data.token);

                                         SpUtil.putBoolean(getApplicationContext(),"ischeck",true);
                                     }else {
                                         SpUtil.putBoolean(getApplicationContext(),"ischeck",false);
                                     }
                                     SpUtil.putString(getApplicationContext(),ConstantValue.TOKEN,user.data.token);
                                     Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                                     SpUtil.putInt(getApplicationContext(),ConstantValue.ID,Integer.parseInt(user.data.id));
                                     startActivity(it);
                                     LoginActivity.this.finish();

                                 }else {
                                     ToastUtil.show(LoginActivity.this,
                                             "密码错误");
                                 }
                             }
                         }
                );

    }

    /**
     * 检查输入
     *
     * @param account
     * @param password
     * @return
     */
    public boolean checkInput(String account, String password) {
        // 账号为空时提示
        if (account == null || account.trim().equals("")) {

            ToastUtil.show(getApplication(),"账号不能为空");
        } else {
            if (password == null || password.trim().equals("")) {

                ToastUtil.show(getApplication(),"密码不能为空");
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:
                clickLogin();
                break;

            default:
                break;
        }
    }

}
