package com.me.diankun.volleydemo;

import java.util.List;

public class Story {

	private List<String> images;
	private int type;
	private int it;
	private String ga_prefix;
	private String title;

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIt() {
		return it;
	}

	public void setIt(int it) {
		this.it = it;
	}

	public String getGa_prefix() {
		return ga_prefix;
	}

	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Story [images=" + images + ", type=" + type + ", it=" + it + ", ga_prefix=" + ga_prefix + ", title="
				+ title + "]";
	}

}
