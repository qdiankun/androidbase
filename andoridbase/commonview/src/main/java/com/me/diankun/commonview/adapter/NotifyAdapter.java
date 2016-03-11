package com.me.diankun.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.diankun.commonview.R;

import java.util.List;

/**
 * Class: NotifyAdapter
 * Description: ListView中动态添加Item
 *
 * @author diankun
 * @date 2015/11/17  15:45
 */
public class NotifyAdapter extends BaseAdapter {

    private List<String> mDatas;
    private LayoutInflater mInflater;

    public NotifyAdapter(List<String> datas, Context context) {
        this.mDatas = datas;
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

        String name = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_notify_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置值
        viewHolder.textView.setText(name);
        return convertView;
    }

    static final class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
