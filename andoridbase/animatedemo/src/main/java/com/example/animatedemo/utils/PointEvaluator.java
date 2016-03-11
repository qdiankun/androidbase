package com.example.animatedemo.utils;

import android.animation.TypeEvaluator;

import com.example.animatedemo.view.Point;

/**
 * Created by diankun on 2016/3/11.
 */
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + (endPoint.getX() - startPoint.getX()) * fraction;
        float y = startPoint.getY() + (endPoint.getY() - startPoint.getY()) * fraction;
        return new Point(x, y);
    }
}
