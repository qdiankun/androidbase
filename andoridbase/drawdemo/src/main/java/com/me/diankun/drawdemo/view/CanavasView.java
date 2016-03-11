package com.me.diankun.drawdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.me.diankun.drawdemo.R;
import com.me.diankun.drawdemo.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2015/11/21.
 * <p/>
 * 注意：
 * 使用canvas.save()和canvas.restore()方法作用：下次绘图的时候使用的坐标系save之前的坐标系， 中间进行的平移等操作对下次操作无影响
 * <p/>
 * 注意绘制文本时对齐方式
 * <p/>
 * paint.setTextAlign()设置文本的对齐方式，该对齐方式是相对于绘制文本时的画笔的坐标来说的，在本例中，我们绘制文本时画笔在Canvas宽度的中间。在drawText()方法执行时，需要传入一个x和y坐标，假设该点为P点，P点表示我们从P点绘制文本。当对齐方式为Paint.Align.LEFT时，绘制的文本以P点为基准向左对齐，这是默认的对齐方式；当对齐方式为Paint.Align.CENTER时，绘制的文本以P点为基准居中对齐；当对齐方式为Paint.Align.RIGHT时，绘制的文本以P点为基准向右对齐。
 */
public class CanavasView  extends View {

    private static final String TAG = CanavasView.class.getName();

    private Paint mPaint;
    private float fontSize;

    //类型
    private DrawMode drawMode;

    private float density = getResources().getDisplayMetrics().density;

    private Bitmap bitmap;

    public CanavasView(Context context) {
        this(context, null);
    }

    public CanavasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanavasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        fontSize = getResources().getDimensionPixelSize(R.dimen.default_font_size);
        Log.i(TAG, "修改后字体大小: " + fontSize + "px");
        Log.i(TAG, "修改后字体大小: " + DisplayUtil.sp2px(context, R.dimen.default_font_size) + "px");

