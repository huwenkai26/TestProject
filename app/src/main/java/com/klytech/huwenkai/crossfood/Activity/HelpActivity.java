package com.klytech.huwenkai.crossfood.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.klytech.huwenkai.crossfood.R;


public class HelpActivity extends BaseActivity {


    private Toolbar helpToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_help;
    }

    @Override
    public void initUi() {
        helpToolbar = (Toolbar) findViewById(R.id.help_toolbar);
        helpToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpActivity.this.finish();
            }
        });

    }

    @Override
    public void initData() {

    }


}
