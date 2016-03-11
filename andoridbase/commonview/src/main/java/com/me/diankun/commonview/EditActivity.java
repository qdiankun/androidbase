package com.me.diankun.commonview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.me.diankun.commonview.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/11.
 */
public class EditActivity  extends Activity {

    /**
     * 未设置固定长度缺点：
     *
     * 如果EditText的宽度设置为wrap_content,并且它的父布局也设置为wrap_content,那么随着输入内容的增多
     * 输入框会被拉长，若超出一行，自动换行显示，显示全部内容，弊端是输入内容过多时，占据屏幕大部分空间非常丑
     *
     * 解决方法: 设置 maxLine，最多显示几行
     */

    @Bind(R.id.et_hide_input)
    EditText mHideEdit;

    @Bind(R.id.et_input_after)
    EditText mInputAfterEdit;

    @Bind(R.id.et_ontouch)
    EditText mTouchEdit;

    @Bind(R.id.et_filter)
    EditText mFilterEdit;

    @Bind(R.id.et_inputtype)
    EditText mInputTypeEdit;

    @Bind(R.id.et_number_watcher)
    EditText mInputNumberOnlyEdit;

    @Bind(R.id.tv_can_input)
    TextView mInputNumberText;

    @Bind(R.id.et_input_numbers)
    EditText mInputNumberEdit;

    //测试是否获取焦点
    @Bind(R.id.et_test_focus)
    EditText et_test_focus;

    @Bind(R.id.et_test_focus_two)
    EditText et_test_focus_two;

    // 字符限制长度
    private static final int MAX_TEXT_INPUT_LENGTH = 5;

    // 还可以输入多少字符
    final int MAX_LENGTH = 20;
    int Rest_Length = MAX_LENGTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ButterKnife.bind(this);

        initEvent();

        // 设置始终不显示键盘
        mInputTypeEdit.setInputType(InputType.TYPE_NULL);

        // 限定输入字符长度的3种方法
        // filter1();
        // filter2();
        filter3();

        // 监听只能输入整型数字
        inputNumberOnlyWatcher();

        // 显示剩余可输入的字符数
        showCanInputNumber();


        //13测试是否获取焦点和失去焦点
        testIsHasFocus();
    }

    //测试是否获取或失去焦点
    private void testIsHasFocus() {
        et_test_focus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ToastUtils.showShort("got the focus");
                }else{
                    ToastUtils.showShort("lost the focus");
                }
            }
        });
    }

    private void showCanInputNumber() {
        mInputNumberEdit.addTextChangedListener(new TextWatcher() {

            private int cou = 0;
            int selectionEnd = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                cou = before + count;
                String editable = mInputNumberEdit.getText().toString();
                String str = stringFilter(editable);
                if (!editable.equals(str)) {
                    mInputNumberEdit.setText(str);
                }
                mInputNumberEdit.setSelection(mInputNumberEdit.length());
                cou = mInputNumberEdit.length();
                if (Rest_Length > 0) {
                    Rest_Length = MAX_LENGTH
                            - mInputNumberEdit.getText().length();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                mInputNumberText.setText("还能输入" + Rest_Length + "个字");
            }

            @Override
            public void afterTextChanged(Editable s) {
                mInputNumberText.setText("还能输入" + Rest_Length + "个字");
                if (cou > MAX_LENGTH) {
                    selectionEnd = mInputNumberEdit.getSelectionEnd();
                    s.delete(MAX_LENGTH, selectionEnd);
                }
            }
        });
    }

    // 清空
    @OnClick(R.id.btn_clear_input)
    void clearText(View view) {
        mInputNumberEdit.setText("");
        Rest_Length = MAX_LENGTH;
    }

    private void inputNumberOnlyWatcher() {
        mInputNumberOnlyEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Log.d("TAG", "onTextChanged--------------->");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                Log.d("TAG", "beforeTextChanged--------------->");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TAG", "afterTextChanged--------------->");
                String input = mInputNumberOnlyEdit.getText().toString();
                try {
                    // String 转 int
                    if (!TextUtils.isEmpty(input)) {
                        Integer.parseInt(input);
                    }
                } catch (Exception e) {
                    showInpuOnlyNumberDialog();
                }
            }
        });
    }

    protected void showInpuOnlyNumberDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setTitle("消息").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("你输出的整型数字有误，请改正");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void filter3() {
        mFilterEdit.addTextChangedListener(new TextWatcher() {

            private int cou = 0;
            int selectionEnd = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                cou = before + count;
                String editable = mFilterEdit.getText().toString();
                String str = stringFilter(editable);
                if (!editable.equals(str)) {

                    mFilterEdit.setText(str);
                }
                mFilterEdit.setSelection(mFilterEdit.length());
                cou = mFilterEdit.length();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cou > MAX_TEXT_INPUT_LENGTH) {
                    selectionEnd = mFilterEdit.getSelectionEnd();
                    s.delete(MAX_TEXT_INPUT_LENGTH, selectionEnd);
                }

            }
        });
    }

    public static String stringFilter(String str) throws PatternSyntaxException {

        String regEx = "[/\\:*?<>|\"\n\t]";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        return m.replaceAll("");

    }

    private void filter2() {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                boolean bInvlid = false;
                int sourceLen = getCharacterNum(source.toString());
                int destLen = getCharacterNum(dest.toString());
                if (sourceLen + destLen > MAX_TEXT_INPUT_LENGTH) {
                    return "";
                }
                return source;
            }
        };
        mFilterEdit.setFilters(FilterArray);
    }

    public int getCharacterNum(String str) {
        return (str == null || "".equals(str)) ? 0 : str.length();
    }

    /**
     * 设置输入的位数
     */
    private void filter1() {
        // 方法一:
        // 这种方法只能限制固定长度的字符数，也就是说MAX_TEXT_INPUT_LENGTH必须是一个定值。
        mFilterEdit
                .setFilters(new InputFilter[] { new InputFilter.LengthFilter(
                        MAX_TEXT_INPUT_LENGTH) });

    }

    private void initEvent() {
        mTouchEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 有时因为EditText过于灵敏，会Toast两次，加上ACTION_DOWN就可以保证只点击了一次
                    Toast.makeText(EditActivity.this, "Touch EidtText",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    @OnClick(R.id.btn_hide_input)
    void HideInput(View view) {
        hideSoftKeyboard(this, mHideEdit);
        // 始终不显示键盘
        // mHideEdit.setInputType(InputType.TYPE_NULL);
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    @OnClick(R.id.btn_input_after)
    void inputAfter(View view) {
        insertIntoEditText(mInputAfterEdit, new SpannableString(
                " Inserted Character"));
    }

    // 将ss插入到光标后，并自动将光标在最会显示
    private void insertIntoEditText(EditText editText, SpannableString ss) {
        Editable et = editText.getText();// 先获取Edittext中的内容
        int start = editText.getSelectionStart();// 获取光标所在位置
        et.insert(start, ss);// 设置ss要添加的位置
        editText.setText(et);// 把et添加到Edittext中
        editText.setSelection(start + ss.length());// 设置Edittext中光标在最后面显示
    }

    @OnClick(R.id.btn_input_type)
    void ChangeInputType(View view) {
        mInputTypeEdit.setInputType(InputType.TYPE_CLASS_TEXT);
    }

}