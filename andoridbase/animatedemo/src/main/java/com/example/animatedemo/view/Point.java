package com.example.animatedemo.view;

/**
 * Class: Point
 * Description:自定义Point，用于实现动画
 *
 * @author diankun
 * @date 2015/11/2  15:47
 */
public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}