package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/4.
 *
 * 参考Blog  http://www.cnblogs.com/fuly550871915/p/4985053.html
 */
public class DragViewActivity extends AppCompatActivity {

    private CustomTextView customTextView;
    private Button btn_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn_scroll = (Button) findViewById(R.id.btn_scroll);
        customTextView = (CustomTextView) findViewById(R.id.customtxtview);
        btn_scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customTextView.scrollTo(300,300);
            }
        });
    }
}
