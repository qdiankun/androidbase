package com.me.diankun.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.diankun.commonview.R;

import java.util.List;

public class EditListAdapter extends BaseAdapter {

	private List<String> mDatas;
	private Context mContext;
	private LayoutInflater mInflater;

	public EditListAdapter(List<String> datas, Context context) {
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

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_edit_item,
					parent, false);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(mDatas.get(position));
		return convertView;
	}

	static class ViewHolder {
		TextView tv_name;
	}
}
