package com.me.diankun.takeimag;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @ClassName: PhotoWallAdapter
 * @Description: ע�⣺1. ÿ��ͼƬ����һ���߳�ȥ�������ͼƬ 2. ���е���������Set�����У�����ʱȡ��֮ǰ���ص�ͼƬ 3.
 *               �߳��м���ͼƬ���Ƴ������� 4. ʹ��Set���������е�����URL
 * @author: diankun
 * @date: 2016��2��29�� ����6:20:04
 */
public class PhotoWallAdapter extends BaseAdapter implements OnScrollListener {

	private Context mContext;
	private GridView mGridView;
	private LinkedList<String> mDatas;
	private LayoutInflater mInflater;
	// �ڴ滺��
	private LruCache<String, Bitmap> mLruCache;
	// �״ν����������ͼƬ
	private boolean isFirstIn = true;

	// ҳ���һ���ɼ�Ԫ��λ��
	private int mFirstVisibleItem = 0;
	// ҳ�����һ���ɼ�Ԫ��λ��
	private int mLastVisibleItem = 0;

	private Set<ImageAsynTask> mTaskCollections = null;

	public PhotoWallAdapter(GridView gridView, LinkedList<String> datas, Context context) {

		// ��ȡ�������ڴ�����ֵ��ʹ���ڴ泬�����ֵ������OutOfMemory�쳣��
		// LruCacheͨ�����캯�����뻺��ֵ����KBΪ��λ��
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		// Log.i("TAG", "maxMemory = " + maxMemory / 1024 / 1024 + "MB");
		// Log.i("TAG", "maxMemory/8 = " + maxMemory / 1024 / 1024 / 8 + "MB");
		mLruCache = new LruCache<String, Bitmap>(maxMemory / 6) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
		// ��ӻ�������
		this.mGridView = gridView;
		mGridView.setOnScrollListener(this);

		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = datas;
		this.mTaskCollections = new HashSet<>();

	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		String url = mDatas.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_photo, parent, false);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.image.setTag(url);
		// �ӻ�����ȡ��ͼƬ
		Bitmap bitmap = getBitmapFromCache(url);
		if (bitmap != null) {
			viewHolder.image.setImageBitmap(bitmap);
		} else {
			viewHolder.image.setImageResource(R.drawable.empty_photo);
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView image;
	}

	/**
	 * ��ͼƬ��ӵ�LruCache
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		// ��ǰͼƬ���ڻ����У����뵽������
		if (getBitmapFromCache(url) == null) {
			mLruCache.put(url, bitmap);
		}
	}

	/**
	 * ��LruCache��ȡ��Bitmap
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapFromCache(String url) {
		return mLruCache.get(url);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		// Log.i("TAG", "scrollState = " + scrollState);

		switch (scrollState) {
		// 2 The user had previously been scrolling using touch and had
		// performed a fling
		case SCROLL_STATE_FLING:
			cancleAllTask();
			break;
		// 0 The view is not scrolling
		case SCROLL_STATE_IDLE:
			loadImage(mFirstVisibleItem, mLastVisibleItem);
			break;
		// 1 The user is scrolling using touch, and their finger is still on the
		// screen
		case SCROLL_STATE_TOUCH_SCROLL:
			cancleAllTask();
			break;

		default:
			break;
		}

	}

	/**
	 * ֹͣ������������ͼƬ���߳�
	 */
	private void cancleAllTask() {
		for (ImageAsynTask imageAsynTask : mTaskCollections) {
			imageAsynTask.cancel(false);
		}
	}

	/**
	 * ��������ͼƬ
	 */
	private void loadImage(int firstVisibleItem, int lastVisibleItem) {

		ImageAsynTask imageAsynTask = null;
		// imageAsynTask.execute(firstVisibleItem);
		/// ÿһ��ͼƬ�����ж��Ƿ���ڻ����У���������ʹ���߳�ȥ����������ͼƬ
		for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
			String imgUrl = mDatas.get(i);
			// �ӻ�����ȡ��ͼƬ
			Bitmap imgBitmap = getBitmapFromCache(imgUrl);
			// �����д���ͼƬ,���ҵ�ImageView����;�����в����ڸ�ͼƬ��������������ͼƬ
			if (imgBitmap != null) {
				ImageView image = (ImageView) mGridView.findViewWithTag(imgUrl);
				image.setImageBitmap(imgBitmap);
			} else {
				imageAsynTask = new ImageAsynTask();
				imageAsynTask.execute(imgUrl);
				mTaskCollections.add(imageAsynTask);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// Log.i("TAG", "firstVisibleItem = " + firstVisibleItem + "\t
		// visibleItemCount= " + visibleItemCount
		// + "\t totalItemCount= " + totalItemCount);
		// ��ǰҳ��һ��Ԫ��
		mFirstVisibleItem = firstVisibleItem;
		// ��ǰҳ���һ��Ԫ��
		mLastVisibleItem = mFirstVisibleItem + visibleItemCount - 1;

		// �״μ���ͼƬ
		if (isFirstIn && mLastVisibleItem > 0) {
			loadImage(mFirstVisibleItem, mLastVisibleItem);
			isFirstIn = false;
		}
	}

	/**
	 * �첽����ͼƬ
	 * 
	 * @ClassName: ImageAsynTask
	 * @Description: ��ÿһ��ͼƬ,����һ���̴߳���������ͼƬ
	 * @date: 2016��2��29�� ����5:20:15
	 */
	class ImageAsynTask extends AsyncTask<String, Void, Bitmap> {

		// ͼƬ��ַ
		private String imageUrl;

		public ImageAsynTask() {

		}

		@Override
		protected Bitmap doInBackground(String... params) {

			imageUrl = params[0];
			Bitmap imgBitmap = downloadImage(imageUrl);
			// ���뵽������
			addBitmapToCache(imageUrl, imgBitmap);
			return imgBitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// ���Ҷ�Ӧ��ImageView
			ImageView image = (ImageView) mGridView.findViewWithTag(imageUrl);
			if (image != null && result != null) {
				image.setImageBitmap(result);
			}
			mTaskCollections.remove(this);
		}

		/**
		 * ������������ͼƬ
		 * 
		 * @param imageUrl
		 *            ͼƬURL��ַ
		 * @return
		 */
		private Bitmap downloadImage(String imageUrl) {

			Bitmap bitmap = null;
			HttpURLConnection conn = null;
			try {
				URL url = new URL(imageUrl);
				conn = (HttpURLConnection) url.openConnection();

				conn.setAllowUserInteraction(false);
				conn.setInstanceFollowRedirects(true);
				conn.setReadTimeout(5 * 1000);
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");

				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					bitmap = BitmapFactory.decodeStream(conn.getInputStream());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

	}

}
