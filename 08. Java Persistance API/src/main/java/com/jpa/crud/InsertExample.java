package com.jpa.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.entities.Book;

/**
 * Demonstrate how to execute insert entity Book
 * look how the table is drop and create back uses custom script that define in META-INF/persistence.xml
 * 
            <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
            <property name="javax.persistence.schema-generation.create-source" value="script"/>
            <property name="javax.persistence.schema-generation.create-script-source" value="META-INF/create-book.sql"/>
            <property name="javax.persistence.schema-generation.drop-source" value="script"/>
            <property name="javax.persistence.schema-generation.drop-script-source" value="META-INF/drop-book.sql"/> * 
 *
 */
public class InsertExample {

	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

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
