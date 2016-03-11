package com.me.diankun.imagedemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.me.diankun.imagedemo.utils.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetImageActivity extends Activity {

	private ProgressDialog progressDialog;
	private ImageView imageView;
	private Button button;
	private static final String img_url = "https://pic4.zhimg.com//7a71f7868e74af3930ecea71dced8925.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getimg);

		imageView = (ImageView) findViewById(R.id.imageView);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkInternetConenction())
					return;
				downloadImage(img_url);
			}
		});
	}

	private boolean checkInternetConenction() {
		// get Connectivity Manager object to check connection
		ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

		// Check for network connections
		if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

				connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
			Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
			return true;
		} else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
			Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
			return false;
		}
		return false;
	}

	private void downloadImage(final String urlImg) {

		progressDialog = ProgressDialog.show(this, "downimg", " 图片链接  = " + urlImg);

		// 开启线程下载图片
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 直接解析InputStrem得到Bitmap
				// InputStream is = openHttpConnection(url);
				// final Bitmap sourceBitmap = BitmapFactory.decodeStream(is);

				try {
					// 使用inSampleSize,得到压缩图片
					URL url = new URL(urlImg);
					final Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromStream(url, 500, 500);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (progressDialog.isShowing())
								progressDialog.dismiss();
							imageView.setImageBitmap(bitmap);
						}
					});
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private InputStream openHttpConnection(String url) {

		HttpURLConnection conn = null;
		InputStream is = null;
		try {

			URL urlNet = new URL(url);
			conn = (HttpURLConnection) urlNet.openConnection();

			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");

			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

}
