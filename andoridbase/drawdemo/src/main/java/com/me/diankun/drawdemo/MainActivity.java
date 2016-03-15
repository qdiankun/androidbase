package com.me.diankun.drawdemo;

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
            "使用Draw绘制2D图","Canvas实例-绘制Clock","Canvas实例-绘制Layer",
            "quadTo与lineTo()区别&SurfaceView绘制","涂鸦绘制","其他效果"
    };
    private final Class<?>[] classes = {
            Draw2dActivity.class,DrawClockActivity.class,DrawLayerActivity.class,
            DrawPathLineActivity.class,ScrawActivity.class,OtherEffectActivity.class
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
