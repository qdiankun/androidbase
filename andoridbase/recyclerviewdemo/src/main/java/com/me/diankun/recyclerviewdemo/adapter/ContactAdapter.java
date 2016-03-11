package com.me.diankun.recyclerviewdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.me.diankun.recyclerviewdemo.bean.Contact;
import com.me.diankun.recyclerviewdemo.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diankun on 2016/1/19.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {


    // Store a member variable for the contacts
    private List<Contact> mContacts;

    // Pass in the contact array into the constructor
    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ContactHolder holder = new ContactHolder(contactView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.contact_name.setText(contact.getmName());
        if (contact.ismOnline()) {
            holder.message_button.setText("Message");
            holder.message_button.setEnabled(true);
        } else {
            holder.message_button.setText("OffLine");
            holder.message_button.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.contact_name)
        TextView contact_name;
        @Bind(R.id.message_button)
        Button message_button;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
