package com.jpa.crud;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book and Author with Composite Key
 * A composite primary key, also called a composite key, is a combination of two or more columns to form a primary key for a table.
 * 
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  2.	Look how the entities Book and Author is defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity Books with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Demonstrate How to assign a Composite Key
 *  	Composite Key Object should defined or overrides its hashCode and equals method
 *  		> Assign the Embedded Object (@Embeddable) as Composite Primary Key in Entity Object
 *  
 */
public class InsertExample06 {
	
	/**
	 * Demonstrate a Embedded Object as Composite Key to be assign in Entity
	 * require to override hashCode() and equals() 
	 * 
	 */
	@Embeddable
	public class BookKey implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Integer titleHash;
		private Float price;

		public BookKey() {
		}
		
		public BookKey(String title, Float price) {
			this.titleHash = Objects.hash(title);
			this.price = price;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public int hashCode() {
			return Objects.hash(titleHash, price);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (!(obj instanceof BookKey)) return false;
			BookKey other = (BookKey) obj;
			return titleHash.equals(other.titleHash) && price.equals(other.price);
		}
		
		
	}

	/**
	 * demonstrate how to embeded Composite Key into Entity as its primary key / identifier
	 * 
	 * Spring JPA will run following query 
	 *    create table book06 (
	 *       price float not null,
	 *        titleHash integer not null,
	 *        author varchar(255),
	 *        title varchar(255),
	 *        primary key (price, titleHash)
	 *    ) 	  
	 *
	 * primary key is a combination of the price and title hash
	 *
	 */	
	@Entity
	@Table(name = "book06")
	public class Book {
		
		@Id
		private BookKey bookKey;
		
		private String title;
		private String author;

		public Book() {
		}
		
		public Book(String title, String author, Float price) {
			this.bookKey = new BookKey(title, price);
			this.title = title;
			this.author = author;
		}

		public BookKey getBookKey() {
			return bookKey;
		}

		public void setBookKey(BookKey bookKey) {
			this.bookKey = bookKey;
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

	}
	
	/*
	 * 
	 *    create table author06 (
	 *       id integer not null auto_increment,
	 *        birthDate datetime,
	 *        name varchar(255),
	 *        primary key (id)
	 *    )	  
	 * 
	 */
	@Entity
	@Table(name = "author06")
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
		@GeneratedValue(strategy = GenerationType.IDENTITY)
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
			Book thridBook = new Book("Core Java Volume I", "Cay S. Horstmann", 59f);

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
		new InsertExample06().runMain();
	}
}
