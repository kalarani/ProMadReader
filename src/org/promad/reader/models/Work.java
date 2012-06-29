package org.promad.reader.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Work {
	private String title;
	private String author;
	private List<String> urls;

	public Work(String title, String author, JSONArray urls) {
		this.title = title;
		this.author = author;
		this.urls = new ArrayList<String>();
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public List<String> getUrls() {
		return urls;
	}
}
