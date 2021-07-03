package com.jpa.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Demonstrate How Table can be mapped in XML Entity Specification ( not using annotation on Entity Class) located on "META-INF/orm.xml" ,
 * also demonstrate read Operation on JPA, and demonstrate how persistance.xml can initiate a mapping the xml entity specification file
 * 
 * 
 * 	1.	Look how the table is mapped to entity define in META-INF/persistence.xml 
 * 				<mapping-file>META-INF/orm.xml</mapping-file>
    			<class>com.jpa.crud.ReadExample2$Book</class>
 * 	
 *  2. Demonstrate how to retrieve a list of record with JPQL select query
 *   	Look how the query is made and call not using the "Table Name" instead it uses Class Name / Entity Name of the Entity
 *  
 *  
 */
public class ReadExample2 {
	
	/**
	 * This entity Class is mapped with XML Entity Specification File "META-INF/orm.xml" , not using Entity Annotation
	 */
	public static class Book {
		
		private Integer id;

		private String author;

		private String title;
		
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

		@Override
		public String toString() {
			return String.format("\n{ %d, %s, %s, %,.2f }\n", this.id, this.title, this.author, this.price);
		}

	}
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ManualMapping_PUnit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			
			entityManager.getTransaction().begin();
			Book firstBook = new Book("The Java Language Specification", "Gilad Barcha", 99.99f);
			Book secondBook = new Book("The Java Language Specification Second Edition", "Gilad Barcha", 119.99f);
			Book thirdBook = new Book("Core Java Volume I", "Cay S.Horstmann", 59.99f);
			entityManager.persist(firstBook);
			entityManager.persist(secondBook);
			entityManager.persist(thirdBook);
			entityManager.getTransaction().commit();
			
			// JPSQL Select Query ( JPSQL != Query Select Statement )
			// Look how the query is made, the statement is not using the "Table Name" instead it uses Class Name / Entity Name of the Entity
			//List<Book> books = entityManager.createQuery("SELECT b FROM com.jpa.crud.ReadExample2$Book b", Book.class).getResultList(); // <-- using Class Name
			List<Book> books = entityManager.createQuery("SELECT b FROM MyBook2 b", Book.class).getResultList(); // <-- using Entity Name 
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
		new ReadExample2().runMain();
	}
}
