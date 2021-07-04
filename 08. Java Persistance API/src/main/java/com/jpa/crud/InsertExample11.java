package com.jpa.crud;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
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
 * Demonstrate how to define a date time field type with @Temporal Annotation
 * 		TIMESTAMP (Default), DATE, TIME
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  2.	Observe how the entities class are defining Table name with annotation @Table(name="")
 *  
 *  3.	Observe how the GeneratedValue is define in entity Class with different strategy assigning primary key generator
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
 *  5.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *  
 */
public class InsertExample11 {
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 *	create table birthday (
	 *       id integer not null auto_increment,
	 *       birth_date date,
	 *       exactBirthDate datetime,
	 *       exactBirthTime time,
	 *       name varchar(44),
	 *       primary key (id)
	 *  )
	 *  
	 *  alter table birthday
	 *  	add constraint UK_gbk6pw1g8x97j2khr1ba6cywp unique (name) 
	 *
	 */	
	@Entity
	@Table(name = "birthday")
	public class Birthday {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "name", unique = true, length = 44)
		private String name;
		
		@Column(name = "birth_date")
		@Temporal(TemporalType.DATE)
		private Date birthDate;

		private Date exactBirthDate;
		
		@Temporal(TemporalType.TIME)
		private Date exactBirthTime;

		public Birthday() {
		}
		
		public Birthday(String name, Date birthDate) {
			this.name = name;
			this.birthDate = birthDate;
			
			this.exactBirthDate = birthDate;
			this.exactBirthTime = birthDate;
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

		public Date getExactBirthDate() {
			return exactBirthDate;
		}

		public void setExactBirthDate(Date exactBirthDate) {
			this.exactBirthDate = exactBirthDate;
		}

		public Date getExactBirthTime() {
			return exactBirthTime;
		}

		public void setExactBirthTime(Date exactBirthTime) {
			this.exactBirthTime = exactBirthTime;
		}
		
	}	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			Birthday firstBirth = new Birthday("Gilad Barcha", new GregorianCalendar(1980,  1, 0, 14, 20, 56).getTime());
			Birthday secondBirth = new Birthday("James Goshling", new GregorianCalendar(1975,  2, 0, 04, 10, 01).getTime());
			
			entityManager.persist(firstBirth);
			entityManager.persist(secondBirth);			
			
			/**
			 * 
id         birth_date                exactBirthDate            exactBirthTime            name                                         
---------- ------------------------- ------------------------- ------------------------- -------------------------------------------- 
1          1980-01-31                1980-01-31 14:20:56.0     14:20:56                  Gilad Barcha                                 
2          1975-02-28                1975-02-28 04:10:01.0     04:10:01                  James Goshling                               
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
		new InsertExample11().runMain();
	}
}
