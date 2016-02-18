package com.me.diankun.imagedemo.weidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.me.diankun.imagedemo.ScreenSizeUtil;

/**
 * 自定义控件，用于显示宽度和ImageView相同，高度自适应的图片显示模式.
 * 除此之外，还添加了最大高度限制，若图片长度大于等于屏幕长度，则高度显示为屏幕的1/3
 * Created by zhaokaiqiang on 15/4/20.
 * Blog : http://blog.csdn.net/zhaokaiqiang1992/article/details/45306313
 */
public class ShowMaxImageView extends ImageView {

    private float mHeight = 0;

    public ShowMaxImageView(Context context) {
        super(context);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        getHeight(bm);
        super.setImageBitmap(bm);
        requestLayout();
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        if (resId > 0) {
            Bitmap bitmap = drawableToBitamp(ContextCompat.getDrawable(getContext(), resId));
            if(bitmap!=null) getHeight(bitmap);
        }
        super.setImageResource(resId);
        requestLayout();
        invalidate();
    }


    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            getHeight(drawableToBitamp(drawable));
        }
        super.setImageDrawable(drawable);
        requestLayout();
        invalidate();
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable == null) return null;
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    private void getHeight(Bitmap bm) {

        float bitmapHeight = bm.getHeight();
        float bitmapWidth = bm.getWidth();
        Log.i("ShowMaxImageVIew", "getWidth() = " + getWidth());
        if (bitmapWidth > 0 && bitmapHeight > 0) {
            float scale = getWidth() / bitmapWidth;
            if (scale != 0) {
                mHeight = scale * bitmapHeight;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeight != 0) {
            int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
            int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

            int resultHeight = (int) Math.max(sizeHeight, mHeight);

            //大于屏幕高度时，显示图片的1/3
            if (resultHeight > ScreenSizeUtil.getScreenWidth((Activity) getContext())) {
                resultHeight = ScreenSizeUtil.getScreenWidth((Activity) getContext()) / 3;
            }
            setMeasuredDimension(sizeWidth, resultHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
