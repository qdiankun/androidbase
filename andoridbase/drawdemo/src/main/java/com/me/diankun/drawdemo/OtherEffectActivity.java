package com.me.diankun.drawdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/15.
 * <p/>
 * http://blog.csdn.net/a2758963/article/details/7791474
 */
public class OtherEffectActivity extends AppCompatActivity {


    private Button btn_move_view;
    private Button btn_wave_view;
    private Button btn_wave_view2;
    private Button btn_path_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_effect);


        btn_move_view = (Button) findViewById(R.id.btn_move_view);
        btn_wave_view = (Button) findViewById(R.id.btn_wave_view);
        btn_wave_view2 = (Button) findViewById(R.id.btn_wave_view2);
        btn_path_view = (Button) findViewById(R.id.btn_path_view);
        btn_move_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OtherEffectActivity.this, MoveViewActivity.class);
                startActivity(i);
            }
        });
        btn_wave_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OtherEffectActivity.this, WaveviewActivity.class);
                startActivity(i);
            }
        });
        btn_wave_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OtherEffectActivity.this, PorterDuffXfermodeActivity.class);
                startActivity(i);
            }
        });
        btn_path_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OtherEffectActivity.this, PathViewActivity.class);
                startActivity(i);
            }
        });
    }
}
