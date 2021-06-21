package com.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.entities.Book;

public class InsertExample {

	public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
        EntityManager entityManager = factory.createEntityManager();
        
        entityManager.getTransaction().begin();
        
        Book firstBook = new Book(1234, "The Java Language Specification", "Gilad Barcha", 99);
        Book secondBook = new Book(2222, "The Java Language Specification Second Edition", "Gilad Barcha", 119);
        
        entityManager.persist(firstBook);
        entityManager.persist(secondBook);
        
        entityManager.getTransaction().commit();
        
        entityManager.clear();
        factory.close();

	}

}
