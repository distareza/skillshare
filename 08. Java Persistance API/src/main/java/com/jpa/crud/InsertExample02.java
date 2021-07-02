package com.jpa.crud;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 *  2.	Look how the entities Books2 is defining Table name with annotation @Table(name="")
 *  3.	Look how the GeneratedValue is define in entity Books2 and the hibernate_sequence is automatically created 
 * 			demonstrated the entity that having generated value
 * 			@GeneratedValue(strategy = GenerationType.AUTO)
 *  
 * 
 */
public class InsertExample02 {

	@Entity
	@Table(name="book02")
	public class Book {

		private Integer id;
		private String title;
		private String author;
		private Float price;
		
		public Book() {
		}

		public Book(String title, String author, Float price) {
			this.title = title;
			this.author = author;
			this.price = price;
		}

		@Id
		@GeneratedValue
		//@GeneratedValue(strategy = GenerationType.AUTO) // --> uses sequence generator to generate primary key
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}
		
	}
	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			Book firstBook = new Book("The Java Language Specification", "Gilad Barcha", 99f);
			Book secondBook = new Book("The Java Language Specification Second Edition", "Gilad Barcha", 119f);
			Book thridBook = new Book();

			entityManager.persist(firstBook);
			entityManager.persist(secondBook);
			entityManager.persist(thridBook);

		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) {
		new InsertExample02().runMain();
	}
}
