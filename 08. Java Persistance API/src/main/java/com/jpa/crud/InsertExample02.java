package com.jpa.crud;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity with Autogenerated PrimaryKey
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
 *  5.	Observe how the GeneratedValue is define in entity Car and the "hibernate_sequence" is automatically created 
 * 			demonstrated the entity that having generated value
 * 			@GeneratedValue(strategy = GenerationType.AUTO)
 *  
 * 
 */
public class InsertExample02 {

	/**
	 * 
Hibernate: 

	    create table car (
	       id integer not null,
	        model varchar(255),
	        name varchar(255),
	        price float,
	        primary key (id)
	    ) 
	    
	    create table hibernate_sequence (
	       next_val bigint
	    ) 
    
    	insert into hibernate_sequence values ( 1 )
    
	 *
	 */
	@Entity
	@Table(name="car")
	public class Cars {

		private Integer id;
		private String name;
		private String model;
		private Float price;
		
		public Cars() {
		}

		public Cars(String name, String model, Float price) {
			this.name = name;
			this.model = model;
			this.price = price;
		}

		@Id
		@GeneratedValue
		//@GeneratedValue(strategy = GenerationType.AUTO) // --> *default, uses sequence generator to generate primary key
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

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}
		
	}
	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			/**
			 *
			 * 
Hibernate: 
    select
        next_val as id_val 
    from
        hibernate_sequence for update
            
Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
        
Hibernate: 
    insert 
    into
        car
        (model, name, price, id) 
    values
        (?, ?, ?, ?)
        
			 * 
			 */
			Cars firstCar = new Cars("Toyota Corrola", "Sedan", 129266.99f);
			Cars secondCar = new Cars("Mitsubishi Xpender", "MPV", 90999.99f);
			Cars thridCar = new Cars("Perodua Ativa", "SUV", 72000f);

			entityManager.persist(firstCar);
			entityManager.persist(secondCar);
			entityManager.persist(thridCar);
			
			/**
			 * 
			select * from car;
			---------- --------------- ----------------------- ------------ 
			id         model           name                    price        
			---------- --------------- ----------------------- ------------ 
			1          Sedan           Toyota Corrola          129267.0     
			2          MPV             Mitsubishi Xpender      91000.0      
			3          SUV             Perodua Ativa           72000.0      
			 * 
			 */

			
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) {
		new InsertExample02().runMain();
	}
}
