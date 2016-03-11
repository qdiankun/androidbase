package com.me.diankun.imagedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.me.diankun.imagedemo.utils.CameraUtils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diankun on 2016/3/3.
 */
public class TakePhotoActivity extends AppCompatActivity {

    private Button btn_take_photo;
    private Button btn_choose_photo;
    private Button btn_create_file;
    private ImageView image;

    //拍摄或裁剪图片的uri
    private Uri imageUri;
    //拍摄或裁剪图片的path
    private String imagePath;


    public static final int REQUEST_CODE_CAMERA = 0x001;
    public static final int REQUEST_CODE_PHOTO_KITKAT = 0x002;//4.4系统以上情况
    public static final int REQUEST_CODE_PHOTO_NORMAL = 0x003;//4.4以下的情况
    public static final int REQUEST_CODE_CROP = 0x004;

    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        sdf = new SimpleDateFormat("yyyyMMddhhmmss");

        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_choose_photo = (Button) findViewById(R.id.btn_choose_photo);
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
        btn_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }

    /**
     * 打开相册，使用Intent.ACTION_GET_CONTENT，打开的是最近的文档，4.4版本以上和以下要区分对待
     */
    private void choosePhoto() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            startActivityForResult(intent, REQUEST_CODE_PHOTO_KITKAT);
        } else {
            startActivityForResult(intent, REQUEST_CODE_PHOTO_NORMAL);
        }

    }

    /**
     * 创建文件夹，然后创建文件写入到文件内容
     */
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

    /**
     * 打开相机拍照
     */
    private void takePhoto() {
        imageUri = Uri.fromFile(generateImageFile());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA://拍照
                if (imageUri == null)
                    return;
                //Bitmap sourcebitmap = BitmapFactory.decodeFile(imagePath);
                //int sourceSize = BitmapUtils.getBitmapSize(sourcebitmap);
                //Log.i("TAG", "before sourceSize = " + sourceSize / 1024 + "KB");

                //图片指定大小
                //Bitmap samplebitmap = BitmapUtils.decodeSampledBitmapFromFilepath(imagePath, 720, 480);
                //int samplebitmapSize = BitmapUtils.getBitmapSize(samplebitmap);
                //Log.i("TAG", "before samplebitmapSize = " + samplebitmapSize / 1024 + "KB");

                //此时的输入和输出的uri定位同一个
                // 裁剪图片
                cropImage(imageUri, imageUri);
                break;
            case REQUEST_CODE_PHOTO_KITKAT://图库返回4.4系统以上情况
                if (data == null) return;
                //获取图片所在图片库的真实路径
                //String picturePath = CameraUtils.getPath(TakePhotoActivity.this, data.getData());
                //将此图片拷贝到自己创建的文件夹下
                //FileUtils.copyFile(new File(picturePath), generateImageFile());
                //imageUri = Uri.fromFile(new File(picturePath));

                //输出的Uri
                imageUri = Uri.fromFile(generateImageFile());
                //源uri
                String picturePath = CameraUtils.getPath(TakePhotoActivity.this, data.getData());
                Uri souurceUri = Uri.fromFile(new File(picturePath));
                cropImage(souurceUri, imageUri);
                break;
            case REQUEST_CODE_PHOTO_NORMAL://图库返回4.4以下的情况
                if (data == null) return;
                //输出的Uri
                imageUri = Uri.fromFile(generateImageFile());
                cropImage(data.getData(), imageUri);
                break;
            case REQUEST_CODE_CROP:// 裁剪后返回
                if (imageUri == null)
                    return;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                if (bitmap == null)
                    return;
                //int bitmapSize = BitmapUtils.getBitmapSize(bitmap);
                //Log.i("TAG", "bitmapSize = " + bitmapSize / 1024 + "KB");

                // Bitmap samplebitmap =
                // BitmapUtils.decodeSampledBitmapFromFilepath(imagePath, 200, 200);
                // int samplebitmapSize = BitmapUtils.getBitmapSize(samplebitmap);
                // Log.i("TAG", "samplebitmapSize = " + samplebitmapSize / 1024 +
                // "KB");

                image.setImageBitmap(bitmap);
                // photoview.setImageBitmap(bitmap);
                break;
        }
    }


    /**
     * 裁剪图片
     *
     * @param sourceUri 原图片图片uri
     * @param outUri    裁剪后图片uri
     */
    private void cropImage(Uri sourceUri, Uri outUri) {
        // 如果来自相片，指定输出的图片文件
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪后生成图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }


    private File generateImageFile() {
        // 创建文件夹
        String floderPath = Environment.getExternalStorageDirectory() + File.separator + "take";
        File floderFile = new File(floderPath);
        if (!floderFile.exists()) {
            floderFile.mkdirs();
        }
        // 创建文件
        imagePath = floderFile.getAbsolutePath() + File.separator + sdf.format(new Date(System.currentTimeMillis())) + ".jpg";
        File imageFile = new File(imagePath);
        return imageFile;
    }
}
