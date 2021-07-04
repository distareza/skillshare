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

/**
 * Demonstrate how to utilize a sharing fields with @Embeddable Annotation
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  2.	Observe how the entities classes are defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity class with different strategy assigning primary key generator
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
 *  5.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *  
 *  6. Demonstrate Embeddable Entity for Peristent Fields
 *  		Embedded Annotation tells Hibernate that this is a complex object that is embedded with the Entity
 *  		And it should map the fields of this complex nested object to the columns of the Entity Table
 *  
 *  7. Demonstrate Sharing Embeddable Objects can be used with other entity 
 *  
 *  
 */
public class InsertExample14 {
	
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
	 *     create table publisher (
	 *        id integer not null auto_increment,
	 *         city varchar(255),
	 *         country varchar(255),
	 *         name varchar(255),
	 *         primary key (id)
	 *     )
	 *
	 */
	
	@Entity
	@Table(name = "publisher")
	public class Publisher {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@Embedded
		private Address address;

		public Publisher() {
		}
		
		public Publisher(String name) {
			this.name = name;
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

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}
		
	}
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 * 	create table author_book (
	 *       id integer not null auto_increment,
	 *        city varchar(255),
	 *        country varchar(255),
	 *        birth_date date,
	 *        author_name varchar(44),
	 *        primary key (id)
	 *    )
	 *  
	 *  alter table author_book 
	 *  	add constraint UK_gbk6pw1g8x97j2khr1ba6cywp unique (author_name) 
	 *
	 */	
	@Entity
	@Table(name = "author_book")
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

			Author firstAuthor = new Author("Gilad Barcha", new GregorianCalendar(1980,  1, 0, 14, 20, 56).getTime());
			Author secondAuthor = new Author("James Goshling", new GregorianCalendar(1975,  2, 0, 04, 10, 01).getTime());

			Address firstAddress = new Address("New York", "USA");
			Address secondAddress = new Address("Paris", "France");

			firstAuthor.setAddress(firstAddress);
			secondAuthor.setAddress(secondAddress);
			
			entityManager.persist(firstAuthor);
			entityManager.persist(secondAuthor);
			
			
			Publisher firstPublisher = new Publisher("Apress");
			firstPublisher.setAddress(firstAddress);
			Publisher secondPublisher = new Publisher("Manning");
			secondPublisher.setAddress(secondAddress);
			
			entityManager.persist(firstPublisher);
			entityManager.persist(secondPublisher);
			
			/**
			 * 
select * from publisher;
---------- ---------------------- ---------------- ----------------
id         city                   country          name            
---------- ---------------------- ---------------- ----------------
1          New York               USA              Apress          
2          Paris                  France           Manning         

select * from author_book;
---------- ---------------------- ---------------- ------------------------- -----------------------
id         city                   country          birth_date                author_name            
---------- ---------------------- ---------------- ------------------------- -----------------------
1          New York               USA              1980-01-31                Gilad Barcha           
2          Paris                  France           1975-02-28                James Goshling         
			 * 
			 */
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
		new InsertExample14().runMain();
	}
}
