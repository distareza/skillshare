package com.jpa.crud;

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
 * Demonstrate how to exclude a column to map in an entity
 *  
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  2.	Observe how the entities Book and Author is defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity Books3 with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Observe how to defined custom Column definition with @Column Annotation
 *  		check how the column name can be altered or adjust,
 *  		Column Field Type, Column length
 *  		specify the Floating point precision (max digit) and scale ( n digit after decimal ) 
 *  
 *  5.	Demonstrate Transient Annotation to exclude field in Entity Class from generated or mapped into Column for Generated Table in Database 
 *  
 *  
 */
public class InsertExample10 {
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 *      create table cooking_book (
	 *      	id integer not null auto_increment,
	 *      	author_name VARCHAR(55) not null,
	 *      	price float,
	 *      	book_title varchar(255) not null,
	 *      	primary key (id)
	 *      )
	 *
	 *  	alter table book10 
	 *  		add constraint UK_7b6mhf3in8mqha0i47g2iq04w unique (book_title)	        
	 *
	 */	
	@Entity
	@Table(name = "cooking_book")
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
		private boolean inStock; // this field is not going to mapped into an table column
		
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
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			Book firstBook = new Book("Salt, Fat, Acid, Heat: Mastering the Elements of Good Cooking", "Samin Nosrat", 99.99999f); // --> the precision and scale is 7 and 4, the value will be rounded to 100.0f in database
			Book secondBook = new Book("The Joy of Cooking", "Irma S. Rombauer", 119f);
			Book thridBook = new Book("Mastering the Art of French Cooking", "Judith Jones", 59.9999f); // --> the precision and scale is 7 and 4, the value will be as is 59.9999f in database

			entityManager.persist(firstBook);
			entityManager.persist(secondBook);
			entityManager.persist(thridBook);
			
			/**
			 * 
id         author_name                price        book_title                                                               
---------- -------------------------- ------------ ------------------------------------------------------------------------ 
1          Samin Nosrat               100.0        Salt, Fat, Acid, Heat: Mastering the Elements of Good Cooking            
2          Irma S. Rombauer           119.0        The Joy of Cooking                                                       
3          Judith Jones               59.9999      Mastering the Art of French Cooking                                     

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
		new InsertExample10().runMain();
	}
}
