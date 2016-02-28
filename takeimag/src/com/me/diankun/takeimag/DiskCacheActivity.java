package com.me.diankun.takeimag;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import libcore.io.DiskLruCache;
import libcore.io.DiskLruCache.Snapshot;

public class DiskCacheActivity extends Activity implements View.OnClickListener {

	private Button btn_down_image;
	private Button btn_put_diskcache;
	private Button btn_get_diskcache;
	private Button btn_get_diskcache_size;

	private ImageView image;
	private DiskLruCache mDiskLruCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_diskcache);

		image = (ImageView) findViewById(R.id.image);
		btn_put_diskcache = (Button) findViewById(R.id.btn_put_diskcache);
		btn_get_diskcache = (Button) findViewById(R.id.btn_get_diskcache);
		btn_down_image = (Button) findViewById(R.id.btn_down_image);
		btn_get_diskcache_size = (Button) findViewById(R.id.btn_get_diskcache_size);

		btn_put_diskcache.setOnClickListener(this);
		btn_get_diskcache.setOnClickListener(this);
		btn_down_image.setOnClickListener(this);
		btn_get_diskcache_size.setOnClickListener(this);

		// ��ʼ��DiskCache
		File cacheDir = getDiskCacheDir(this, "bitmap");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		try {
			mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(this), 1, 10 * 1024 * 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_down_image:
			requestNetImage();
			break;
		case R.id.btn_put_diskcache:
			putToDiskCache();
			break;
		case R.id.btn_get_diskcache:
			getDiskCache();
			break;
		case R.id.btn_get_diskcache_size:
			getDiskCacheSize();
			break;
		}
	}

	/**
	 * ��ȡDiskCacheSize
	 */
	private void getDiskCacheSize() {
		int Size = (int) mDiskLruCache.size();
		Log.i("TAG", " Size = " + Size + " KB ");
	}

	/**
	 * ��DiskCache��ȡͼƬ
	 */
	protected void getDiskCache() {
		try {
			String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
			String key = hashKeyForDisk(imageUrl);
			Snapshot snapshot = mDiskLruCache.get(key);
			if (snapshot == null)
				return;
			// �õ�������
			InputStream inputStream = snapshot.getInputStream(0);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			image.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼƬ���뵽DiskCache��
	 */
	protected void putToDiskCache() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
					// ����key
					String key = hashKeyForDisk(imageUrl);
					// �õ�Editor
					DiskLruCache.Editor editor = mDiskLruCache.edit(key);
					if (editor != null) {
						// д�뻺��
						OutputStream outputStream = editor.newOutputStream(0);
						if (downloadUrlToStream(imageUrl, outputStream)) {
							editor.commit();
						} else {
							editor.abort();
						}
					}
					mDiskLruCache.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	protected void requestNetImage() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				final Bitmap bitmap = downloadUrlToBitmap(
						"http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						image.setImageBitmap(bitmap);
					}
				});
			}
		}).start();
	}

	public File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	public int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public boolean downloadUrlToStream(String urlStr, OutputStream os) {

		BufferedInputStream bis = null;
		HttpURLConnection connection = null;
		Bitmap bitmap = null;
		int len = 0;
		byte[] buff = new byte[1024];

		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			bis = new BufferedInputStream(connection.getInputStream());
			while ((len = bis.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
			return true;
			// BitmapFactory.decodeByteArray(data, offset, length)
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

			try {
				if (bis != null) {
					bis.close();
				}

				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Bitmap downloadUrlToBitmap(String urlStr) {

		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		HttpURLConnection connection = null;

		Bitmap bitmap = null;
		int len = 0;
		byte[] buff = new byte[1024];

		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			bis = new BufferedInputStream(connection.getInputStream());
			baos = new ByteArrayOutputStream();
			while ((len = bis.read(buff)) != -1) {
				baos.write(buff, 0, len);
			}
			byte[] byteArray = baos.toByteArray();
			int bytecount = byteArray.length / 1024;
			Log.i("TAG", " ͼƬ��С  bytecount = " + bytecount + " KB");
			bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

			// ����ʹ�����·���
			// bitmap = BitmapFactory.decodeStream(connection.getInputStream());

			String hashKeyForDisk = hashKeyForDisk("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
			Log.d("TAG", " MD5 KEY = " + hashKeyForDisk + " ");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

			try {
				if (bis != null) {
					bis.close();
				}

				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

}
