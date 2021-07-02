package com.jpa.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Demonstrate Delete Operation on JPA, and demonstrate how persistance.xml can initiate to load pre-loaded insert statement data in META-INF/preload-query.sql
 * 
 *  1. Demonstrate how to retrieve a list of record with JPQL select query
 *   	Look how the query is made and call not using the "exact table name" instead it uses Class Name of the Entity
 *   
 *  2.  Demonstrate how to Delete a data using JPA,
 *  	notice that it required Transaction Begin and Commit
 *  
 *  
 */
public class RemoveExample {
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			
			ReadExample.Book bookOne = entityManager.find(ReadExample.Book.class, 221);
			System.out.println(bookOne);
			
			ReadExample.Book bookTwo = entityManager.find(ReadExample.Book.class, 251);
			System.out.println(bookTwo);
			
			ReadExample.Book bookThree = entityManager.find(ReadExample.Book.class, 281);
			System.out.println(bookThree);
			
			// JPSQL Select Query ( JPSQL != Query Select Statement )
			// Look how the query is made, the statement is not using the "exact table name" instead it uses Class Name of the Entity
			List<ReadExample.Book> books = entityManager.createQuery("SELECT b FROM ReadExample$Book b", ReadExample.Book.class).getResultList();
			System.out.println(books);
			
			// === Delete Operation === 
			entityManager.getTransaction().begin();
			if (bookOne != null) entityManager.remove(bookOne);
			if (bookTwo != null) entityManager.remove(bookTwo);
			entityManager.getTransaction().commit();
			
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
			ex.printStackTrace();
		} finally {

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new RemoveExample().runMain();
	}
}
