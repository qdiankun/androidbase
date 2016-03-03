package com.me.diankun.imagedemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtils {

	/**
	 * get Bitmap from uri
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap uri2Bitmap(Context context, Uri uri) {
		String path = getRealPathFromURI(context, uri);
		return BitmapFactory.decodeFile(path);
	}

	/**
	 * ���ظ�Bitmap�Ĵ�С
	 *
	 * @param bitmap
	 * @return bytesize
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return bitmap.getAllocationByteCount();
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		} else {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	}

	/**
	 * @param @param
	 *            contentURI
	 * @param @return
	 *            �趨�ļ�
	 * @return String contentURI��Ӧ��·��
	 * @Title: getRealPathFromURI
	 * @Description: TODO(���Uri�õ�sd���д�ŵľ���·��)
	 */
	public static String getRealPathFromURI(Context context, Uri contentURI) {
		String result = "";
		Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) {
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// ԴͼƬ�ĸ߶ȺͿ��
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// �����ʵ�ʿ�ߺ�Ŀ���ߵı���
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// ѡ���͸�����С�ı�����ΪinSampleSize��ֵ��������Ա�֤����ͼƬ�Ŀ�͸�
			// һ��������ڵ���Ŀ��Ŀ�͸ߡ�
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromFilepath(String path, int reqWidth, int reqHeight) {
		// ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// ����ͼƬ�Ŀ��
		Log.i("TAG", "outWidth = " + options.outWidth + " outHeight = " + options.outHeight);
		// �������涨��ķ�������inSampleSizeֵ
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * Decode and sample down a bitmap from resources to the requested width and
	 * height.
	 * 
	 * @param res
	 *            The resources object containing the image data
	 * @param resId
	 *            The resource id of the image data
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		// ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// �������涨��ķ�������inSampleSizeֵ
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * Decode and sample down a bitmap from Stream to the requested width and
	 * height. decodeStream�����ظ�����ͬһ����������inputStream��������������´�һ��
	 * 
	 * @param is
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 * @throws Exception
	 */
	public static Bitmap decodeSampledBitmapFromStream(URL url, int reqWidth, int reqHeight) throws Exception {
		// ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(conn.getInputStream(), null, options);
		// �ر�����
		if(conn!=null) conn.disconnect();
		// ���´�����
		conn = (HttpURLConnection) url.openConnection();

		// �������涨��ķ�������inSampleSizeֵ
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
		// �ر�����
		if(conn!=null) conn.disconnect();
		return bitmap;
	}

}
