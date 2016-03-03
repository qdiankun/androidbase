package com.me.diankun.volleydemo;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class Base64Utils {
	/**
	 * 将bitmap转换成base64字符串,图片大小要求小于等于size
	 *
	 * @param bitmap
	 * @param size
	 * @return base64 字符串
	 */
	public static String bitmaptoString(Bitmap bitmap, int bitmapQuality, float size) {

		if (bitmap == null)
			return "";

		String string = null;
		byte[] byteData = null;

		// 读取图片大小，判断图片是否小于size
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
		long length = bStream.toByteArray().length / 1024;// 读出图片的kb大小
		if (length <= size) {
			byteData = bStream.toByteArray();
		} else {
			byteData = compressBitmap(bitmap, size);
		}
		string = Base64.encodeToString(byteData, Base64.DEFAULT);
		return string;
	}

	/**
	 * 将bitmap转换成base64字符串
	 *
	 * @param bitmap
	 * @param size
	 * @return base64 字符串
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
	 * 将base64转换成bitmap图片
	 *
	 * @param string
	 *            base64字符串
	 * @return bitmap
	 */
	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
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
	 * 将图片压缩到指定的大小
	 *
	 * @param bitmap
	 * @param size
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap bitmap, float size) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
		int quality = 100;
		Log.i("TAG", "------照片初始大小--------" + baos.toByteArray().length / 1024f);
		while ((baos.toByteArray().length / 1024f) > size) {
			quality -= 5;// 每次都减少5(如果这里-=10，有时候循环次数会提前结束)
			Log.i("TAG", "------quality--------" + quality);
			baos.reset();// 重置baos即清空baos
			if (quality <= 0) {
				break;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			Log.i("TAG", "------质量--------" + baos.toByteArray().length / 1024f);
		}
		byte[] byteData = baos.toByteArray();
		return byteData;
	}
}

