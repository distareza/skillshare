package com.jpa.crud;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Demonstrate Read Operation on JPA, and demonstrate how persistance.xml can initiate to load pre-loaded insert statement data in META-INF/preload-query.sql
 * 
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 		
 *  	Run Preloaded Insert statement at start up to initiate data in META-INF/persistence.xml 
 * 		 	<property name="javax.persistence.sql-load-script-source" value="META-INF/preload-query.sql"/>
 * 
 *  2.	Look how the entities Book and Author is defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity Books3 with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Defined how to defined custom Column definition with @Column Annotation
 *  		check how the column name can be altered or adjust,
 *  		Column Field Type, Column length
 *  		specify the Floating point precision (max digit) and scale ( n digit after decimal )
 *   
 *  5. Demonstrate preloaded-query.sql is triggered when application is started
 *  
 *  6. Demonstrate how to retrieve a single record from its primary key
 *  
 *   7. Demonstrate how to retrieve a list of record with JPQL select query
 *   	Look how the query is made and call not using the "Table Name" instead it uses Class Name / Entity Name of the Entity
 *  
 *  
 */
public class ReadExample {
	
	/**
	 * Spring JPA will run following query
	 * 
	 *      create table book_read (
	 *      	id integer not null auto_increment,
	 *      	author_name VARCHAR(55) not null,
	 *      	price float,
	 *      	book_title varchar(255) not null,
	 *      	primary key (id)
	 *      )
	 *
	 *  	alter table book14 
	 *  		add constraint UK_7b6mhf3in8mqha0i47g2iq04w unique (book_title)	        
	 *
	 *	data will be inserted using script that describe in sql query statement in persistence.xml
	 *		<property name="javax.persistence.sql-load-script-source" value="META-INF/preload-query.sql"/>
	 *
	 *
	 * note : static class is necesary for inner class, static class not necessary if it is a normal class
	 */	
	@Entity(name = "MyBook")
	@Table(name = "book_read")
	public static class Book {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;

		@Column(name = "author_name", columnDefinition = "VARCHAR(55)", nullable = false)
		private String author;

		@Column(name = "book_title", unique = true, nullable = false, length = 255)
		private String title;
		
		@Column(precision = 7, scale = 4)
		private Float price;

		@Transient
		private boolean inStock;
		
		public Book() {
		}
		
		public Book(String title, String author, Float price) {
			this.title = title;
			this.author = author;
			this.price = price;
			this.inStock = false;
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

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		public boolean isInStock() {
			return inStock;
		}

		public void setInStock(boolean inStock) {
			this.inStock = inStock;
		}

		@Override
		public String toString() {
			return String.format("\n{ %d, %s, %s, %,.2f }\n", this.id, this.title, this.author, this.price);
		}

	}
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			
			Book bookOne = entityManager.find(Book.class, 221);
			System.out.println(bookOne);
			
			Book bookTwo = entityManager.find(Book.class, 251);
			System.out.println(bookTwo);
			
			Book bookThree = entityManager.find(Book.class, 281);
			System.out.println(bookThree);
			
			// JPSQL Select Query ( JPSQL != Query Select Statement )
			// Look how the query is made, the statement is not using the "Table Name" instead it uses Class Name / Entity Name of the Entity
			List<Book> books = entityManager.createQuery("SELECT b FROM MyBook b", Book.class).getResultList(); // <-- calling the Entity Name
			//List<Book> books = entityManager.createQuery("SELECT b FROM com.jpa.crud.ReadExample$Book b", Book.class).getResultList(); // <-- calling using Class Name
			System.out.println(books);
			
			
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
			ex.printStackTrace();
		} finally {

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new ReadExample().runMain();
	}
}
