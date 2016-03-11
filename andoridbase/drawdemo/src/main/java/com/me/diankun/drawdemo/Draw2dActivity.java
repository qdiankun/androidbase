package com.me.diankun.drawdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.me.diankun.drawdemo.view.CanavasView;

/**
 * 参考Blog:
 * http://blog.csdn.net/iispring/article/details/49770651
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2012/1212/703.html
 * https://segmentfault.com/a/1190000000721127
 */
public class Draw2dActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw2d);
    }


    @Override
    public void onClick(View v) {
        CanavasView.DrawMode mode = null;
        switch (v.getId()) {
            case R.id.btn_draw_axis:
                mode = CanavasView.DrawMode.AXIS;
                break;
            case R.id.btn_draw_argb:
                mode = CanavasView.DrawMode.ARGB;
                break;
            case R.id.btn_draw_text:
                mode = CanavasView.DrawMode.TEXT;
                break;
            case R.id.btn_draw_point:
                mode = CanavasView.DrawMode.POINT;
                break;
            case R.id.btn_draw_line:
                mode = CanavasView.DrawMode.LINE;
                break;
            case R.id.btn_draw_rect:
                mode = CanavasView.DrawMode.RECT;
                break;
            case R.id.btn_draw_circle:
                mode = CanavasView.DrawMode.CIRCLE;
                break;
            case R.id.btn_draw_oval:
                mode = CanavasView.DrawMode.OVAL;
                break;
            case R.id.btn_draw_arc:
                mode = CanavasView.DrawMode.ARC;
                break;
            case R.id.btn_draw_path:
                mode = CanavasView.DrawMode.PATH;
                break;
            case R.id.btn_draw_bitmap:
                mode = CanavasView.DrawMode.BITMAP;
                break;
            case R.id.btn_draw_pathto:
                mode = CanavasView.DrawMode.PATHTO;
                break;
            case R.id.btn_draw_arc_demo:
                mode = CanavasView.DrawMode.ARCDEMO;
                break;
        }
        openActivity(TwoDActivity.class, "drawMode", mode.getValue());
    }

    public void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void openActivity(Class clazz, String name, int value) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(name, value);
        startActivity(intent);
    }
}
