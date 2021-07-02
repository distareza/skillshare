package com.jpa.crud;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book and Author with Composite Key using IdClass Annotation
 * A composite primary key, also called a composite key, is a combination of two or more columns to form a primary key for a table.
 * 
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
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
 *  
 */
public class InsertExample09 {
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 *      create table book09 (
	 *      	id integer not null auto_increment,
	 *      	author_name VARCHAR(55) not null,
	 *      	price float,
	 *      	book_title varchar(255) not null,
	 *      	primary key (id)
	 *      )
	 *
	 *  	alter table book09 
	 *  		add constraint UK_7b6mhf3in8mqha0i47g2iq04w unique (book_title)	        
	 *
	 */	
	@Entity
	@Table(name = "book09")
	public class Book {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;

		@Column(name = "book_title", unique = true, nullable = false, length = 255)
		private String title;
		
		@Column(name = "author_name", columnDefinition = "VARCHAR(55)", nullable = false)
		private String author;

		@Column(precision = 7, scale = 4)
		private Float price;

		public Book() {
		}
		
		public Book(String title, String author, Float price) {
			this.title = title;
			this.author = author;
			this.price = price;
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

		
	}
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 *	create table author09 (
	 *		id integer not null auto_increment,	
	 *		birth_date datetime, 
	 *		author_name VARCHAR(44),
	 *		primary key (id)
	 *  )
	 *  
	 *  alter table author09 
	 *  	add constraint UK_gbk6pw1g8x97j2khr1ba6cywp unique (author_name) 
	 *
	 */	
	@Entity
	@Table(name = "author09")
	public class Author {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "author_name", unique = true, length = 44)
		private String name;
		
		@Column(name = "birth_date")
		private Date birthDate;

		public Author() {
		}
		
		public Author(String name, Date birthDate) {
			this.name = name;
			this.birthDate = birthDate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}
		
	}	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			Book firstBook = new Book("The Java Language Specification", "Gilad Barcha", 99.99999f); // --> the precision and scale is 7 and 4, the value will be rounded to 100.0f in database
			Book secondBook = new Book("The Java Language Specification Second Edition", "Gilad Barcha", 119f);
			Book thridBook = new Book("Core Java Volume I", "Cay S. Horstmann", 59.9999f); // --> the precision and scale is 7 and 4, the value will be as is 59.9999f in database

			entityManager.persist(firstBook);
			entityManager.persist(secondBook);
			entityManager.persist(thridBook);
			
			Author firstAuthor = new Author("Gilad Barcha", new GregorianCalendar(1980,  1, 0).getTime());
			Author secondAuthor = new Author("James Goshling", new GregorianCalendar(1975,  2, 0).getTime());
			
			entityManager.persist(firstAuthor);
			entityManager.persist(secondAuthor);			

		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
			ex.printStackTrace();
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new InsertExample09().runMain();
	}
}
