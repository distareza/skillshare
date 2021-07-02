package com.jpa.crud;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
 *  5.	Demonstrate Transient Annotation, to exclude field in Entity Class from generated or mapped into Column for Generated Table in Database 
 *  
 *  6.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *  
 *  7. Demonstrate LOB Annotation for Large Object
 *  		uses of FecthType.LAZY means that the contents of the bio column will be lazily loaded when we explicitly use the getter or setter
 *  		Lob annotation is assign Large Object for the column
 *  
 *  8. Demonstrate Embeddable Entity for Peristent Fields
 *  		Embedded Annotation tells Hibernate that this is a complex object that is embedded with the Entity
 *  		And it should map the fields of this complex nested object to the columns of the Entity Table
 *  
 *  
 */
public class InsertExample13 {
	
	@Embeddable
	public static class Address {
		
		private String city;
		private String country;

		public Address() {
		}
		
		public Address(String city, String country) {
			this.city = city;
			this.country = country;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
		
	}
	
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 *      create table book13 (
	 *      	id integer not null auto_increment,
	 *      	author_name VARCHAR(55) not null,
	 *      	price float,
	 *      	book_title varchar(255) not null,
	 *      	primary key (id)
	 *      )
	 *
	 *  	alter table book13 
	 *  		add constraint UK_7b6mhf3in8mqha0i47g2iq04w unique (book_title)	        
	 *
	 */	
	@Entity
	@Table(name = "book13")
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

		
	}
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 * 	create table author13 (
	 *       id integer not null auto_increment,
	 *        city varchar(255),
	 *        country varchar(255),
	 *        birth_date date,
	 *        author_name varchar(44),
	 *        primary key (id)
	 *    )
	 *  
	 *  alter table author13 
	 *  	add constraint UK_gbk6pw1g8x97j2khr1ba6cywp unique (author_name) 
	 *
	 */	
	@Entity
	@Table(name = "author13")
	public class Author {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "author_name", unique = true, length = 44)
		private String name;
		
		@Column(name = "birth_date")
		@Temporal(TemporalType.DATE)
		private Date birthDate;

		@Embedded
		private Address address;
		
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

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
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
			
			Author firstAuthor = new Author("Gilad Barcha", new GregorianCalendar(1980,  1, 0, 14, 20, 56).getTime());
			Author secondAuthor = new Author("James Goshling", new GregorianCalendar(1975,  2, 0, 04, 10, 01).getTime());

			Address firstAddress = new Address("New York", "USA");
			Address secAddress = new Address("Paris", "France");

			firstAuthor.setAddress(firstAddress);
			secondAuthor.setAddress(secAddress);
			
			entityManager.persist(firstAuthor);
			entityManager.persist(secondAuthor);
			
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
			ex.printStackTrace();
		} finally {

			entityManager.getTransaction().commit();

			entityManager.clear();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new InsertExample13().runMain();
	}
}
