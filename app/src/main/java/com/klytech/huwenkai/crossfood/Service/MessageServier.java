package com.klytech.huwenkai.crossfood.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.klytech.huwenkai.crossfood.R;
import com.klytech.huwenkai.crossfood.Utils.ConstantValue;
import com.klytech.huwenkai.crossfood.Utils.LogUtils;
import com.klytech.huwenkai.crossfood.Utils.SpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


/**
 * Created by huwenkai on 2017-03-16.
 */

public class MessageServier extends Service {

    private int userId;
    private MYWebSocketListener myWebSocketListener;
    private OkHttpClient mOkHttpClient;
    private WebSocket webSocket;
    private Vibrator vibrator;
    private MediaPlayer mMediaPlayer;
    private Vibrator vib;
    private Request request;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("ONbind", "onbind");
        userId = SpUtil.getInt(getApplicationContext(), ConstantValue.ID, 0);
        LogUtils.e("useid", userId + "");
        initInternet();

        return super.onStartCommand(intent, flags, startId);
    }

    private void initInternet() {

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        OkHttpUtils.initClient(mOkHttpClient);
        //配置url
        String url = "ws://" + ConstantValue.SOCKETURL + "/websocket";
        request = new Request.Builder().get().url(url).build();
        LogUtils.e("URl", url);
        myWebSocketListener = new MYWebSocketListener();
        webSocket = mOkHttpClient.newWebSocket(request, myWebSocketListener);
    }

    class MYWebSocketListener extends WebSocketListener {
        private final ExecutorService sendExecutor = Executors.newSingleThreadExecutor();
        private WebSocket webSocket;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            JSONObject json = new JSONObject();
            try {
                json.put("id", userId);
                json.put("user_type", 2);
                webSocket.send(json.toString());

                LogUtils.e("json", json.toString());
                this.webSocket = webSocket;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogUtils.e("WebSocketCall", "onOpen");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            mOkHttpClient.newWebSocket(request, myWebSocketListener);
            LogUtils.e("WebSocketCall", "onFailure");
        }

        /**
         * 接收到消息
         *
         * @param text
         * @throws IOException
         */

        @Override
        public void onMessage(WebSocket webSocket, String text) {

            super.onMessage(webSocket, text);
            Intent intent = new Intent();
            intent.putExtra(ConstantValue.WebSocket, text);
            intent.setAction(ConstantValue.ACTION);
            sendBroadcast(intent);
            //开启震动

            //todo 这里要判断是否开启消息提醒
            boolean isVib = SpUtil.getBoolean(getApplication(), ConstantValue.ISOPENVIB, false);
            boolean iSsend = SpUtil.getBoolean(getApplication(), ConstantValue.SENDMESSAGE, false);
            LogUtils.e("ISvib",isVib+"");
            if(isVib&&iSsend){
            if(vib==null){
                vib = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
            }

            vib.vibrate(500);}
            //判断是否为后台，然后开启响铃
            boolean isVol = SpUtil.getBoolean(getApplication(), ConstantValue.ISOPENVol, false);
            LogUtils.e("isVols",isVol+"");

            boolean Isbackground = isBackground(getApplicationContext());
           if (Isbackground&&isVol&&iSsend)
                PlaySound();


            sendExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * 6);
                        JSONObject json = new JSONObject();
                        try {
                            json.put("id", userId);
                            json.put("user_type", 2);
                            MYWebSocketListener.this.webSocket.send(json.toString());
                            LogUtils.d("sendExecutor", "sendExecutor");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            LogUtils.e("SocketText", text);


        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {

            super.onClosing(webSocket, code, reason);
            mOkHttpClient.newWebSocket(request, myWebSocketListener);
            //// TODO: 2017-03-30 服务器断线重连
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            sendExecutor.shutdown();
        }


    }

    public void PlaySound() {


        LogUtils.e("PlaySound1","PlaySound");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

// 如果为空，才构造，不为空，说明之前有构造过
        if(mMediaPlayer == null)
            LogUtils.e("PlaySound2","PlaySound");
            mMediaPlayer =  new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/"+ R.raw.aa));
            mMediaPlayer.setLooping(false); //循环播放
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    // 要释放资源，不然会打开很多个MediaPlayer
                    mMediaPlayer.release();
                }
            });
            LogUtils.e("PlaySound3","PlaySound");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("PlaySound4","PlaySound");
        }
    }

    @Override
    public void onDestroy() {
        if (webSocket != null) {
            webSocket.close(1000,null);
        }
        if(vib!=null){
            vib.cancel();
        }
        LogUtils.e("Ondestroy","OnDestroy");
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            // 要释放资源，不然会打开很多个MediaPlayer
            mMediaPlayer.release();
        }

        super.onDestroy();
    }
    public  boolean isBackground(Context context) {


        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.e("后台", appProcess.processName);
                    return true;
                }else{
                    Log.e("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

}
