package com.mola.tellandfeelings.webmodel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mola.tellandfeelings.pojo.Setting;

import java.util.Timer;
import java.util.TimerTask;

public class TellRefresherService extends Service {
    private static final String TAG = "TellRefresherService";
    private Timer mRefreshTimer;
    //service的接口
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onStartCommand");
        //初始化timer
        initTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //初始化timer
    private void initTimer(){
        mRefreshTimer=new Timer();
        mRefreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //发送广播
                sendBroadcast(new Intent("com.mola.refreshList"));
                Log.d(TAG, "run: ");
            }
        },100, Setting.getSettingInstance().getShowSpeed());
    }

}