        initPaint();
    }

    private void initPaint() {
        //初始化Paint
        mPaint = new TextPaint();


        //paint的默认字体大小
        Log.i(TAG, "默认字体大小: " + mPaint.getTextSize() + "px");

        //mPaint的默认颜色
        Log.i(TAG, "默认颜色: " + Integer.toString(mPaint.getColor(), 16));

        //mPaint的默认style是FILL，即填充模式
        Log.i(TAG, "默认style: " + mPaint.getStyle().toString());

        //mPaint的默认cap是
        Log.i(TAG, "默认cap: " + mPaint.getStrokeCap().toString());

        //mPaint默认的strokeWidth
        Log.i(TAG, "默认strokeWidth: " + mPaint.getStrokeWidth() + "");

        //设置抗锯齿
        mPaint.setAntiAlias(true);

        //设置字体大小
        mPaint.setTextSize(fontSize);
    }


    public static enum DrawMode {
        UNKNOW(0),
        AXIS(1),
        ARGB(2),
        TEXT(3),
        POINT(4),
        LINE(5),
        RECT(6),
        CIRCLE(7),
        OVAL(8),
        ARC(9),
        PATH(10),
        BITMAP(11),
        PATHTO(12),
        ARCDEMO(13);


        private int value;

        private DrawMode(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }

        public static DrawMode valueOf(int i) {
            switch (i) {
                case 1:
                    return AXIS;
                case 2:
                    return ARGB;
                case 3:
                    return TEXT;
                case 4:
                    return POINT;
                case 5:
                    return LINE;
                case 6:
                    return RECT;
                case 7:
                    return CIRCLE;
                case 8:
                    return OVAL;
                case 9:
                    return ARC;
                case 10:
                    return PATH;
                case 11:
                    return BITMAP;
                case 12:
                    return PATHTO;
                case 13:
                    return ARCDEMO;
                default:
                    return UNKNOW;
            }

        }
    }

    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (drawMode) {
            case AXIS:
                drawAxis(canvas);
                break;
            case ARGB:
                drawArgb(canvas);
                break;
            case TEXT:
                drawText(canvas);
                break;
            case POINT:
                drawPoint(canvas);
            case LINE:
                drawLine(canvas);
                break;
            case RECT:
                drawRect(canvas);
                break;
            case CIRCLE:
                drawCircle(canvas);
                break;
            case OVAL:
                drawOval(canvas);
                break;
            case ARC:
                drawArc(canvas);
                break;
            case PATH:
                drawPath(canvas);
                break;
            case BITMAP:
                drawBitmap(canvas);
                break;
            case PATHTO:
                drawPathTo(canvas);
                break;
            case ARCDEMO:
                drawArcDemo(canvas);
                break;
        }
    }

    private void drawArcDemo(Canvas canvas) {


        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int count = 3;
        int circleHeight = canvasHeight / (count + 1);//将Canvas的高度均等分4份，实际用到的只有三份存放圆，其中的一份等分成三份，用作三个圆间的分隔间距
        int deltaY = circleHeight / count;

        RectF mRectF = new RectF();
        mRectF.left = (canvasWidth - circleHeight) / 2;//等分高度的距离
        mRectF.top = 0;
        mRectF.right = canvasWidth - mRectF.left;
        mRectF.bottom = circleHeight;

        //无填充绘制
        canvas.translate(0, deltaY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(mRectF, 0, 90, false, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(mRectF, 90, 90, false, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(mRectF, 180, 90, false, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(mRectF, 270, 90, false, mPaint);
        //canvas.drawRect(mRectF, mPaint2);

        //无填充绘制
        canvas.translate(0, deltaY + circleHeight);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40 * density);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(mRectF, 0, 90, false, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(mRectF, 90, 90, false, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(mRectF, 180, 90, false, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(mRectF, 270, 90, false, mPaint);


        //填充绘制
        canvas.translate(0, deltaY + circleHeight);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(mRectF, 0, 90, true, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(mRectF, 90, 90, true, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(mRectF, 180, 90, true, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(mRectF, 270, 90, true, mPaint);


        //无填充绘制
//        canvas.translate(0, deltaY + circleHeight);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(40 * density);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(mRectF, 0, 90, true, mPaint);
//        mPaint.setColor(Color.RED);
//        canvas.drawArc(mRectF, 90, 90, true, mPaint);
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawArc(mRectF, 180, 90, true, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawArc(mRectF, 270, 90, true, mPaint);
    }

    private void drawPathTo(Canvas canvas) {

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5 * density);

        Path path = new Path();
        RectF mRectF = new RectF(10, 10, 600, 600);
        path.arcTo(mRectF, 90, 90);
        canvas.drawPath(path, mPaint);


    }

    private void drawBitmap(Canvas canvas) {
        //如果bitmap不存在，那么就不执行下面的绘制代码
        if (bitmap == null) {
            return;
        }

        //直接完全绘制Bitmap
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        //绘制Bitmap中的一部分，对它拉伸；Rect定义了绘制Bitmap的那一部分
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = bitmap.getWidth();
        rect.bottom = (int) (bitmap.getHeight() * 0.33);

        float radio = (float) (rect.bottom - rect.top) / bitmap.getWidth();//要强制转换为float

        //dstRecF定义了要将绘制的Bitmap拉伸到哪里
        RectF destRectF = new RectF();
        destRectF.left = 0;
        destRectF.top = bitmap.getHeight();
        destRectF.right = canvas.getWidth();//宽度拉伸至canvas的宽度
        destRectF.bottom = destRectF.top + radio * (destRectF.right - destRectF.left);
        canvas.drawBitmap(bitmap, rect, destRectF, mPaint);


    }

    public void setBitmap(Bitmap bitmap) {
        releaseBitmap();
        this.bitmap = bitmap;
    }

    private void releaseBitmap() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    private void drawPath(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int deltaX = canvasWidth / 4;
        int deltaY = (int) (deltaX * 0.75);

        //设置线宽
        mPaint.setStrokeWidth(2 * density);
        mPaint.setColor(0xff8bc5ba);//设置画笔颜色

        /*-------------------使用Path绘制填充面-------------------*/
        //设置填充模式
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        //向Path中Arc
        RectF rectF = new RectF(0, 0, deltaX, deltaY);
        path.addArc(rectF, 0, 135);
        //向Path中添加Oval
        RectF ovalRect = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path.addOval(ovalRect, Path.Direction.CCW);
        //向Path中添加Circle
        path.addCircle((float) (deltaX * 2.5), deltaY / 2, deltaX / 2, Path.Direction.CCW);
        //向Path中添加Rect
        RectF rect = new RectF(3 * deltaX, 0, canvasWidth, deltaY);
        path.addRect(rect, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);


         /*-------------------用Path画线-------------------*/
        canvas.translate(0, 2 * deltaY);//画布向下移动deltaY
        mPaint.setStyle(Paint.Style.STROKE);//设置为绘制线
        Path path2 = path;
        canvas.drawPath(path2, mPaint);


         /*-------------------使用lineTo、arcTo、quadTo、cubicTo画线-------------------*/
        canvas.translate(0, 2 * deltaY);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path3 = new Path();
        List<Point> pointList = new ArrayList<>();
        //1.第一部分绘制直线
        path3.moveTo(0, 0);
        path3.lineTo(deltaX / 2, 0);//绘制线段
        pointList.add(new Point(0, 0));
        pointList.add(new Point(deltaX / 2, 0));
        //2.第二部分，绘制椭圆右上角的四分之一狐仙
        RectF rec = new RectF(0, 0, deltaX, deltaY);
        path3.arcTo(rec, 270, 90);
        pointList.add(new Point(deltaX, deltaY / 2));
        //3.第三部分，绘制椭圆左下角的四分之一狐仙   ????
        //注意，我们此处调用了path的moveTo方法，将画笔的移动到我们下一处要绘制arc的起点上
        path3.moveTo((float) (deltaX * 1.5), deltaY);
        RectF rectF2 = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path3.arcTo(rectF2, 90, 90);//绘制圆弧
        pointList.add(new Point((int) (deltaX * 1.5), deltaY));
        //4.第四部分，绘制二阶贝塞尔曲线
        //二阶贝塞尔曲线的起点就是当前画笔的位置，然后需要添加一个控制点，以及一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 1.5f, deltaY);
        //绘制二阶贝塞尔曲线
        path3.quadTo(deltaX * 2, 0, deltaX * 2.5f, deltaY / 2);
        pointList.add(new Point((int) (deltaX * 2.5), deltaY / 2));
        //5. 第五部分，绘制三阶贝塞尔曲线，三阶贝塞尔曲线的起点也是当前画笔的位置
        //其需要两个控制点，即比二阶贝赛尔曲线多一个控制点，最后也需要一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 2.5f, deltaY / 2);
        //绘制三阶贝塞尔曲线
        path3.cubicTo(deltaX * 3, 0, deltaX * 3.5f, 0, deltaX * 4, deltaY);
        pointList.add(new Point(deltaX * 4, deltaY));

        canvas.drawPath(path3, mPaint);
        //绘制Path连接点
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(0xff0000ff);
        for (Point p : pointList) {
            canvas.drawPoint(p.x, p.y, mPaint);
        }

    }

    /**
     * 设置Paint.Style设置是否填充，
     *
     * 参数useCenter如果为true，表示在绘制完弧之后，用椭圆的中心点连接弧上的起点和终点以闭合弧；如果值为false，表示在绘制完弧之后，弧的起点和终点直接连接，不经过椭圆的中心点。
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        int canvasWidht = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int count = 5;
        float ovalHeight = canvasHeight / (count + 1);
        float left = 10 * density;
        float top = 0;
        float right = canvasWidht - left;
        float bottom = ovalHeight;
        RectF rectF = new RectF(left, top, right, bottom);

        mPaint.setStrokeWidth(2 * density);//设置线宽
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔填充模式

        //使用canvas绘制完整的椭圆 oval
        canvas.translate(0, ovalHeight / count);
        canvas.drawArc(rectF, 0, 360, true, mPaint);//注意：第二个参数指的是扫过的角度

        //绘制椭圆的四分之一,起点是钟表的3点位置，从3点绘制到6点的位置
        canvas.translate(0, ovalHeight + ovalHeight / count);
        canvas.drawArc(rectF, 0, 90, true, mPaint);


        //绘制椭圆的四分之一,将useCenter设置为false
        canvas.translate(0, ovalHeight + ovalHeight / count);
        canvas.drawArc(rectF, 0, 90, false, mPaint);

        //绘制椭圆的四分之一，只绘制轮廓线
        canvas.translate(0, ovalHeight + ovalHeight / count);
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.drawArc(rectF, 0, 90, true, mPaint);

        //绘制带有轮廓线的椭圆的四分之一
        //1.先绘制椭圆的填充部分
        mPaint.setStyle(Paint.Style.FILL);
        canvas.translate(0, ovalHeight + ovalHeight / count);
        canvas.drawArc(rectF, 0, 90, true, mPaint);
        //2.再绘制椭圆的轮廓线部分
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff0000ff);//设置轮廓线条为蓝色
        canvas.drawArc(rectF, 0, 90, true, mPaint);
    }

    private void drawOval(Canvas canvas) {

        int canvasWidht = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int quarter = canvasHeight / 4;

        float left = 10 * density;
        float top = 0;
        float right = canvasWidht - left;
        float bottom = quarter;
        RectF rectF = new RectF(left, top, right, bottom);

        //绘制椭圆形轮廓线
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2 * density);
        mPaint.setColor(0xff8bc5ba);//设置线条颜色
        canvas.translate(0, quarter / 4);
        canvas.drawOval(rectF, mPaint);
        canvas.restore();

        //绘制椭圆形填充面
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.translate(0, quarter + quarter / 4 + 10 * density);
        canvas.drawOval(rectF, mPaint);
        canvas.restore();

        /**
         *画两个椭圆，形成轮廓线和填充色不同的效果
         */

        //绘制椭圆
        canvas.save();
        canvas.translate(0, 2 * quarter + quarter / 4 + 20 * density);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawOval(rectF, mPaint);
        //绘制椭圆外边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2 * density);
        mPaint.setColor(0xff0000ff);//设置线条颜色
        canvas.drawOval(rectF, mPaint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//默认绘图为填充模式
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int count = 3;
        int detaY = canvasHeight / count;
        int radius = detaY / 2;


        //绘制一个圆
        canvas.save();
        canvas.translate(0, radius);
        canvas.drawCircle(canvasWidth / 2, 0, radius, mPaint);
        canvas.restore();

        /**
         *绘制一个圆环，两种方法，方法一：绘制一个大圆，在绘制一个小圆覆盖中间部分 方法二：使用空心线，设置线宽为圆环的1/2
         */

        //方法一绘制圆环
        canvas.save();
        canvas.translate(0, 2 * radius + radius);
        // 绘制大圆
        canvas.drawCircle(canvasWidth / 2, 0, radius, mPaint);
        // 绘制小圆
        mPaint.setColor(0xffffffff);//将画笔设置为白色，画小圆
        int r = (int) (radius * 0.7);
        canvas.drawCircle(canvasWidth / 2, 0, r, mPaint);
        canvas.restore();


        //方法二绘制圆环
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStrokeWidth(10 * density);
        canvas.save();
        canvas.translate(0, 4 * radius + radius);
        canvas.drawCircle(canvasWidth / 2, 0, radius, mPaint);
        canvas.restore();

    }


    private void drawRect(Canvas canvas) {

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        //使用默认的颜色
        int left1 = 0;
        int top1 = 10;
        int right1 = canvasWidth / 3;
        int bottom1 = canvasHeight / 3;
        canvas.drawRect(left1, top1, right1, bottom1, mPaint);

        //修改画笔的颜色
        mPaint.setColor(0xff8bc5ba);//A:ff,R:8b,G:c5,B:ba
        int left2 = (canvasWidth / 3) * 2;
        int top2 = 10;
        int right2 = canvasWidth;
        int bottom2 = canvasHeight / 3;
        canvas.drawRect(left2, top2, right2, bottom2, mPaint);
    }

    private void drawLine(Canvas canvas) {

        mPaint.setStyle(Paint.Style.STROKE);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfWidth = canvasWidth / 2;
        int deltaY = canvasHeight / 5;
        int halfDelatY = deltaY / 2;
        float[] pts = {50, 0, halfWidth, halfDelatY, halfWidth, halfDelatY, canvasWidth - 50, 0};

        //绘制一条直线
        canvas.drawLine(10, 10, canvasWidth - 50, deltaY / 2, mPaint);

        //绘制一条折线
        canvas.save();
        canvas.translate(0, deltaY);
        canvas.drawLines(pts, mPaint);
        canvas.restore();

        //设置线宽
        mPaint.setStrokeWidth(10 * density);
        //输出默认Cap
        Paint.Cap defaultCap = mPaint.getStrokeCap();
        Log.i("DemoLog", "默认Cap:" + defaultCap);

        //使用BUTT作为Cap
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.save();
        canvas.translate(0, deltaY * 2);
        canvas.drawLine(50, 0, halfWidth, 0, mPaint);
        canvas.restore();

        //使用Round作为Cap
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.save();
        canvas.translate(0, deltaY * 2 + 20 * density);
        canvas.drawLine(50, 0, halfWidth, 0, mPaint);
        canvas.restore();

        //使用SQUARE作为Cap
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.save();
        canvas.translate(0, deltaY * 2 + 40 * density);
        canvas.drawLine(50, 0, halfWidth, 0, mPaint);
        canvas.restore();


        //修改为默认Cap
        mPaint.setStrokeCap(defaultCap);
    }

    private void drawPoint(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int x = canvasWidth / 2;
        int deltaY = canvasHeight / 3;
        int y = deltaY / 2;
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStrokeWidth(50 * density);//设置线宽，如果不设置线宽，无法绘制点

        //绘制Cap为BUTT的点
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(x, y, mPaint);

        //绘制Cap为ROUND的点
        canvas.translate(0, deltaY);//首先下移deltaY距离
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(x, y, mPaint);

        //绘制SQUARE的点
        canvas.translate(0, deltaY);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(x, y, mPaint);
    }

    private void drawText(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int halfCanvasWidth = canvasWidth / 2;
        float translateY = fontSize;

        //绘制正常文本
        canvas.save();
        canvas.translate(0, translateY);//只是这次有效果
        canvas.drawText("正常绘制文本", 0, 0, mPaint);
        canvas.restore();//使用canvas.save()和canvas.restore()方法作用：下次绘图的时候使用的坐标系save之前的坐标系， 中间进行的平移等操作对下次操作无影响
        //下移两倍字体大小的距离
        translateY += fontSize * 2;//此处移动的距离增加了，这里是为下次去服务的


        //绘制绿色文本
        mPaint.setColor(0xff00ff00);//设置字体为绿色
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("正常绘制文本", 0, 0, mPaint);
        canvas.restore();
        mPaint.setColor(0xff000000);//重新设置为黑色
        translateY += fontSize * 2;

        //设置左对齐
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("左对齐文本", 0, 0, mPaint);
        canvas.restore();
        translateY += fontSize * 2;

        //设置中对齐
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("中对齐文本", 0, 0, mPaint);
        canvas.restore();
        translateY += fontSize * 2;

        //设置右对齐
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("右对齐文本", 0, 0, mPaint);
        canvas.restore();
        mPaint.setTextAlign(Paint.Align.LEFT);//重新设置为左对齐
        translateY += fontSize * 2;

        //设置下划线
        mPaint.setUnderlineText(true);
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("下划线文本", 0, 0, mPaint);
        canvas.restore();
        //重新设置为无下划线
        mPaint.setUnderlineText(false);
        translateY += fontSize * 2;//向下移动的距离增加

        //绘制加粗文本
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("加粗了的文本", 0, 0, mPaint);
        canvas.restore();
        translateY += fontSize * 2;

        //文本绕绘制起点顺时针旋转
        canvas.save();
        canvas.translate(0, translateY);
        canvas.rotate(30);
        canvas.drawText("文本绕绘制起点旋转30度", 0, 0, mPaint);
        canvas.restore();


    }

    private void drawArgb(Canvas canvas) {

        canvas.drawARGB(255, 139, 197, 186);
    }

    private void drawAxis(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(6 * density);

        //绿色画X轴，蓝色Y轴

        //第一次绘制坐标轴
        mPaint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制X轴
        mPaint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制Y轴

        //绘制Cap为BUTT的点
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(100, 100, mPaint);


        //坐标系平移后，第二次绘制
        canvas.translate(canvasWidth / 4, canvasHeight / 4);
        mPaint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制X轴
        mPaint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制Y轴

        canvas.drawPoint(100, 100, mPaint);

        //再次平移坐标系，在此基础上旋转坐标，第三次绘制
        canvas.translate(canvasWidth / 4, canvasHeight / 4);
        canvas.rotate(30);//基于当前绘图坐标系的原点旋转坐标系
        mPaint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制X轴
        mPaint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制Y轴

        canvas.drawPoint(100, 100, mPaint);


    }
}