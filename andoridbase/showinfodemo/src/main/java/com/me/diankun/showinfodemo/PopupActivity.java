package com.me.diankun.showinfodemo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.me.diankun.showinfodemo.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/11.
 */
public class PopupActivity extends AppCompatActivity {

    private LayoutInflater mInflater;
    private PopupWindow mPopupWindow;

    @Bind(R.id.ll_rootview)
    LinearLayout mRootView;

    @Bind(R.id.menu)
    TextView menuTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        ButterKnife.bind(this);

        mInflater = LayoutInflater.from(this);
    }

    /**
     * 使用showAtLocation来显示一个PopupWindow
     *
     * @param view
     */
    @OnClick(R.id.btn_show_popup)
    void showPopup(View view) {
        //设置contentView
        View contentView = mInflater.inflate(R.layout.popup_layout, null);
        //注册点击事件
        Button item_popupwindows_camera = (Button) contentView.findViewById(R.id.item_popupwindows_camera);
        Button item_popupwindows_photo = (Button) contentView.findViewById(R.id.item_popupwindows_photo);
        Button item_popupwindows_cancel = (Button) contentView.findViewById(R.id.item_popupwindows_cancel);
        item_popupwindows_camera.setOnClickListener(new PopupClickListener());
        item_popupwindows_photo.setOnClickListener(new PopupClickListener());
        item_popupwindows_cancel.setOnClickListener(new PopupClickListener());
        //新建PopupWindow,注意：布局参数以代码中的为准
        mPopupWindow = new PopupWindow(contentView);
        // 设置弹出窗体的宽
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 是否具有获取焦点的能力
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 设置弹出窗体的背景
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示PopupWindow
        mPopupWindow.showAtLocation(mRootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    @OnClick(R.id.menu)
    void showMenuPopup(View view) {
        View menuView = mInflater.inflate(R.layout.popup_menu_layout, null);
        //注册点击事件
        TextView pop_computer = (TextView) menuView.findViewById(R.id.pop_computer);
        TextView pop_financial = (TextView) menuView.findViewById(R.id.pop_financial);
        TextView pop_manage = (TextView) menuView.findViewById(R.id.pop_manage);
        pop_computer.setOnClickListener(new PopupClickListener());
        pop_financial.setOnClickListener(new PopupClickListener());
        pop_manage.setOnClickListener(new PopupClickListener());
        //新建PopupWindow,注意：布局参数以代码中的为准
        PopupWindow menuPopup = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 是否具有获取焦点的能力,EditText使用的到
        menuPopup.setFocusable(true);
        //menuPopup.setTouchable(true);//默认是可点击的Touch
        //PopupWindow以外的区域是否可点击,隐藏Popup,注意一下两行代码都需要
        menuPopup.setOutsideTouchable(true);
        menuPopup.setBackgroundDrawable(new BitmapDrawable());// 设置弹出窗体的背景
        //设置动画效果
        menuPopup.setAnimationStyle(R.style.menuAnim);
        //显示PopupWindow
        menuPopup.showAsDropDown(menuTV);
    }


    class PopupClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_popupwindows_camera:
                    ToastUtils.showShort("拍照");
                    break;
                case R.id.item_popupwindows_photo:
                    ToastUtils.showShort("从相册中选取");
                    break;
                case R.id.item_popupwindows_cancel:
                    ToastUtils.showShort("取消");
                    break;
                //menu菜单中的item
                case R.id.pop_computer:
                    ToastUtils.showShort("计算机");
                    break;
                case R.id.pop_financial:
                    ToastUtils.showShort("金融");
                    break;
                case R.id.pop_manage:
                    ToastUtils.showShort("管理");
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
