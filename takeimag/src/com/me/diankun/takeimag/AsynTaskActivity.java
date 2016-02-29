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
			mProgressDialog = ProgressDialog.show(mContext, "download", "���ؽ���:" + downpercent + "% ");
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {
				while (true) {
					doDownload();
					// ���½���
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
		 * ���½���
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// �������ؽ���
			mProgressDialog.setMessage("���ؽ���:  " + values[0] + "% ");
		}

		@Override
		protected void onPostExecute(Boolean result) {
			mProgressDialog.dismiss();
			if (result) {
				Toast.makeText(mContext, "���سɹ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "����ʧ��", Toast.LENGTH_SHORT).show();
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
