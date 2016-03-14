package com.me.diankun.imagedemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.me.diankun.imagedemo.utils.ScreenUtils;

/**
 * Created by diankun on 2016/3/14.
 */
public class GetScreenActivity extends AppCompatActivity {

    private ImageView screen_image;
    private Button btn_get_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getscreen);

        screen_image = (ImageView) findViewById(R.id.screen_image);
        btn_get_screen = (Button) findViewById(R.id.btn_get_screen);

        btn_get_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenImage();
            }
        });
    }

    private void screenImage() {
        Bitmap bitmap = ScreenUtils.snapShotWithoutStatusBar(this);
        screen_image.setImageBitmap(bitmap);

    }
}
