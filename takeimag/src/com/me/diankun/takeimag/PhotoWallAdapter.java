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
 * @Description: 注意：1. 每张图片开启一个线程去网络加载图片 2. 所有的请求存放在Set集合中，滑动时取消之前下载的图片 3.
 *               线程中加载图片后，移除此请求 4. 使用Set来管理所有的请求URL
 * @author: diankun
 * @date: 2016年2月29日 下午6:20:04
 */
public class PhotoWallAdapter extends BaseAdapter implements OnScrollListener {

	private Context mContext;
	private GridView mGridView;
	private LinkedList<String> mDatas;
	private LayoutInflater mInflater;
	// 内存缓存
	private LruCache<String, Bitmap> mLruCache;
	// 首次进入加载网络图片
	private boolean isFirstIn = true;

	// 页面第一个可见元素位置
	private int mFirstVisibleItem = 0;
	// 页面最后一个可见元素位置
	private int mLastVisibleItem = 0;

	private Set<ImageAsynTask> mTaskCollections = null;

	public PhotoWallAdapter(GridView gridView, LinkedList<String> datas, Context context) {

		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
		// LruCache通过构造函数传入缓存值，以KB为单位。
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		// Log.i("TAG", "maxMemory = " + maxMemory / 1024 / 1024 + "MB");
		// Log.i("TAG", "maxMemory/8 = " + maxMemory / 1024 / 1024 / 8 + "MB");
		mLruCache = new LruCache<String, Bitmap>(maxMemory / 6) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
		// 添加滑动监听
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
		// 从缓存中取出图片
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
	 * 将图片添加到LruCache
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		// 当前图片不在缓存中，加入到缓存中
		if (getBitmapFromCache(url) == null) {
			mLruCache.put(url, bitmap);
		}
	}

	/**
	 * 从LruCache中取出Bitmap
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
	 * 停止所有正在下载图片的线程
	 */
	private void cancleAllTask() {
		for (ImageAsynTask imageAsynTask : mTaskCollections) {
			imageAsynTask.cancel(false);
		}
	}

	/**
	 * 加载网络图片
	 */
	private void loadImage(int firstVisibleItem, int lastVisibleItem) {

		ImageAsynTask imageAsynTask = null;
		// imageAsynTask.execute(firstVisibleItem);
		/// 每一张图片，先判断是否存在缓存中，不存在则使用线程去网络上下载图片
		for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
			String imgUrl = mDatas.get(i);
			// 从缓存中取出图片
			Bitmap imgBitmap = getBitmapFromCache(imgUrl);
			// 缓存中存在图片,查找到ImageView设置;缓存中不存在该图片，从网络上下载图片
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
		// 当前页第一个元素
		mFirstVisibleItem = firstVisibleItem;
		// 当前页最后一个元素
		mLastVisibleItem = mFirstVisibleItem + visibleItemCount - 1;

		// 首次加载图片
		if (isFirstIn && mLastVisibleItem > 0) {
			loadImage(mFirstVisibleItem, mLastVisibleItem);
			isFirstIn = false;
		}
	}

	/**
	 * 异步下载图片
	 * 
	 * @ClassName: ImageAsynTask
	 * @Description: 对每一张图片,开启一个线程从网上下载图片
	 * @date: 2016年2月29日 下午5:20:15
	 */
	class ImageAsynTask extends AsyncTask<String, Void, Bitmap> {

		// 图片地址
		private String imageUrl;

		public ImageAsynTask() {

		}

		@Override
		protected Bitmap doInBackground(String... params) {

			imageUrl = params[0];
			Bitmap imgBitmap = downloadImage(imageUrl);
			// 加入到缓存中
			addBitmapToCache(imageUrl, imgBitmap);
			return imgBitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// 查找对应的ImageView
			ImageView image = (ImageView) mGridView.findViewWithTag(imageUrl);
			if (image != null && result != null) {
				image.setImageBitmap(result);
			}
			mTaskCollections.remove(this);
		}

		/**
		 * 从网络上下载图片
		 * 
		 * @param imageUrl
		 *            图片URL地址
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
