package com.me.diankun.commonview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.me.diankun.commonview.service.UpdateTextService;
import com.me.diankun.commonview.utils.ToastUtils;

import org.xml.sax.XMLReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/11.
 */
public class TextViewActivity extends AppCompatActivity {

    @Bind(R.id.tv_html_local_img)
    TextView tv_html_local_img;

    @Bind(R.id.tv_html_net_img)
    TextView tv_html_net_img;

    @Bind(R.id.tv_use_html)
    TextView tv_use_html;

    @Bind(R.id.tv_use_span)
    TextView tv_use_span;

    //后台通过广播更新UI,UI也通过广播停止后台
    @Bind(R.id.tv_get_service_data)
    TextView tv_get_service_data;

    private MyBoardReceiver receiver;
    private Intent serviceIntent;

    private static final String TAG = "TextViewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        ButterKnife.bind(this);

        //使用Html来显示TextView
        useHtmlShow();

        //使用Html来显示TextView中包含本地图片
        showImgLocalHtml();

        //使用Html来显示TextView中包含网络图片
        showImgNetHtml();

        //使用Span元素来显示TextView
        useSpan();

        //注册广播，用于接收服务发送的广播
        IntentFilter filter = new IntentFilter("com.diankun.updateui");
        receiver = new MyBoardReceiver();
        registerReceiver(receiver, filter);

        //开启Servie更新UI
        serviceIntent = new Intent(TextViewActivity.this, UpdateTextService.class);
        startService(serviceIntent);
    }

    @OnClick(R.id.btn_stop_service)
    void stopUpdateUi(View view) {
        //给UpadteTextService发送广播，让其停止更新
        Intent intent = new Intent("com.diankun.stopservice");
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unregisterReceiver(receiver);
        //手动关闭服务
        stopService(serviceIntent);
    }

    class MyBoardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) return;
            String result = intent.getStringExtra("data");
            result = TextUtils.isEmpty(result) ? "no result" : result;
            Log.i("TAG", "接收数据 : " + result);
            tv_get_service_data.setText(result);
        }
    }

    private void useSpan() {
        SpannableStringBuilder builder = new SpannableStringBuilder("南通天气又变凉了， 要多穿点衣服,查话费的话打：10086");
        //要添加图片的Span
        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_launcher);
        //点击的Span
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showShort("clickableSpan clicked");
            }
        };
        //添加图片的Span
        builder.setSpan(imageSpan, 8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //添加点击的Span
        builder.setSpan(clickableSpan, 10, 15, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_use_span.setText(builder);
    }

    private void useHtmlShow() {

        String htmlStr = "<html><h2>南通</h2>天气又变凉了无语，要多穿点衣服,查话费的话打：10086<html>";
        //标签
        Html.TagHandler tagHandler = new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                //ToastUtils.showShort("TAG : " + tag);
            }
        };
        tv_use_html.setText(Html.fromHtml(htmlStr, null, tagHandler));
    }

    /**
     * 使用Html.formHtml()加载网络的图片
     * <p/>
     * http://stackoverflow.com/questions/16179285/html-imagegetter-textview?answertab=votes#tab-top
     */
    private void showImgNetHtml() {
        String source = "this is a test of <b>ImageGetter</b> it contains " +
                "two images: <br/>" +
                "<img src=\"http://d.lanrentuku.com/down/png/1406/little_animal_64x64/little_animal_12.png\"><br/>and<br/>" +
                "<img src=\"http://d.lanrentuku.com/down/png/1406/little_animal_64x64/little_animal_14.png\">";

        Html.ImageGetter imageGetter = new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = getResources().getDrawable(R.mipmap.ic_launcher);
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                new LoadImage().execute(source, d);

                return d;
            }
        };
        Spanned spanned = Html.fromHtml(source, imageGetter, null);
        tv_html_net_img.setText(spanned);
    }

    /**
     * 使用Html.formHtml()加载本地的图片
     * <p/>
     * http://stackoverflow.com/questions/16396462/android-textview-settext-in-html-fromhtml-to-display-image-and-text
     */
    private void showImgLocalHtml() {


        String code = "<p><b>First, </b><br/>" +
                "Please press the <img src ='account_balance.png'> button beside the to insert a new event.</p>" +
                "<p><b>Second,</b><br/>" +
                "Please insert the details of the event.</p>" +
                "<p>The icon of the is show the level of the event.<br/>" +
                "eg: <img src = 'account_circle_.png' > is easier to do.</p></td>";

        Html.ImageGetter imageGetter = new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {

                int id = 0;

                if (source.equals("account_balance.png")) {
                    id = R.drawable.account_balance;
                }

                if (source.equals("account_circle_.png")) {
                    id = R.drawable.account_circle_;
                }
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = getResources().getDrawable(id);
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                return d;
            }
        };
        //tv_content.setText(Html.fromHtml("<b>中国你好</b><h1>日本你不好</h1><font color='#00FF00'>苹果你好<font><img src='https://cdn4.iconfinder.com/data/icons/aami-flat-contact-us/64/contact-34-128.png'><img>"));

        Spanned spanned = Html.fromHtml(code, imageGetter, null);
        tv_html_local_img.setText(spanned);
        tv_html_local_img.setTextSize(16);

    }


    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tv_html_net_img.getText();
                tv_html_net_img.setText(t);
            }
        }
    }
}