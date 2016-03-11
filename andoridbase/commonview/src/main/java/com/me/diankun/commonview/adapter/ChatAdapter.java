package com.me.diankun.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.diankun.commonview.R;
import com.me.diankun.commonview.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Class: ChatAdapter
 * Description: 聊天的Adapter
 *
 * @author diankun
 * @date 2015/11/17  17:32
 */
public class ChatAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;


    public ChatAdapter(Context context, List<ChatMessage> datas) {
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
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.GET_MSG) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到当前的Item对应的bean
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            //根据类型，使用相应的布局文件
            if(getItemViewType(position) == 0)
            {
                convertView = mInflater.inflate(R.layout.listview_item_getmsg, parent,false);
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_get_msgdate);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.tv_get_msg);
            }else{
                convertView = mInflater.inflate(R.layout.listview_item_sendmsg, parent,false);
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_send_msgdate);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.tv_send_msg);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        //设置对应的数据到View中
        viewHolder.mDate.setText(sdf.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());
        return convertView;
    }

    private final class ViewHolder
    {
        TextView mMsg;
        TextView mDate;
    }
}
