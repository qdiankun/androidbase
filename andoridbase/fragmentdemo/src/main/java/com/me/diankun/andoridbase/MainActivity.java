package com.me.diankun.andoridbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.diankun.andoridbase.news.NewsDemoActivity;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private final String[] items = {"Fragment的生命周期", "使用静态Fragment", "使用动态Fragment", "通过接口交互", "加入&弹出回退栈", "适配平板手机的简易新闻客户端"};
    private final Class<?>[] classes = {LifeActivity.class, StaticFragmentActivity.class, DynamicFragmentActivity.class, RssfeedActivity.class, BackstckActivity.class,NewsDemoActivity.class};
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
