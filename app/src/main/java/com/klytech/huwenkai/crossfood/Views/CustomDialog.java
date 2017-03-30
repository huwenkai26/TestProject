package com.klytech.huwenkai.crossfood.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.klytech.huwenkai.crossfood.Utils.DisplayUtil;
import com.klytech.huwenkai.crossfood.Utils.KeyboardUtils;

/**
 * Created by pc on 2017/2/24.
 */

public class CustomDialog extends Dialog {
    private Activity mContext;
    public CustomDialog(Context context) {
        super(context);
        mContext = (Activity) context;
    }

    public CustomDialog(Context context, int alertType) {
        super(context, alertType);
        mContext = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().width = DisplayUtil.dip2px(mContext,50);
        getWindow().getAttributes().height = DisplayUtil.dip2px(mContext,50);
    }

    @Override
    public void show() {
        super.show();
        KeyboardUtils.hideSoftInput(mContext);
    }
    public void dismissWithAnimation(){

    }
}
