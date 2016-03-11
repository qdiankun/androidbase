package com.me.diankun.showinfodemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.me.diankun.showinfodemo.adapter.CustomDialogAdapter;
import com.me.diankun.showinfodemo.bean.ItemData;
import com.me.diankun.showinfodemo.utils.ToastUtils;
import com.me.diankun.showinfodemo.view.CustomDialog;
import com.me.diankun.showinfodemo.view.QueryDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/11.
 */
public class DialogActivity extends AppCompatActivity {


    @Bind(R.id.iv_customdialog_get)
    ImageView iv_customdialog_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }



    /**
     * 显示一个自定义列表的Dialog
     */
    @OnClick(R.id.btn_show_query_dialog)
    void shoDialog() {
        QueryDialog queryDialog = new QueryDialog(this, 0, this);
        queryDialog.showWindow();
    }

    /**
     * 显示基本的Dialog
     */
    @OnClick(R.id.btn_show_base_dialog)
    void showBaseDialog() {
        baseDialog();
    }



    /**
     * 显示基本的ListDialog
     */
    @OnClick(R.id.btn_show_list_dialog)
    void showBaseListDialog() {
        baseListDialog();
    }



    /**
     * 显示基本的单选ListDialog
     */
    @OnClick(R.id.btn_show_singlelist_dialog)
    void showBaseSingleListDialog() {
        baseSingleListDialog();
    }

    /**
     * 显示基本的多选ListDialog
     */
    @OnClick(R.id.btn_show_multilist_dialog)
    void showMultiListDialog() {
        baseMultiListDialog();
    }

    /**
     * 使用Adapter来显示ListDialog
     */
    @OnClick(R.id.btn_show_list_dialog_adapter)
    void showCustomAdapterDialog() {
        customAdapterDialog();
    }


    /**
     * 显示自定义的CustomDialog
     * <p/>
     * 可以实现往Dialog中传递参数和回调监听的功能
     * <p/>
     * 传参数，在Custom中显示传递的参数信息
     * 监听，点击Custom中的图片后，在Activity中显示选中的图片
     */
    @OnClick(R.id.btn_show_custom_dialog)
    void showCustomDialog() {
        customDialog();
    }

    private void customDialog() {
        CustomDialog customDialog = new CustomDialog(this, "我是萌宠，请选择", new CustomDialog.ICustomDialogEventListener() {
            @Override
            public void customDialogEvent(int valueYouWantToSendBackToTheActivity) {
                iv_customdialog_get.setImageResource(valueYouWantToSendBackToTheActivity);
            }
        });
        customDialog.show();
    }


    private void baseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dialog1");//标题
        //builder.setIcon(R.mipmap.ic_launcher);//设置对话图标
        builder.setMessage("对话框内容");//对话框内容

        // 添加一个按钮，并监听按钮事件 (积极的;确实的)
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort("PositiveButton");
            }
        });
        // 添加一个按钮，并监听按钮事件 (消极的;否认的)
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort("NegativeButton");
            }
        });
        // 添加一个按钮，并监听按钮事件 (中立的)
        builder.setNeutralButton("帮助", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort("NeutralButton");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void baseListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dialog2");//标题
        //builder.setIcon(R.mipmap.ic_launcher);//设置对话图标
        //builder.setMessage("对话框内容");//对话框内容
        //列表
        CharSequence chars[] = {"hello", "world", "nihao", "shijie"};
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 点击了第几列
                ToastUtils.showShort("点击了" + which + "位置");
                // ... 动作
            }
        });
        // 添加一个按钮，并监听按钮事件 (积极的;确实的)
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort("PositiveButton");
            }
        });
        builder.show();
    }


    private void baseSingleListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dialog2");//标题
        //builder.setIcon(R.mipmap.ic_launcher);//设置对话图标
        //builder.setMessage("对话框内容");//对话框内容
        //列表
        CharSequence chars[] = {"hello", "world", "nihao", "shijie"};
        builder.setSingleChoiceItems(chars, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击了第几列
                ToastUtils.showShort("点击了" + which + "位置");
            }
        });

        // 添加一个按钮，并监听按钮事件 (积极的;确实的)
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort("PositiveButton");
            }
        });
        builder.show();
    }


    private void baseMultiListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dialog2");//标题
        //builder.setIcon(R.mipmap.ic_launcher);//设置对话图标
        //builder.setMessage("对话框内容");//对话框内容
        //列表
        CharSequence[] chars = {"hello", "world", "nihao", "shijie"};
        boolean[] checked = new boolean[]{false, false, false, false};
        builder.setMultiChoiceItems(chars, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                ToastUtils.showShort("点击了" + which + "位置");
            }
        });
        // 添加一个按钮，并监听按钮事件 (积极的;确实的)
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort("PositiveButton");
            }
        });
        builder.show();
    }


    private void customAdapterDialog() {
        List<ItemData> datas = generateData();
        CustomDialogAdapter dialogAdapter = new CustomDialogAdapter(datas, this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("可爱萌宠");
        builder.setAdapter(dialogAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Toast.makeText(DialogActivity.this, "clicked:" + which, Toast.LENGTH_LONG).show();
            }
        });
        builder.create();
        builder.show();
    }


    private List<ItemData> generateData() {
        List<ItemData> itemLists = new ArrayList<ItemData>();
        ItemData itemData = new ItemData("可爱萌宠1", R.drawable.animal1);
        ItemData itemData2 = new ItemData("可爱萌宠2", R.drawable.animal2);
        ItemData itemData3 = new ItemData("可爱萌宠3", R.drawable.animal3);
        ItemData itemData4 = new ItemData("可爱萌宠4", R.drawable.animal4);
        itemLists.add(itemData);
        itemLists.add(itemData2);
        itemLists.add(itemData3);
        itemLists.add(itemData4);
        return itemLists;
    }
}
