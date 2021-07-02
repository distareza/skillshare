package com.jpa.crud;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 			the best way to implement testing environment
 *  2.	Look how the entities Books is defining Table name with annotation @Table(name="")
 * 
 * 
 */
public class InsertExample01 {

	@Entity
	@Table(name="book01")
	public class Books {
		
		private Integer id;
		private String title;
		private String author;
		private Float price;

		public Books() {
		}
		
		public Books(Integer id, String title, String author, Float price) {
			this.id = id;
			this.title = title;
			this.author = author;
			this.price = price;
		}

		@Id
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

			Books firstBook = new Books(1234, "The Java Language Specification", "Gilad Barcha", 99f);
			Books secondBook = new Books(2222, "The Java Language Specification Second Edition", "Gilad Barcha", 119f);
			Books thridBook = new Books();
			thridBook.setId(3331);

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
		(new InsertExample01()).runMain();
	}

}
