package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by diankun on 2016/2/19.
 *
 * http://www.runoob.com/w3cnote/android-tutorial-radiobutton-checkbox.html
 *
 * 另外有一点要切记，要为每个RadioButton添加一个id，不然单选功能会生效！！！
 */
public class RadioCheckActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button btnpost,btn_fruit;

    private CheckBox checkbox_banana,checkbox_orange,checkbox_apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiocheck);

        bindView();

    }

    private void bindView() {
        btnpost = (Button) findViewById(R.id.btnpost);
        btnpost.setOnClickListener(this);

        checkbox_banana = (CheckBox) findViewById(R.id.checkbox_banana);
        checkbox_orange = (CheckBox) findViewById(R.id.checkbox_orange);
        checkbox_apple = (CheckBox) findViewById(R.id.checkbox_apple);
        checkbox_banana.setOnCheckedChangeListener(this);
        checkbox_orange.setOnCheckedChangeListener(this);
        checkbox_apple.setOnCheckedChangeListener(this);
        btn_fruit = (Button) findViewById(R.id.btn_fruit);
        btn_fruit.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked()) Toast.makeText(this,compoundButton.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnpost: //获取选中RadioButton值的两种方法
                //方法一： 通过监听事件
                //methodOne();
                //方法二： 遍历RadioGroup，判断是否checked()
                methodTwo();
                break;
            case R.id.btn_fruit:
                //方法一： 通过监听事件
                //方法二： 挨个判断是否checked();
                checkMethodTwo();
                break;
            default:
                break;
        }
    }

    private void checkMethodTwo() {
        String choose = "";
        if(checkbox_banana.isChecked())choose += checkbox_banana.getText().toString() + "";
        if(checkbox_orange.isChecked())choose += checkbox_orange.getText().toString() + "";
        if(checkbox_apple.isChecked())choose +=  checkbox_apple.getText().toString() + "";
        Toast.makeText(this,choose,Toast.LENGTH_SHORT).show();
    }

    private void methodTwo() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.isChecked()) {
                Toast.makeText(RadioCheckActivity.this, "按钮组值发生改变,你选了" + radioButton.getText(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void methodOne() {
        //获取选中值方法一
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radbtn = (RadioButton) findViewById(checkedId);
                Toast.makeText(RadioCheckActivity.this, "按钮组值发生改变,你选了" + radbtn.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
