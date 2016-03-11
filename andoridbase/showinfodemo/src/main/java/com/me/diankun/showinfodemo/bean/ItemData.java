package com.me.diankun.showinfodemo.bean;

/**
 * Created by diankun on 2016/3/11.
 */
public class ItemData {

    private String title;
    private int imageId;

    public ItemData(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "title='" + title + '\'' +
                ", imageId=" + imageId +
                '}';
    }

}
