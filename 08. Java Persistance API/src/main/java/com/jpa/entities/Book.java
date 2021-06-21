package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	private int id;
	private String title;
	private String author;
	private float price;

	public Book() {
	}
	
	public Book(int id, String title, String author, float price) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
	}
	
	
}
