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

/**
 * Demonstrate how to execute insert entity Class with Generate Auto incremental sequence
 * 
 * 	1.	Observer how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 		use drop-and-create only to implement testing environment
 * 
 *  2.	Observe the @Entity Annotation is defined for the class Entity, 
 *  	it will automatically create when the schema generation.table.action is set with one of this option "create", "update" or "drop-and-create"
 *  	"drop-and-create" : means drop and create the tables for the entities, it is good for "testing environment"
 *  	"create" : means create the tables for the entities (so it is assumed to not exist yet).
 *  	"update" : means create table for the entities if it is not exists in DB yet.
 *     
 *  3.	Observe how the entities Class is defining Table name with annotation @Table(name="")
 *  
 *  4.	observe that the annotation @Id is required to identified a Primary Key 
 *  
 *  5.	Observe how the GeneratedValue is define in entity Class with different strategy (IDENTITY) assigning primary key generator
 * 			demonstrated the entity that having generated value
 * 			@GeneratedValue(strategy = GenerationType.IDENTITY) uses auto incremented of table column 
 * 
 *  		> AUTO 		: uses sequence
 *  		> IDENTITY  : uses auto increment of each table. Each Table having different Identity auto increment value, it is very possive that multiple table can have same value
 *  
 * 
 */
public class InsertExample03 {
	
	/**
	 * Demonstrate the entity that having generated value
	 * @GeneratedValue
	 * 	strategy = GeneratedType.AUTO --> uses "default" sequence object (hibernate_sequence) within your database to generate primary keys values , default
	 * 	strategy = GenerationType.IDENTITY --> uses auto incremented table column (not using sequence object), the efficient way (not optimum way) 
	 *
	 * this will create a following table script
	 * 
Hibernate: 
    
    create table customers (
       id integer not null auto_increment,
        balance float,
        name varchar(255),
        origin varchar(255),
        primary key (id)
    ) engine=MyISAM
    
	 *    
	 *
	 */
	@Entity
	@Table(name = "customers")
	public class Customer {

		private Integer id;
		private String name;
		private String origin;
		private Float balance;
		
		public Customer() {
		}

		public Customer(String name, String origin, Float balance) {
			this.name = name;
			this.origin = origin;
			this.balance = balance;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) // --> uses auto incremented database column (not using sequence object)
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

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public Float getBalance() {
			return balance;
		}

		public void setBalance(Float balance) {
			this.balance = balance;
		}

	}

	/**
	 *     
Hibernate: 
    
    create table companies (
       id integer not null auto_increment,
        establishDate datetime,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM

	 *
	 */
	@Entity
	@Table(name = "companies")
	public class Company {
		
		private Integer id;
		private String name;
		private Date establishDate;

		public Company() {
		}
		
		public Company(String name, Date establishDate) {
			this.name = name;
			this.establishDate = establishDate;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) // --> uses auto incremented database column (not using sequence object)
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

		public Date getEstablishDate() {
			return establishDate;
		}

		public void setEstablishDate(Date establishDate) {
			this.establishDate = establishDate;
		}

	
	}
	
	

	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			/**
			 * 
				Hibernate: 
				    insert 
				    into
				        customers
				        (balance, name, origin) 
				    values
				        (?, ?, ?)
			 * 
			 */
			Customer firstCustomer = new Customer("Mike Nash", "California", 99f);
			Customer secondCustomer = new Customer("Lois Van Dome", "Vancouver", 119f);
			Customer thridCustomer = new Customer("Lee Haw Yat", "Shanghai", 89f);

			entityManager.persist(firstCustomer);
			entityManager.persist(secondCustomer);
			entityManager.persist(thridCustomer);
			
			/**
			 * 
				Hibernate: 
				    insert 
				    into
				        companies
				        (establishDate, name) 
				    values
				        (?, ?)
			 * 
			 */
			Company firstCompany = new Company("Gilad Barcha", new GregorianCalendar(1980,  1, 0).getTime());
			Company secondCompany = new Company("James Goshling", new GregorianCalendar(1975,  2, 0).getTime());
			
			entityManager.persist(firstCompany);
			entityManager.persist(secondCompany);			

		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) {
		new InsertExample03().runMain();
	}

}
