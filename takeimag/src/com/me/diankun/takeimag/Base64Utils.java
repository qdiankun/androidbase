package com.me.diankun.takeimag;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class Base64Utils {
	/**
	 * ��bitmapת����base64�ַ���,ͼƬ��СҪ��С�ڵ���size
	 * 
	 * @param bitmap
	 * @param size
	 * @return base64 �ַ���
	 */
	public static String bitmaptoString(Bitmap bitmap, int bitmapQuality, float size) {

		if (bitmap == null)
			return "";

		String string = null;
		byte[] byteData = null;

		// ��ȡͼƬ��С���ж�ͼƬ�Ƿ�С��size
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
		long length = bStream.toByteArray().length / 1024;// ����ͼƬ��kb��С
		if (length <= size) {
			byteData = bStream.toByteArray();
		} else {
			byteData = compressBitmap(bitmap, size);
		}
		string = Base64.encodeToString(byteData, Base64.DEFAULT);
		return string;
	}

	/**
	 * ��bitmapת����base64�ַ���
	 * 
	 * @param bitmap
	 * @param size
	 * @return base64 �ַ���
	 */
	public static String bitmaptoString(Bitmap bitmap) {

		if (bitmap == null)
			return "";

		String encoded = null;
		byte[] byteData = null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		
		byte[] array = baos.toByteArray();
		encoded = Base64.encodeToString(array, Base64.DEFAULT);
		
		return encoded;
	}

	/**
	 * ��base64ת����bitmapͼƬ
	 * 
	 * @param string
	 *            base64�ַ���
	 * @return bitmap
	 */
	public static Bitmap stringtoBitmap(String string) {
		// ���ַ���ת����Bitmap����
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * ��ͼƬѹ����ָ���Ĵ�С
	 * 
	 * @param bitmap
	 * @param size
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap bitmap, float size) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ���ǩ����png�Ļ����򲻹�quality�Ƕ��٣����������������ѹ��
		int quality = 100;
		Log.i("TAG", "------��Ƭ��ʼ��С--------" + baos.toByteArray().length / 1024f);
		while ((baos.toByteArray().length / 1024f) > size) {
			quality -= 5;// ÿ�ζ�����5(�������-=10����ʱ��ѭ����������ǰ����)
			Log.i("TAG", "------quality--------" + quality);
			baos.reset();// ����baos�����baos
			if (quality <= 0) {
				break;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			Log.i("TAG", "------����--------" + baos.toByteArray().length / 1024f);
		}
		byte[] byteData = baos.toByteArray();
		return byteData;
	}
}
