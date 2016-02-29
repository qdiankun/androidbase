package com.me.diankun.takeimag;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import uk.co.senab.photoview.PhotoView;

public class MainActivity extends Activity {

	private ImageView image;
	private PhotoView photoview;
	private SimpleDateFormat sdf;
	private TextView tv_imgtostr;

	private Uri imageUri;
	private String imagePath;

	public static final int REQUEST_CODE_CAMERA = 0;
	public static final int REQUEST_CODE_CROP = 1;

	private static RequestQueue mRequestQueue = Volley.newRequestQueue(App.getContext());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		image = (ImageView) findViewById(R.id.image);
		photoview = (PhotoView) findViewById(R.id.photoview);
		tv_imgtostr = (TextView) findViewById(R.id.tv_imgtostr);
		sdf = new SimpleDateFormat("yyyyMMddhhmmss");

		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1021);
		Log.i("TAG", " maxMemory = " + maxMemory + " MB");

		initView();

	}

	private void initView() {
		((Button) findViewById(R.id.btn_take_photo)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				takePhoto();
			}
		});

		((Button) findViewById(R.id.btn_test)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				createFloder();
			}
		});

		((Button) findViewById(R.id.btn_volley)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				useVolley();
			}
		});

		((Button) findViewById(R.id.btn_imageloader_volley)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				useImageLoadVolley();
			}
		});

		((Button) findViewById(R.id.btn_gson_volley)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				useGsonLoadVolley();
			}
		});

		((Button) findViewById(R.id.btn_open_diskcache)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, DiskCacheActivity.class);
				startActivity(i);
			}
		});

		((Button) findViewById(R.id.btn_get_image)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, GetImageActivity.class);
				startActivity(i);
			}
		});

		((Button) findViewById(R.id.btn_photo_wall)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, PhotoWallActivity.class);
				startActivity(i);
			}
		});
	}

	protected void useGsonLoadVolley() {
		GsonRequest<Newest> newestRequest = new GsonRequest<Newest>("http://news-at.zhihu.com/api/4/news/latest",
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "error = " + error.getMessage());
					}
				}, new Listener<Newest>() {

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

	protected void useImageLoadVolley() {

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				"http://news-at.zhihu.com/api/4/start-image/1080*1776", null, new Listener<JSONObject>() {

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
						ImageListener imageListener = ImageLoader.getImageListener(image,
								R.drawable.iconfont_defaultpic, R.drawable.iconfont_error);
						// 4.获取图片
						imageLoader.get(imgUrl, imageListener);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("TAG", "error = " + arg0.getMessage());
					}
				});
		mRequestQueue.add(jsonObjectRequest);

	}

	private class BitmapCache implements ImageCache {

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

	protected void useVolley() {
		// StringRequest request = new
		// StringRequest("http://ngup.gitcafe.io/2015/04/11/android%E5%B8%B8%E7%94%A8%E7%BD%91%E7%AB%99%E6%94%B6%E9%9B%86/",
		// new Listener<String>() {
		//
		// @Override
		// public void onResponse(String arg0) {
		// Log.i("TAG", "response = "+arg0);
		// }
		// }, new ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError arg0) {
		// Log.e("TAG", "error = "+arg0.getMessage());
		// }
		// });

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				"http://news-at.zhihu.com/api/4/start-image/1080*1776", null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject obj) {
						Log.i("TAG", "response = " + obj.toString());
						String imgUrl = obj.optString("img");
						if (TextUtils.isEmpty(imgUrl))
							return;
						ImageRequest imageRequest = new ImageRequest(imgUrl, new Listener<Bitmap>() {

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
						}, 0, 0, Config.RGB_565, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								Log.e("TAG", "error = " + error.getMessage());
							}
						});
						mRequestQueue.add(imageRequest);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("TAG", "error = " + arg0.getMessage());
					}
				});
		mRequestQueue.add(jsonObjectRequest);
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
			photoview.setImageBitmap(sourcebitmap);

			// 裁剪图片
			// cropImage();
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

	private void zoomImage() {
		String path = getRealPathFromURI(imageUri);
	}

	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file
								// path
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	private void takePhoto() {
		imageUri = Uri.fromFile(generateImage());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
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
