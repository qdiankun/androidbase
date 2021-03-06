package com.me.diankun.imagedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private final String[] items = {
            "图片显示指定大小", "src与background区别", "缩放Activity", "宽度填充高度自适应",
            "通过HttpURLConnection获取图片", "使用DiskCache缓存图片", "照片墙的实现",
            "手机拍照","ImageButton&ImageView点击效果","获取当前屏幕的截图"
    };

    private final Class<?>[] classes = {
            RadioActivity.class, CheckActivity.class, ScaleActivity.class, MaxActivity.class,
            GetImageActivity.class, DiskCacheActivity.class, PhotoWallActivity.class,
            TakePhotoActivity.class,ImageClickActivity.class,GetScreenActivity.class
    };
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = new ListView(context);
        mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setFadingEdgeLength(0);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, classes[position]);
                startActivity(intent);
            }
        });

        setContentView(lv);
    }
}
