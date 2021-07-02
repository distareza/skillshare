package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@SuppressWarnings("unused")
public class Book {

	@Id
	private Integer id;
	private String title;
	private String author;
	private Float price;

	public Book() {
	}
	
	public Book(Integer id, String title, String author, Float price) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
	}

}
