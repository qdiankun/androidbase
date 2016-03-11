package com.me.diankun.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by diankun on 2016/3/11.
 */
public class BroadCastActivity  extends AppCompatActivity {

    private BroadcastReceiver receiver;

    private TextView number;
    private Button btn_send_broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);

        initView();

        //注册广播，接收后台数据更新ui
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("com.diankun.updateui");
        registerReceiver(receiver, filter);

        //发送广播
        startService(new Intent(this, UpdateTextService.class));
    }

    private void initView() {
        number = (TextView) findViewById(R.id.number);
        btn_send_broadcast = (Button) findViewById(R.id.btn_send_broadcast);
        btn_send_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadCast();
            }
        });
    }

    void sendBroadCast() {
        Intent intent = new Intent("com.diankun.stopservice");
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(BroadCastActivity.this, "接收到广播", Toast.LENGTH_SHORT).show();
            //判断传递过来的Intent
            if (intent != null) {
                String data = intent.getStringExtra("data");
                number.setText(data);
            }
        }
    }
}
