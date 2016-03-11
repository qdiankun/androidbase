package com.me.diankun.showinfodemo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.me.diankun.showinfodemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryDialog {

	private Dialog dialog;
	private Context context;
	private Activity ac;
	private int xxflag;
	private List<String> stringType = new ArrayList<String>();
	private HashMap<String, Object> resList = new HashMap<String, Object>();

	public QueryDialog(final Activity ac, int xxflag, final Context context) {
		this.ac = ac;
		this.xxflag = xxflag;
		this.context = context;
	}

	public void showWindow() {
		if (dialog == null) {
			LayoutInflater layoutInflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.zbdialog, null);
			TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title);
			dialog_title.setText("操作列表");
			stringType.clear();
			switch (xxflag) {
				case 0:
					stringType.add("详细信息");
					stringType.add("现实表现采集");
					break;
				case 1:
					stringType.add("详细信息");
					stringType.add("现实表现采集");
					stringType.add("活动轨迹");
					stringType.add("查看历史现实表现采集");
					stringType.add("加入布控");
					break;
				default:
					break;
			}
			ListView zb_list = (ListView) view.findViewById(R.id.zb_list);
			dialog = new Dialog(ac, R.style.dimdialog);
			ZbAdapter zbadapter = new ZbAdapter();
			zb_list.setAdapter(zbadapter);
			dialog.setContentView(view);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			zb_list.setOnItemClickListener(new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> a, View arg1, int arg2, long arg3) {

					switch (arg2) {
						// 重点人员详细信息
						case 0:

							break;
						case 1:

							break;
					}
					dialog.dismiss();
				}
			});
		}
	}

	class ZbAdapter extends BaseAdapter {
		ZbAdapter() {

		}

		@Override
		public int getCount() {
			return stringType.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(R.layout.zbtext, null);
				TextView zb_title = (TextView) convertView.findViewById(R.id.zb_title);
				zb_title.setText(stringType.get(position));
			}
			return convertView;
		}
	};

}
