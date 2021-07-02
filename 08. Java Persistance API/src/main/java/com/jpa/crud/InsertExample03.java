package com.jpa.crud;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book and Author with Generate Auto incremental sequence
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 *  2.	Look how the entities Books3 is defining Table name with annotation @Table(name="")
 *  3.	Look how the GeneratedValue is define in entity Books3 with different strategy assigning primary key generator
 *  		> AUTO 		: uses sequence
 *  		> IDENTITY  : uses auto increment of each table
 * 
 */
public class InsertExample03 {
	
	/**
	 * Demonstrate the entity that having generated value
	 * @GeneratedValue
	 * 	strategy = GeneratedType.AUTO --> uses sequence object (hibernate_sequence)  within your database to generate primary keys values , default
	 * 	strategy = GenerationType.IDENTITY --> uses auto incremented database column (not using sequence object), the efficient way (not optimum way) 
	 *
	 * this will create a following table script
	 * 
	 *     create table book (
	 *       id integer not null auto_increment,
	 *       author varchar(255),
	 *       price float,
	 *       title varchar(255),
	 *       primary key (id)
	 *    )
	 *
	 */
	@Entity
	@Table(name = "book03")
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
		@GeneratedValue(strategy = GenerationType.IDENTITY) // --> uses auto incremented database column (not using sequence object)
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

	/**
	 *     create table author (
	 *       id integer not null auto_increment,
	 *       birthDate datetime,
	 *       name varchar(255),
	 *       primary key (id)
	 *     )
	 *
	 */
	@Entity
	@Table(name = "author03")
	public class Author {
		
		private Integer id;
		private String name;
		private Date birthDate;

		public Author() {
		}
		
		public Author(String name, Date birthDate) {
			this.name = name;
			this.birthDate = birthDate;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) // --> uses auto incremented database column (not using sequence object)
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

			Book firstBook = new Book("The Java Language Specification", "Gilad Barcha", 99f);
			Book secondBook = new Book("The Java Language Specification Second Edition", "Gilad Barcha", 119f);
			Book thridBook = new Book();

			entityManager.persist(firstBook);
			entityManager.persist(secondBook);
			entityManager.persist(thridBook);
			
			Author firstAuthor = new Author("Gilad Barcha", new GregorianCalendar(1980,  1, 0).getTime());
			Author secondAuthor = new Author("James Goshling", new GregorianCalendar(1975,  2, 0).getTime());
			
			entityManager.persist(firstAuthor);
			entityManager.persist(secondAuthor);			

		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) {
		new InsertExample03().runMain();
	}

}
