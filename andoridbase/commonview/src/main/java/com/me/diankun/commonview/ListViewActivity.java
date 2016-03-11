package com.me.diankun.commonview;

import android.os.Bundle;
import android.view.View;

import com.me.diankun.commonview.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/11.
 */
public class ListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_has_edit)
    void openEditTextActivity(View view) {
        openActivity(ListViewEditActivity.class);
    }

    @OnClick(R.id.btn_more_getview)
    void openMoreGetView(View view) {
        openActivity(ListViewGetViewActivity.class);
    }

    @OnClick(R.id.btn_dynamic_item)
    void openDynamicAddView(View view) {
        openActivity(ListViewNotifyActivity.class);
    }

    @OnClick(R.id.btn_flexible)
    void openFlexible(View view) {
        openActivity(ListViewFlexibleActivity.class);
    }

    @OnClick(R.id.btn_auto_hide)
    void openAuto(View view) {
        openActivity(ListViewScrollHideActivity.class);
    }

    @OnClick(R.id.btn_show_chat)
    void openChat(View view) {
        openActivity(ListViewChatActivity.class);
    }

    @OnClick(R.id.btn_dynamic_change)
    void dynamicChangeLayout(View view) {
        openActivity(ListViewLayoutActivity.class);
    }

}