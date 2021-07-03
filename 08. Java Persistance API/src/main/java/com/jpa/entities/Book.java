package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * Entity Class should have :
 * 
 * 	1.	Entity Annotation on Class Definition or Defined in META-INF/persistence.xml
 * 
 * 	2.	If Entity Class is defined by annotation, always defined a Primary Key Column that map to a field ("id"), by having @Id Annotation
 * 
 *  3. the Default Constructor must be define
 *
 */

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
