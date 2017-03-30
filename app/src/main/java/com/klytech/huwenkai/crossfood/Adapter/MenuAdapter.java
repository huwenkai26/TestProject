package com.klytech.huwenkai.crossfood.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.klytech.huwenkai.crossfood.Activity.HomeActivity;
import com.klytech.huwenkai.crossfood.Bean.Food;
import com.klytech.huwenkai.crossfood.R;
import com.klytech.huwenkai.crossfood.Utils.ConstantValue;
import com.klytech.huwenkai.crossfood.Utils.LogUtils;
import com.klytech.huwenkai.crossfood.Utils.SpUtil;
import com.klytech.huwenkai.crossfood.Utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by huwenkai on 2017-03-14.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{

    private final List<Food.DataBean.RemarkBean> remark;
    private List<Food.DataBean> foodlist;
    private Context mContext;
    private int mPosition;
    private RecyclerView.Adapter mAdapter;
    private Button bt_unfinish;
    private TranslateAnimation mTranslateAnimation;
    private OkHttpClient okHttpClient;
    private Food.DataBean.RemarkBean remarkBean;
    private String token;
    private String food_id;
    private String order_id;


    public MenuAdapter(Context applicationContext, List<Food.DataBean> foodlist, int position, RecyclerView.Adapter myRecyclerviewAdapter, Button bt_unfinish, Food.DataBean dataBean) {
        this.foodlist = foodlist;
        this.mContext = applicationContext;
        this.mPosition = position;
        this.mAdapter = myRecyclerviewAdapter;
        this.bt_unfinish = bt_unfinish;
        this.remark = dataBean.remark;
        initAnimation();
        initInternet();
    }

    private void initInternet() {
        token = SpUtil.getString(mContext, ConstantValue.TOKEN, "");
        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)

                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    private void initAnimation() {
        mTranslateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        mTranslateAnimation.setDuration(500);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);//解决宽度不能铺满
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String food_name = foodlist.get(mPosition).remark.get(position).food_name;
        holder.greens.setText(food_name);


        remarkBean = remark.get(position);



        int served = remarkBean.served;
        if (served != 1) {
            holder.delete_greens.setVisibility(View.GONE);
            holder.finish.setVisibility(View.VISIBLE);
        }
        holder.delete_greens.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(final View view) {
                ToastUtil.show(mContext,"anle");

                //添加动画效果,动画默认是非阻塞的,所以执行动画的同时,动画以下的代码也会执行
                remarkBean = remark.get(position);
                food_id = remarkBean.food_id;
                order_id = remarkBean.order_id;
                String url = ConstantValue.URL + ConstantValue.served_food;
                LogUtils.e("MenuAdapter", token);
                OkHttpUtils
                        .get()
                        .url(url)
                        .addParams("food_id", food_id)
                        .addParams("order_id", order_id)
                        .addParams("token", token)
                        .build()
                        .execute(new StringCallback() {


                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtil.show(mContext, "网络错误");
                                view.setEnabled(false);
                                view.setBackgroundColor(Color.GRAY);

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                //如果返回的response里没有code":200则返回错误
                                if (!response.contains("code\":200")) {
                                    LogUtils.e("Code", "code码错误" + response + "  " + order_id + "　：" + food_id + mPosition + "   " + position);
                                    view.setEnabled(false);
                                    view.setBackgroundColor(Color.GRAY);
                                    LogUtils.e("Codeview:isEnabled", view.isEnabled() + "");
                                    final Handler handler = new Handler() {
                                        public void handleMessage(Message msg) {
                                            if (msg.what == 1) {
                                                view.setEnabled(true);
                                                view.setBackgroundColor(Color.parseColor("#FF9b2A"));

                                                holder.ll.startAnimation(mTranslateAnimation);
                                                mTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {
                                                        LogUtils.e("ssss");
                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {
                                                        //该桌号的菜的集合
                                                        if (mAdapter instanceof HomeActivity.MyRecyclerviewAdapter) {
                                                            foodlist.get(mPosition).remark.remove(position);

                                                            mAdapter.notifyDataSetChanged();
                                                            notifyDataSetChanged();
                                                            if (foodlist.get(mPosition).remark.size() == 0) {
                                                                foodlist.remove(mPosition);
                                                                mAdapter.notifyDataSetChanged();
                                                                //todo 未完成的人数
                                                                bt_unfinish.setText("未完成(" + foodlist.size() + ")");
                                                            }
                                                        } else {


                                                            holder.delete_greens.setVisibility(View.GONE);
                                                            holder.finish.setVisibility(View.VISIBLE);
//                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {

                                                    }
                                                });

                                            }
                                            super.handleMessage(msg);
                                        }

                                        ;
                                    };
                                    Timer timer = new Timer();
                                    TimerTask task = new TimerTask() {

                                        @Override
                                        public void run() {
                                            // 需要做的事:发送消息
                                            Message message = new Message();
                                            message.what = 1;
                                            handler.sendMessage(message);


                                        }
                                    };
                                    timer.schedule(task, 500);
                                    ToastUtil.show(mContext, "这道菜被划了");


                                } else {
                                    holder.ll.startAnimation(mTranslateAnimation);
                                    mTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            LogUtils.e("ssss");
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            //该桌号的菜的集合
                                            if (mAdapter instanceof HomeActivity.MyRecyclerviewAdapter) {
                                                foodlist.get(mPosition).remark.remove(position);

                                                mAdapter.notifyDataSetChanged();
                                                notifyDataSetChanged();
                                                if (foodlist.get(mPosition).remark.size() == 0) {
                                                    foodlist.remove(mPosition);
                                                    mAdapter.notifyDataSetChanged();
                                                    bt_unfinish.setText("未完成(" + foodlist.size() + ")");
                                                }
                                            } else {


                                                holder.delete_greens.setVisibility(View.GONE);
                                                holder.finish.setVisibility(View.VISIBLE);
//                            }
                                            }
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                }
                            }
                        });


            }
        });
    }


    @Override
    public int getItemCount() {

        return foodlist.get(mPosition).remark.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView finish;
        public TextView greens;
        public Button delete_greens;
        public LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            greens = (TextView) itemView.findViewById(R.id.greens);

            delete_greens = (Button) itemView.findViewById(R.id.delete_greens);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_menu);
            finish = (TextView) itemView.findViewById(R.id.finish);
        }
    }
}
