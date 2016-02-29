package com.me.diankun.takeimag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class AsynTaskActivity extends Activity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asyntask);
		mContext = this;

		ImageAsynTask task = new ImageAsynTask();
		task.execute();

	}

	class ImageAsynTask extends AsyncTask<Void, Integer, Boolean> {

		private ProgressDialog mProgressDialog;
		private int downpercent = 0;

		public ImageAsynTask() {

		}

		@Override
		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(mContext, "download", "下载进度:" + downpercent + "% ");
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {
				while (true) {
					doDownload();
					// 更新进度
					publishProgress(downpercent);

					if (downpercent >= 100) {
						break;
					}
				}
			} catch (Exception e) {
				return false;
			}
			return true;
		}

		/**
		 * 更新进度
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// 更新下载进度
			mProgressDialog.setMessage("下载进度:  " + values[0] + "% ");
		}

		@Override
		protected void onPostExecute(Boolean result) {
			mProgressDialog.dismiss();
			if (result) {
				Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
			}
		}

		public void doDownload() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			downpercent++;
		}

	}
}
