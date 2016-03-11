package com.example.animatedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_move_getx)
    void moveUseGetX(View view) {
        openActivity(ScrollViewOneActivity.class);
    }

    @OnClick(R.id.btn_move_getrawx)
    void moveUseGetRawX(View view){
        openActivity(ScrollViewTwoActivity.class);
    }

    @OnClick(R.id.btn_move_layoutparams)
    void moveUseLayoutParams(View view){
        openActivity(ScrollViewThreeActivity.class);
    }

    @OnClick(R.id.btn_move_scrollby)
    void moveUseScrollBy(View view){
        openActivity(ScrollViewFourActivity.class);
    }
    @OnClick(R.id.btn_move_scrollback)
    void moveUseScrollByBack(View view){
        openActivity(ScrollViewFiveActivity.class);
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
