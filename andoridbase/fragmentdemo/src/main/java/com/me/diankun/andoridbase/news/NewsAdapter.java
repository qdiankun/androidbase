package com.me.diankun.andoridbase.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.me.diankun.andoridbase.R;

import java.util.List;

/**
 * Created by diankun on 2016/3/3.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;

    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        News news = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.news_title = (TextView) convertView.findViewById(R.id.news_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置标题
        viewHolder.news_title.setText(news.getTitle());
        return convertView;
    }


    private static class ViewHolder {
        TextView news_title;
    }

}
