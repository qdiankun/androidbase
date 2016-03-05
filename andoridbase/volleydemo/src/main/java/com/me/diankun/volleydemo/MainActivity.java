package com.me.diankun.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_volley_string;
    private Button btn_volley;
    private Button btn_imageloader_volley;
    private Button btn_gson_volley;

    private ImageView image;

    private static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        image = (ImageView) findViewById(R.id.image);

        btn_volley_string = (Button) findViewById(R.id.btn_volley_string);
        btn_volley = (Button) findViewById(R.id.btn_volley);
        btn_imageloader_volley = (Button) findViewById(R.id.btn_imageloader_volley);
        btn_gson_volley = (Button) findViewById(R.id.btn_gson_volley);

        btn_volley_string.setOnClickListener(this);
        btn_volley.setOnClickListener(this);
        btn_imageloader_volley.setOnClickListener(this);
        btn_gson_volley.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_volley_string:
                useVolleyString();
                break;
            case R.id.btn_volley:
                useVolley();
                break;

            case R.id.btn_imageloader_volley:
                useImageLoadVolley();
                break;
            case R.id.btn_gson_volley:

                break;
        }
    }

    private void useVolleyString() {
        StringRequest request = new
                StringRequest("http://ngup.gitcafe.io/2015/04/11/android%E5%B8%B8%E7%94%A8%E7%BD%91%E7%AB%99%E6%94%B6%E9%9B%86/",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        Log.i("TAG", "response = " + arg0);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.e("TAG", "error = " + arg0.getMessage());
            }
        });
        mRequestQueue.add(request);
    }


    protected void useVolley() {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://news-at.zhihu.com/api/4/start-image/1080*1776", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject obj) {
                Log.i("TAG", "response = " + obj.toString());
                String imgUrl = obj.optString("img");
                if (TextUtils.isEmpty(imgUrl))
                    return;
                ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(final Bitmap bitmap) {
                        image.setImageBitmap(bitmap);
                        int size = BitmapUtils.getBitmapSize(bitmap);
                        Log.i("TAG", "bitmapsize = " + size / 1024 + " KB");

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // 使用Base64将Bitmap转为String
                                final String bitmaptoString = Base64Utils.bitmaptoString(bitmap);
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        // tv_imgtostr.setText(bitmaptoString);
                                    }
                                });
                            }
                        }).start();
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "error = " + error.getMessage());
                    }
                });
                mRequestQueue.add(imageRequest);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.e("TAG", "error = " + arg0.getMessage());
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }


    protected void useImageLoadVolley() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://news-at.zhihu.com/api/4/start-image/1080*1776", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject obj) {
                Log.i("TAG", "response = " + obj.toString());
                String imgUrl = obj.optString("img");
                if (TextUtils.isEmpty(imgUrl))
                    return;
                // 1.创建RequestQueue
                // RequestQueue mRequestQueue =
                // Volley.newRequestQueue(this);
                // 2.创建ImageLoader
                ImageLoader imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
                // 3.创建ImageListener
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(image,
                        R.drawable.iconfont_defaultpic, R.drawable.iconfont_error);
                // 4.获取图片
                imageLoader.get(imgUrl, imageListener);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.e("TAG", "error = " + arg0.getMessage());
            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }

    private class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }


    protected void useGsonLoadVolley() {
        GsonRequest<Newest> newestRequest = new GsonRequest<Newest>("http://news-at.zhihu.com/api/4/news/latest",
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "error = " + error.getMessage());
                    }
                }, new Response.Listener<Newest>() {

            @Override
            public void onResponse(Newest newest) {
                // Log.i("TAG", "newest = " + newest.toString());
                List<Story> stories = newest.getStories();
                if (stories == null)
                    return;
                for (Story story : stories) {
                    Log.i("TAG", "story = " + story);
                }

            }
        }, Newest.class);
        mRequestQueue.add(newestRequest);
    }
}
