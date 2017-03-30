package com.klytech.huwenkai.crossfood.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klytech.huwenkai.crossfood.Adapter.MenuAdapter;
import com.klytech.huwenkai.crossfood.Application.ActivityManager;
import com.klytech.huwenkai.crossfood.Application.MyApplication;
import com.klytech.huwenkai.crossfood.Bean.Food;
import com.klytech.huwenkai.crossfood.R;
import com.klytech.huwenkai.crossfood.Utils.ConstantValue;
import com.klytech.huwenkai.crossfood.Utils.LogUtils;
import com.klytech.huwenkai.crossfood.Utils.SpUtil;
import com.klytech.huwenkai.crossfood.Utils.TimeToDate;
import com.klytech.huwenkai.crossfood.Utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class HomeActivity extends BaseActivity {

    private Button bt_all;
    private Button bt_unfinish;
    private LinearLayout ll_unfinish;
    private LinearLayout ll_all;
    private LinearLayout tv_massage;
    private RecyclerView rl_unfinish;
    private RecyclerView rl_all;
    private long mExitTime;
    //对返回键进行监听


    private SwipeRefreshLayout swipe_refresh_widget;
    private SwipeRefreshLayout all_swipe_refresh_widget;
    private OkHttpClient okHttpClient;
    private MyRecyclerviewAdapter adapter;
    private String token;
    private List<Food.DataBean> foodlist;
    private MyAllRecyclerViewAdapter myAllRecyclerViewAdapter;
    private List<Food.DataBean> unfinishList = new ArrayList<Food.DataBean>();

    private MyReceiver receiver;
    private List<Food.DataBean> allList;
    private String mResponse = "";
    private String mResponse2 = "";
    private TextView message;
    private String s = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInternet();


        inData();
    }

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initUi() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_setting) {
                    Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        bt_all = (Button) findViewById(R.id.bt_all);
        bt_unfinish = (Button) findViewById(R.id.bt_unfinish);
        ll_unfinish = (LinearLayout) findViewById(R.id.ll_unfinish);
        ll_all = (LinearLayout) findViewById(R.id.ll_all);
        tv_massage = (LinearLayout) findViewById(R.id.tv_message);
        message = (TextView) findViewById(R.id.message);
        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();

        filter.addAction(ConstantValue.ACTION);
        HomeActivity.this.registerReceiver(receiver, filter);


        rl_unfinish = (RecyclerView) findViewById(R.id.list);
        rl_all = (RecyclerView) findViewById(R.id.all_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        //配置未完成的RecyclerView
        rl_unfinish.setLayoutManager(manager);
        adapter = new MyRecyclerviewAdapter();
        rl_unfinish.setItemAnimator(new DefaultItemAnimator());
        rl_unfinish.setAdapter(adapter);

        //配置全部的RecyclerView
        rl_all.setLayoutManager(new LinearLayoutManager(this));

        rl_all.setItemAnimator(new DefaultItemAnimator());
        myAllRecyclerViewAdapter = new MyAllRecyclerViewAdapter();
        rl_all.setAdapter(myAllRecyclerViewAdapter);

        swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        all_swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.all_swipe_refresh_widget);
        all_swipe_refresh_widget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                all_swipe_refresh_widget.setRefreshing(true);
                allrefresh();
                tv_massage.setVisibility(View.GONE);
            }
        });
        swipe_refresh_widget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e("swipe_refresh_widget", "onRefresh");
                swipe_refresh_widget.setRefreshing(true);
                refresh();

                tv_massage.setVisibility(View.GONE);
            }
        });
        bt_unfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                //1.已加锁列表隐藏,未加锁列表显示
                ll_all.setVisibility(View.GONE);
                ll_unfinish.setVisibility(View.VISIBLE);
                //2.未加锁变成深色图片,已加锁变成浅色图片
                bt_unfinish.setBackgroundResource(R.drawable.tab_left_pressed);
                bt_all.setBackgroundResource(R.drawable.tab_right_default);
            }
        });

        bt_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allrefresh();
                //1.已加锁列表显示,未加锁列表隐藏
                ll_all.setVisibility(View.VISIBLE);
                ll_unfinish.setVisibility(View.GONE);
                //2.未加锁变成浅色图片,已加锁变成深色图片
                bt_unfinish.setBackgroundResource(R.drawable.tab_left_default);
                bt_all.setBackgroundResource(R.drawable.tab_right_pressed);
            }
        });

    }

    /**
     * 初始化平移动画的方法(平移自身的一个宽度大小)
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(HomeActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(false);
        }
    }

    public void initData() {

    }

    private void inData() {

        refresh();
        allrefresh();
    }


    private void refresh() {
        OkHttpUtils
                .post()//
                .url(ConstantValue.URL + ConstantValue.FoodURL)//
                .addParams("token", token)
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.show(HomeActivity.this, "网络错误");
                        swipe_refresh_widget.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (response.contains("code\":200")) {

                            LogUtils.e("foodlist", response);
                            //判断数据相同不做更新
                            if (!mResponse.equals(response)) {
                                mResponse = response;
                                Food food = new Gson().fromJson(response, Food.class);

                                foodlist = food.data;
                                //将没有完成的菜放进一个集合
                                unfinishList = foodlist;
                                for (int i = 0; i < unfinishList.size(); i++) {
                                    for (int j = 0; j < unfinishList.get(i).remark.size(); j++) {

                                        int served = foodlist.get(i).remark.get(j).served;
                                        if (served == 2) {
                                            unfinishList.get(i).remark.remove(j);
                                            j--;
                                            if (unfinishList.get(i).remark.size() == 0) {
                                                unfinishList.remove(i);
                                                i--;
                                                break;
                                            }
                                        }
                                    }
                                }

                                //todo 跟改未完成的数字
                                bt_unfinish.setText("未完成(" + unfinishList.size() + ")");
//                                使加载圆环消失

                                //告知Adapter更新数据
                                adapter.notifyDataSetChanged();
                                swipe_refresh_widget.setRefreshing(false);
                                Toast.makeText(HomeActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                            } else {
                                swipe_refresh_widget.setRefreshing(false);
                                Toast.makeText(HomeActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }

    private void allrefresh() {
        OkHttpUtils
                .post()//
                .url(ConstantValue.URL + ConstantValue.FoodURL)
                .addParams("token", token)
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.show(HomeActivity.this, "网络错误");
                        all_swipe_refresh_widget.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (response.contains("code\":200")) {
                            LogUtils.e("foodlist", response);

                            //判断数据相同不做更新
                            if (!mResponse2.equals(response)) {
                                mResponse2 = response;
                                Food food = new Gson().fromJson(response, Food.class);

                                foodlist = food.data;

                                // todo 跟改未完成的数字
                                allList = food.data;
                                bt_all.setText("全部(" + allList.size() + ")");
//                                使加载圆环消失
                                all_swipe_refresh_widget.setRefreshing(false);
                                //告知Adapter更新数据
                                myAllRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                all_swipe_refresh_widget.setRefreshing(false);
                            }
                        } else {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }


    private void initInternet() {
        token = SpUtil.getString(getApplicationContext(), ConstantValue.TOKEN, "");
        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(7000L, TimeUnit.MILLISECONDS)
                .readTimeout(7000L, TimeUnit.MILLISECONDS)

                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_unfinish, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final LinearLayoutManager manager = new LinearLayoutManager(HomeActivity.this);
            holder.Rlmenu.setLayoutManager(manager);
            MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), unfinishList, position, this, bt_unfinish, unfinishList.get(position));

            holder.Rlmenu.setAdapter(menuAdapter);
            Food.DataBean dataBean = unfinishList.get(position);
            String time = dataBean.time;
            String diner = dataBean.diner;
//
            holder.time.setText(TimeToDate.stampToDate(time));
            //// TODO: 2017-03-23 要把id去除掉
            holder.pelNum.setText(diner + "人" );

            String[] split = dataBean.seat.split(" ");
            holder.deskNum.setText(split[0] + ".");

        }

        @Override
        public int getItemCount() {
            if (unfinishList != null)
                return unfinishList.size();

            return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView time;
            public TextView deskNum;
            public TextView pelNum;
            public RecyclerView Rlmenu;

            public MyViewHolder(View itemView) {
                super(itemView);
                time = (TextView) itemView.findViewById(R.id.time);
                deskNum = (TextView) itemView.findViewById(R.id.desk_num);
                pelNum = (TextView) itemView.findViewById(R.id.pel_num);
                Rlmenu = (RecyclerView) itemView.findViewById(R.id.rv_menu);


            }
        }
    }

    private class MyAllRecyclerViewAdapter extends RecyclerView.Adapter<MyAllRecyclerViewAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyAllRecyclerViewAdapter.MyViewHolder holder = new MyAllRecyclerViewAdapter.MyViewHolder(LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_unfinish, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final LinearLayoutManager manager = new LinearLayoutManager(HomeActivity.this);
            holder.Rlmenu.setLayoutManager(manager);
            MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), allList, position, this, bt_all, allList.get(position));
            holder.Rlmenu.setAdapter(menuAdapter);
            Food.DataBean dataBean = allList.get(position);
            String time = dataBean.time;
            String diner = dataBean.diner;

//
            holder.time.setText("下单时间:" + TimeToDate.stampToDate(time));
            holder.pelNum.setText(diner + "人");


            String[] split = dataBean.seat.split(" ");
            holder.deskNum.setText(split[0] + ".");
        }

        @Override
        public int getItemCount() {
            if (allList != null)
                return allList.size();

            return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView time;
            public TextView deskNum;
            public TextView pelNum;
            public RecyclerView Rlmenu;

            public MyViewHolder(View itemView) {
                super(itemView);
                time = (TextView) itemView.findViewById(R.id.time);
                deskNum = (TextView) itemView.findViewById(R.id.desk_num);
                pelNum = (TextView) itemView.findViewById(R.id.pel_num);
                Rlmenu = (RecyclerView) itemView.findViewById(R.id.rv_menu);


            }
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String PushMessage = bundle.getString(ConstantValue.WebSocket, "");
            LogUtils.e("PushMessage", PushMessage);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(PushMessage);
                JSONObject type = jsonObj.getJSONObject("type");
                s = type.toString();
                LogUtils.e("JSONObject",s);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 得到指定json key对象的value对象
            // 2017-03-20 解析json对象判断是新订单消息还是划菜消息
            //  2017-03-16  将消息栏弹出
            if (PushMessage.contains("type\":2")) {
                tv_massage.setVisibility(View.VISIBLE);
                message.setText("新订单来了");
            }else {
                tv_massage.setVisibility(View.VISIBLE);
                message.setText("有人划菜了");
            }

        }
    }

    @Override
    protected void onDestroy() {
        MyApplication application = (MyApplication) this.getApplication();
        ActivityManager activityManager = application.getActivityManager();
        activityManager.popActivity(this);
        super.onDestroy();
    }

}
