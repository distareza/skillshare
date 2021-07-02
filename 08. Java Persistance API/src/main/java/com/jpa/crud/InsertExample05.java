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
import javax.persistence.TableGenerator;

/**
 * Demonstrate how to execute insert entity Book and Author with Generated TABLE () as Sequence
 * 
 * 	1.	Look how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 *          <property name="javax.persistence.schema-generation.create-source" value="script"/>
 *          <property name="javax.persistence.schema-generation.create-script-source" value="META-INF/create-book.sql"/>
 *          <property name="javax.persistence.schema-generation.drop-source" value="script"/>
 *          <property name="javax.persistence.schema-generation.drop-script-source" value="META-INF/drop-book.sql"/>           
 * 		Look how the create sql and drop sql is executed every time the application is start
 * 		each line statement on the script is executed 1 at the time 
 * 
 *  2.	Look how the entities Book and Author is defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity Books3 with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4. Table Book and Author can uses same sequence generator for both table
 * 			Demonstrate multiple identity generator with existing sequence from defined table ("bookstore_table") 
 * 			and uses column "book_id", "author_id" as sequence value
 * 			bookstore_table is defined on META-INF/create-table.sql sript, uses script when starting application
 *  
 */
public class InsertExample05 {

	/**
	 * Demonstrate the entity that having generated value
	 * uses defined table ("bookstore_table") as sequence object and column "book_id" as sequence value 
	 * 
	 * @GeneratedValue
	 * 	strategy = GeneratedType.AUTO --> uses default sequence object (hibernate_sequence)  within your database to generate primary keys values , default
	 * 	strategy = GenerationType.IDENTITY --> uses auto incremented database column (not using sequence object), the efficient way (not optimum way)
	 *  strategy = GenerationType.SEQUENCE --> uses defined sequence object to generate primary keys values
	 *  	uses along with annotatioh @SequenceGenerator 
	 *  strategy = GenerationType.TABLE --> uses defined sequence on defined table object
	 *  	uses along with annotation @TableGenerator 
	 *
	 *
	 *	    create table bookstore_table (
	 *			gen_name varchar(255) not null,
	 *			gen_val bigint,
	 *			primary key (gen_name)
	 *		)
	 *
	 *    create table book05 (
	 *       id integer not null,
	 *        author varchar(255),
	 *        price float,
	 *        title varchar(255),
	 *        primary key (id)
	 *    )	 
	 *
	 */

	@Entity
	@Table(name = "book05")
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
		@TableGenerator(name = "bookstore_generator", table = "bookstore_table", pkColumnName = "gen_name", pkColumnValue = "book_id", valueColumnName = "gen_val", allocationSize = 10)
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "bookstore_generator")	
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
	 * Demonstrate multiple identity generator with existing sequence from defined table ("bookstore_table") 
	 * and uses column "author_id" as sequence value
	 * bookstore_table is defined on META-INF/create-table.sql sript, uses script when starting application
	 * 
	 *     create table author05 (
	 *        id integer not null,
	 *         birthDate datetime,
	 *         name varchar(255),
	 *         primary key (id)
	 *     )	  
	 * 
	 */

	@Entity
	@Table(name = "author05")
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
		@TableGenerator(name = "bookstore_generator", table= "bookstore_table", pkColumnName = "gen_name", pkColumnValue = "author_id", valueColumnName = "gen_val")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="bookstore_generator")
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
		new InsertExample05().runMain();
	}
}
