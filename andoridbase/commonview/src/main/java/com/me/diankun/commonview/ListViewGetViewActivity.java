package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.me.diankun.commonview.adapter.GetviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewGetViewActivity extends AppCompatActivity {

    /**
     * 多次调用原因，参见Blog ：
     * <p/>
     * ListView / GirdView Adpater的getView方法，首项多次调用
     * http://www.liaohuqiu.net/cn/posts/first-view-will-be-created-multi-times-in-list-view/
     */
    @Bind(R.id.lv_page)
    ListView mPageListView;
    private List<String> mViewDatas;
    private GetviewAdapter mViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_get_view);

        ButterKnife.bind(this);

        initDatas();
        mViewAdapter = new GetviewAdapter(mViewDatas, this);
        mPageListView.setAdapter(mViewAdapter);
    }

    private void initDatas() {
        mViewDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mViewDatas.add(String.valueOf((char) ('a' + i)));
        }
    }
}
