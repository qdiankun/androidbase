package com.me.diankun.drawdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/15.
 */
public class DrawPathLineActivity extends AppCompatActivity {

    private Button btn_draw_quadTo_lineTo;
    private Button btn_draw_surfaceview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_path_line);

        btn_draw_quadTo_lineTo = (Button) findViewById(R.id.btn_draw_quadTo_lineTo);
        btn_draw_surfaceview = (Button) findViewById(R.id.btn_draw_surfaceview);
        btn_draw_quadTo_lineTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DrawPathLineActivity.this, QuadLineActivity.class);
                startActivity(i);
            }
        });
        btn_draw_surfaceview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DrawPathLineActivity.this, SurfaceViewActivity.class);
                startActivity(i);
            }
        });
    }


}
