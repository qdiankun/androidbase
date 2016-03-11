package com.me.diankun.showinfodemo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.diankun.showinfodemo.R;


/**
 * 自定义Dialog
 * Created by diankun on 2015/11/5.
 */
public class CustomDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private String mDescription;

    private ICustomDialogEventListener mListener;

    //定义点击回调接口
    public interface ICustomDialogEventListener {
        public void customDialogEvent(int valueYouWantToSendBackToTheActivity);
    }

    public CustomDialog(Context context, String description, ICustomDialogEventListener listener) {
        this(context, 0, description,listener);
    }

    public CustomDialog(Context context, int theme, String description, ICustomDialogEventListener listener) {
        //重新设置样式，使默认不显示标题,此处可以写死或者是通过调用CustomDialog时传递过来
        super(context, R.style.custom_dialog);
        this.mContext = context;
        this.mDescription = description;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_dialog, null);
        //设置描述
        TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_description.setText(mDescription);
        //注册图片的点击事件
        bindImageClick(view);
        this.setContentView(view);
    }

    private void bindImageClick(View layout) {
        ImageView img1 = (ImageView)layout.findViewById(R.id.dialog_image1);
        ImageView img2 = (ImageView)layout.findViewById(R.id.dialog_image2);
        ImageView img3 = (ImageView)layout.findViewById(R.id.dialog_image3);
        ImageView img4 = (ImageView)layout.findViewById(R.id.dialog_image4);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        int drawableID = -1;
        switch (id){
            case R.id.dialog_image1:
                drawableID = R.drawable.animal1;
                break;
            case R.id.dialog_image2:
                drawableID = R.drawable.animal2;
                break;
            case R.id.dialog_image3:
                drawableID = R.drawable.animal3;
                break;
            case R.id.dialog_image4:
                drawableID = R.drawable.animal4;
                break;
        }
        if (drawableID != -1) {
            mListener.customDialogEvent(drawableID);
        }
        dismiss();
    }

}
