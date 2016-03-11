package com.me.diankun.showinfodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.diankun.showinfodemo.R;
import com.me.diankun.showinfodemo.bean.ItemData;

import java.util.List;

/**
 * Created by diankun on 2016/3/11.
 */
public class CustomDialogAdapter  extends BaseAdapter {


    private List<ItemData> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public CustomDialogAdapter(List<ItemData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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

        ItemData itemData = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.custom_dialog_adapter, parent, false);
            viewHolder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
            viewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置值
        viewHolder.tv_item.setText(itemData.getTitle());
        viewHolder.iv_item.setImageResource(itemData.getImageId());

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_item;
        TextView tv_item;
    }

}
