package com.me.diankun.commonview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Class: FlexibleListView
 * Description: Androi群英传 自定义ListView实现弹性动画效果
 *
 * @author diankun
 * @date 2015/11/17  16:01
 */
public class FlexibleListView extends ListView {

    private static int mMAxOverDistance = 50;

    private Context mContext;

    public FlexibleListView(Context context) {
        this(context, null);
    }

    public FlexibleListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 满足分辨率的要求，通过屏幕的density计算值
     */
    private void initView() {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMAxOverDistance = (int) (density * mMAxOverDistance);
    }

    /**
     * 修改最后一个参数maxOverScrollY可以让ListView有弹性
     *
     * @param deltaX
     * @param deltaY
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMAxOverDistance, isTouchEvent);
    }
}
