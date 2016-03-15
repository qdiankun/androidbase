package com.me.diankun.drawdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.me.diankun.drawdemo.view.ScrawView;

/**
 * Created by diankun on 2016/3/15.
 */
public class ScrawActivity extends AppCompatActivity {

    private Button btn_get_cachebitmap;
    private ScrawView scrawview;
    private ImageView cacheiamge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraw);

        cacheiamge = (ImageView) findViewById(R.id.cacheiamge);
        scrawview = (ScrawView) findViewById(R.id.scrawview);
        btn_get_cachebitmap = (Button) findViewById(R.id.btn_get_cachebitmap);
        btn_get_cachebitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap cacheBitmap = scrawview.getCacheBitmap();
                cacheiamge.setImageBitmap(cacheBitmap);
            }
        });
    }
}
