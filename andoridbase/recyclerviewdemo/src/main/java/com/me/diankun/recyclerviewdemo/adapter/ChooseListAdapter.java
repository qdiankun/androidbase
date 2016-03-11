package com.me.diankun.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.diankun.recyclerviewdemo.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diankun on 2016/3/11.
 */
public class ChooseListAdapter extends RecyclerView.Adapter<ChooseListAdapter.MyViewHolder> {

    private List<String> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    //点击监听
    private OnMainItemClickListener mOnMainItemClickListener;

    public interface OnMainItemClickListener {
        public void onMainItemClick(View view, int position);
    }

    public void setOnMainItemClickListener(OnMainItemClickListener mOnMainItemClickListener) {
        this.mOnMainItemClickListener = mOnMainItemClickListener;
    }

    public ChooseListAdapter(List<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item_main, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String title = mData.get(position);
        holder.tv_title.setText(title);
        //设置点击事件
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMainItemClickListener.onMainItemClick(holder.ll_item,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;

        @Bind(R.id.ll_item)
        LinearLayout ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
