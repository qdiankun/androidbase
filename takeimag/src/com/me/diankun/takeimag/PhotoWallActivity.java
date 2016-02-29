package com.me.diankun.takeimag;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class PhotoWallActivity extends Activity {

	private GridView mGridView;
	private PhotoWallAdapter mAdapter;
	private LinkedList<String> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photowall);

		mDatas = new LinkedList<>(Arrays.asList(ImageUrls.IMAGE_URLS));
		mGridView = (GridView) findViewById(R.id.gridview);
		mAdapter = new PhotoWallAdapter(mGridView, mDatas, this);
		mGridView.setAdapter(mAdapter);
	}
}
