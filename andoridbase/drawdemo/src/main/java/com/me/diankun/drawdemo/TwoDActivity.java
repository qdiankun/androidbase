package com.me.diankun.drawdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.me.diankun.drawdemo.view.CanavasView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diankun on 2016/3/11.
 */
public class TwoDActivity  extends AppCompatActivity {

    @Bind(R.id.canavasview)
    CanavasView canavasView;

    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2d);

        ButterKnife.bind(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canavasView.setBitmap(bitmap);
        if (null != getIntent()) {
            int mode = getIntent().getIntExtra("drawMode", 0);
            canavasView.setDrawMode(CanavasView.DrawMode.valueOf(mode));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bitmap!=null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
