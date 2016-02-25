package com.me.diankun.recyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2016/1/19.
 */
public class Contact {
    private String mName;
    private boolean mOnline;

    public Contact(String name, boolean online) {
        this.mName = name;
        this.mOnline = online;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmOnline(boolean mOnline) {
        this.mOnline = mOnline;
    }

    public String getmName() {
        return mName;
    }

    public boolean ismOnline() {
        return mOnline;
    }

    public static List<Contact> createContactList(int numberContact) {
        List<Contact> contactsList = new ArrayList<Contact>();

        Contact contact = null;
        for (int i = 0; i < numberContact; i++) {
            contact = new Contact("Person" + (i + 1), i <= numberContact / 2);
            contactsList.add(contact);
        }
        return contactsList;
    }
}
