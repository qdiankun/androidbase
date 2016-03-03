package com.me.diankun.imagedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diankun on 2016/3/3.
 */
public class TakePhotoActivity extends AppCompatActivity {

    private Button btn_take_photo;
    private Button btn_create_file;
    private ImageView image;

    private Uri imageUri;
    private String imagePath;


    public static final int REQUEST_CODE_CAMERA = 0;
    public static final int REQUEST_CODE_CROP = 1;

    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        sdf = new SimpleDateFormat("yyyyMMddhhmmss");

        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_create_file = (Button) findViewById(R.id.btn_create_file);
        image = (ImageView) findViewById(R.id.image);
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        btn_create_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFloder();
            }
        });
    }

    protected void createFloder() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "takePhoto";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File txtFile = new File(path, "new.txt");
        try {
            FileWriter fw = new FileWriter(txtFile);
            fw.append("welcome to china");
            fw.flush();
            fw.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void takePhoto() {
        imageUri = Uri.fromFile(generateImage());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (imageUri == null)
                    return;
                Bitmap sourcebitmap = BitmapFactory.decodeFile(imagePath);
                int sourceSize = BitmapUtils.getBitmapSize(sourcebitmap);
                Log.i("TAG", "before sourceSize = " + sourceSize / 1024 + "KB");

                Bitmap samplebitmap = BitmapUtils.decodeSampledBitmapFromFilepath(imagePath, 720, 480);
                int samplebitmapSize = BitmapUtils.getBitmapSize(samplebitmap);
                Log.i("TAG", "before samplebitmapSize = " + samplebitmapSize / 1024 + "KB");

                image.setImageBitmap(samplebitmap);

                // 裁剪图片
                cropImage();
                break;
            case REQUEST_CODE_CROP:// 裁剪后返回
                if (imageUri == null)
                    return;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                if (bitmap == null)
                    return;
                int bitmapSize = BitmapUtils.getBitmapSize(bitmap);
                Log.i("TAG", "bitmapSize = " + bitmapSize / 1024 + "KB");

                // Bitmap samplebitmap =
                // BitmapUtils.decodeSampledBitmapFromFilepath(imagePath, 200, 200);
                // int samplebitmapSize = BitmapUtils.getBitmapSize(samplebitmap);
                // Log.i("TAG", "samplebitmapSize = " + samplebitmapSize / 1024 +
                // "KB");

                // image.setImageBitmap(samplebitmap);
                // photoview.setImageBitmap(bitmap);
                break;
        }
    }


    private void cropImage() {
        // 如果来自相片，指定输出的图片文件
        // if (isFromAlbum) imageUri = Uri.fromFile(getImageFile());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪后生成图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }


    private File generateImage() {
        Log.i("TAG", "Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());
        // 创建文件夹
        String floderPath = Environment.getExternalStorageDirectory() + File.separator + "take";
        File floderFile = new File(floderPath);
        if (!floderFile.exists()) {
            floderFile.mkdirs();
        }
        // 创建文件
        Log.i("TAG", "floderPath = " + floderPath);
        Log.d("TAG", "floderFile.getAbsolutePath() = " + floderFile.getAbsolutePath());

        imagePath = floderFile.getAbsolutePath() + File.separator + sdf.format(new Date(System.currentTimeMillis()))
                + ".jpg";
        File imageFile = new File(imagePath);
        return imageFile;
    }
}
