package com.me.diankun.commonview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.me.diankun.commonview.R;

import java.util.List;

public class GetviewAdapter extends BaseAdapter {

	private List<String> mDatas;
	private Context mContext;
	private LayoutInflater mInflater;

	public GetviewAdapter(List<String> datas, Context context) {
		this.mDatas = datas;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
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

		Log.i("GetviewAdapter", "position : " + position);

		String name = mDatas.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_getview_item,
					parent, false);
			viewHolder.tv_dzwm = (TextView) convertView
					.findViewById(R.id.tv_dzwm);
			viewHolder.rb_item = (RadioButton) convertView
					.findViewById(R.id.rb_item);
			viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_dzwm.setText(name);
		viewHolder.iv.setImageResource(R.drawable.inc);
		return convertView;
	}

	static class ViewHolder {
		TextView tv_dzwm;
		RadioButton rb_item;
		ImageView iv;
	}

}
