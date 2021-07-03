package com.jpa.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.entities.Book;

/**
 * Demonstrate how working with entity in JPA
 * 1.  Observe how the table are being created and drop uses custom script that define in META-INF/persistence.xml
 * 
            <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
            <property name="javax.persistence.schema-generation.create-source" value="script"/>
            <property name="javax.persistence.schema-generation.create-script-source" value="META-INF/create-book.sql"/>
            <property name="javax.persistence.schema-generation.drop-source" value="script"/>
            <property name="javax.persistence.schema-generation.drop-script-source" value="META-INF/drop-book.sql"/> * 
 *
 * 		observe how the create sql and drop sql is executed every time the application is start
 * 		each line statement on the script is executed 1 at the time 
 *
 *
 * 2. Observe how an Object can be define as Entity Class
 * 		
 * 		1.	Entity Annotation on Class Definition or Defined in META-INF/persistence.xml
 * 		2.	If Entity Class is defined by annotation, always defined a Primary Key Column that map to a field ("id"), by having @Id Annotation
 *  	3. the Default Constructor must be define 
 *   
 * 3. Observe how JPA auto generate a insert query statement
 * 
 */
public class InsertExample {

	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ExampleAutoCreateAndDropTable_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			/**
			 * JPA will generate Insert Statement based on Entity Class Definition (Book.class)
				Hibernate: 
				    insert 
				    into
				        Book
				        (author, price, title, id) 
				    values
				        (?, ?, ?, ?)
			 * 
			 */
			Book firstBook = new Book(1234, "The Java Language Specification", "Gilad Barcha", 99f);
			Book secondBook = new Book(2222, "The Java Language Specification Second Edition", "Gilad Barcha", 119f);

			entityManager.persist(firstBook);
			entityManager.persist(secondBook);

		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
		
	}
	
	public static void main(String[] args) {
		(new InsertExample()).runMain();
	}

}
