package com.me.diankun.showinfodemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.me.diankun.showinfodemo.adapter.ButtonItemAdapter;
import com.me.diankun.showinfodemo.utils.PreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diankun on 2016/3/11.
 */
public class LibraryDialogActivity extends AppCompatActivity {


    @Bind(R.id.listview)
    ListView mListView;
    private String[] mDatas;
    private ArrayAdapter mAdapter;

    protected PreferenceUtils mPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_library_dialog);
        ButterKnife.bind(this);

        mPreferenceUtils = PreferenceUtils.getInstance(this);

        mDatas = getResources().getStringArray(R.array.material_design_dialog);
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);

        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    private void initEvent() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        libraryDialog1();
                        break;
                    case 1:
                        libraryDialog2();
                        break;
                    case 2:
                        libraryDialog3();
                        break;
                    case 3:
                        libraryDialog4();
                        break;
                    case 4:
                        libraryDialog5();
                        break;
                    case 5:
                        libraryDialog6();
                        break;
                    case 6:
                        libraryDialog7();
                        break;
                    case 7:
                        //libraryDialog8();
                        break;
                    default:
                        break;
                }
            }
        });
    }



    private void libraryDialog8() {
//        GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.colors_panel_layout, null);
//        List<Integer> list = initColorDatas();
//        final ColorChooseAdapter adapter = new ColorChooseAdapter(this, list);
//        final int chooseValue = mPreferenceUtils.getIntParam(getString(R.string.choosed_theme_position));
//        adapter.setCheckedPosition(chooseValue);
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//        gridView.setCacheColorHint(0);
//        gridView.setAdapter(adapter);
//
//        boolean wrapInScrollView = false;
//        MaterialDialog.Builder builder =new MaterialDialog.Builder(this)
//                .title(R.string.title)
//                .customView(gridView, wrapInScrollView)
//                .positiveText(R.string.positive);
//        final MaterialDialog dialog = builder.show();
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                dialog.dismiss();
//                //与当前主题不同，修改
//                if (chooseValue != position) {
//                    mPreferenceUtils.saveParam(getString(R.string.choosed_theme_position), position);
//                    //changeTheme();
//                }
//            }
//        });
    }

    private void libraryDialog7() {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .adapter(new ButtonItemAdapter(this, R.array.socialNetworks),
                        new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                Toast.makeText(LibraryDialogActivity.this, "Clicked item " + which, Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
    }


    private void libraryDialog6() {
        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .items(R.array.items)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected.
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void libraryDialog5() {

        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .items(R.array.items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Toast.makeText(LibraryDialogActivity.this, "你点击了：" + which + "位置", Toast.LENGTH_SHORT).show();
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return false;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void libraryDialog4() {
        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .items(R.array.items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Toast.makeText(LibraryDialogActivity.this, "你点击了：" + which + "位置", Toast.LENGTH_SHORT).show();
                    }
                })
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .show();
    }

    private void libraryDialog3() {
        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .content(R.string.content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .iconRes(R.mipmap.ic_launcher)
                        //设置点击监听事件
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        Toast.makeText(LibraryDialogActivity.this, "onPositive", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        Toast.makeText(LibraryDialogActivity.this, "onNegative", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {
                        super.onNeutral(dialog);
                        Toast.makeText(LibraryDialogActivity.this, "onNeutral", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    private void libraryDialog2() {
        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.title)
                .setMessage(R.string.message)
                .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    private void libraryDialog1() {

        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .content(R.string.content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .iconRes(R.mipmap.ic_launcher)
                .show();
    }
}
