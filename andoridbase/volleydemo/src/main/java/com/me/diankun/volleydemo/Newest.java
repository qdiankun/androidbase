package com.me.diankun.volleydemo;

import java.util.List;

public class Newest {

	private String date;
	private List<Story> stories;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	@Override
	public String toString() {
		return "Newest [date=" + date + ", stories=" + stories + "]";
	}
	
}
