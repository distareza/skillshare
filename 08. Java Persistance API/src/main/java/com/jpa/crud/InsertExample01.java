package com.jpa.crud;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity 
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 		use drop-and-create only to implement testing environment
 * 
 *  2.	Observe the @Entity Annotation is defined for the class Entity, 
 *  	it will automatically create when the schema generation.table.action is set with one of this option "create", "update" or "drop-and-create"
 *  	"drop-and-create" : means drop and create the tables for the entities, it is good for "testing environment"
 *  	"create" : means create the tables for the entities (so it is assumed to not exist yet).
 *  	"update" : means create table for the entities if it is not exists in DB yet.
 *     
 *  3.	Observer how the entities StoreAsset is defining Table name with annotation @Table(name="")
 * 
 * 
 */
public class InsertExample01 {

	/**
	 * 
	    create table asset (
	       id integer not null,
	        name varchar(255),
	        price float,
	        type varchar(255),
	        primary key (id)
	    )	 
	 *
	 */
	
	@Entity
	@Table(name="asset")
	public class Asset {
		
		private Integer id;
		private String name;
		private String type;
		private Float price;

		public Asset() {
		}
		
		public Asset(Integer id, String name, String type, Float price) {
			this.id = id;
			this.name = name;
			this.type = type;
			this.price = price;
		}

		@Id
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

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
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
			    into
			        asset
			        (name, price, type, id) 
			    values
			        (?, ?, ?, ?)
			 * 
			 */
			Asset firstAsset = new Asset(1, "BookShelf", "Furniture", 99f);
			Asset secondAsset = new Asset(2, "LED TV 32 Inch", "Electronics", 229f);
			Asset thirdAsset = new Asset(3, "Bicycle", "Sport", 59f);

			entityManager.persist(firstAsset);
			entityManager.persist(secondAsset);
			entityManager.persist(thirdAsset);

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
		(new InsertExample01()).runMain();
	}

}
