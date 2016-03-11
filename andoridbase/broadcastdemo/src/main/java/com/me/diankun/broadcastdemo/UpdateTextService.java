package com.me.diankun.broadcastdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Administrator on 2015/11/8.
 */
public class UpdateTextService extends Service {

    boolean flag = true;//用于停止线程

    private ServiceReceiver receiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //注册广播,用于接收停止广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.diankun.stopservice");
        receiver = new ServiceReceiver();
        registerReceiver(receiver, filter);
        //更新数据，发送广播给前台
        new MyThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "不在更新", Toast.LENGTH_SHORT).show();
            flag = false;//跳出线程里面的While循环
            stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyThread extends Thread {

        Random random = new Random();

        @Override
        public void run() {

            while (flag) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = random.nextInt(100);
                Intent intent = new Intent("com.diankun.updateui");
                intent.putExtra("data", String.valueOf(i));
                sendBroadcast(intent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
