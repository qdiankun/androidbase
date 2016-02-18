package com.me.diankun.toolbardemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.diankun.toolbardemo.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private String[] mDatas = new String[]{"Toolbar使用Demo", "复用Toolbar", "给Toolbar设置样式", "Toolbar自定义Title",
            "缩放Toolbar", "向上滑动隐藏toolbar;向下滑动显示", "Toolbar下跟着Tab"};
    private Class<? extends Activity>[] clazz = new Class[]{ToolbarDemoActivity.class, ReuseToolbarActivity.class,
            StyleToolbarActivity.class, TitleToolbarActivity.class, ExpandToolbarActivity.class, ScrollToolbarActivity.class, TabToolbarActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the toolbar view inside the activity layout
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(mToolbar);

        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(MainActivity.this, clazz[position]);
                startActivity(i);
            }
        });
    }


}
