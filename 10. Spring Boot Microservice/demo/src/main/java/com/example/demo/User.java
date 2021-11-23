package com.example.demo;

public class User {

	private String name;
	private String blog;
	private String type;
	private String url;
	
	
	public User(String name, String blog, String type, String url) {
		this.name = name;
		this.blog = blog;
		this.type = type;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
}
