package com.example.model;


public class Tutorial {
	
	private long id;
	private String title;
	private String description;
	private boolean published;
	
	public Tutorial() {
		
	}
	
	public Tutorial(long id,String title,String description,boolean published) {
		super();
		this.id=id;
		this.title=title;
		this.description=description;
		this.published=published;
	}
	
	public Tutorial(String title,String description,boolean published) {
		super();
		this.title=title;
		this.description=description;
		this.published=published;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDesciption(String description) {
		this.description = description;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	@Override
	public String toString() {
		return ("id="+id+" , title="+title+", decc="+description+", published="+published);
	}

}
