package com.me.diankun.toolbardemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.diankun.toolbardemo.R;

import java.util.List;

/**
 * Created by diankun on 2016/1/19.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleHolder> {


    // Store a member variable for the contacts
    private List<String> mData;

    // Pass in the contact array into the constructor
    public SimpleAdapter(List<String> data) {
        mData = data;
    }

    @Override
    public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        SimpleHolder holder = new SimpleHolder(contactView);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpleHolder holder, int position) {
        String name = mData.get(position);
        holder.contact_name.setText(name);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class SimpleHolder extends RecyclerView.ViewHolder {

        TextView contact_name;

        public SimpleHolder(View itemView) {
            super(itemView);
            contact_name = (TextView) itemView.findViewById(R.id.contact_name);
        }
    }
}
