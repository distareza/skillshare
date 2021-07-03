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
 *  5.	observe how the GeneratedValue is define in entity Class with different strategy (TABLE) assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  6. observe the "Sequence Generator" Table  can uses same sequence generator for multiple table
 *  
 *  7. Observe initial query has been added and defined in META-INF/create-table.sql sript, uses script when starting application
 *  
 */
public class InsertExample05 {

	/**
	 * Demonstrate the entity that having generated value
	 * uses defined table ("place_generator") as sequence object and column "seq_name" as sequence name , column "seq_value" as sequence value 
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
		Hibernate: 
		    
		    create table city (
		       id integer not null,
		        country varchar(255),
		        latitude float,
		        longitude float,
		        name varchar(255),
		        primary key (id)
		    ) engine=MyISAM
    
		Hibernate: 
		    
		    create table place_generator (
		       seq_name varchar(255) not null,
		        seq_val bigint,
		        primary key (seq_name)
		    ) engine=MyISAM
        
	 *    	 
	 *
	 */

	@Entity
	@Table(name = "city")
	public class City {
		
		private Integer id;
		private String name;
		private String country;
		private Float latitude;
		private Float longitude;

		public City() {
		}
		
		public City(String title, String country, Float latitude, Float longitude) {
			this.name = title;
			this.country = country;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		@Id
		@TableGenerator(name = "placeId_generator", table = "place_generator", pkColumnName = "seq_name", pkColumnValue = "city_next_id", valueColumnName = "seq_val", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "placeId_generator")	
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

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public Float getLatitude() {
			return latitude;
		}

		public void setLatitude(Float latitude) {
			this.latitude = latitude;
		}

		public Float getLongitude() {
			return longitude;
		}

		public void setLongitude(Float longitude) {
			this.longitude = longitude;
		}

	}
	
	/**
	 * Demonstrate multiple identity generator with existing sequence from defined table ("bookstore_table") 
	 * and uses column "author_id" as sequence value
	 * bookstore_table is defined on META-INF/create-table.sql sript, uses script when starting application
	 * 
Hibernate: 
    
    create table country (
       id integer not null,
        independenceDate datetime,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    
	 * 
	 */

	@Entity
	@Table(name = "country")
	public class Country {
		
		private Integer id;
		private String name;
		private Date independenceDate;

		public Country() {
		}
		
		public Country(String name, Date independenceDate) {
			this.name = name;
			this.independenceDate = independenceDate;
		}

		@Id
		@TableGenerator(name = "placeId_generator", table = "place_generator", pkColumnName = "seq_name", pkColumnValue = "country_next_id", valueColumnName = "seq_val", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "placeId_generator")	
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

		public Date getIndependenceDate() {
			return independenceDate;
		}

		public void setIndependenceDate(Date independenceDate) {
			this.independenceDate = independenceDate;
		}

	}	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			/**
			 * seq_name = "city_next_id"
			 * 
			Hibernate: 
			    select
			        tbl.seq_val 
			    from
			        place_generator tbl 
			    where
			        tbl.seq_name=? for update
			            
			Hibernate: 
			    insert 
			    into
			        place_generator
			        (seq_name, seq_val)  
			    values
			        (?,?)
			Hibernate: 
			    update
			        place_generator 
			    set
			        seq_val=?  
			    where
			        seq_val=? 
			        and seq_name=?
			        
			Hibernate: 
			    insert 
			    into
			        city
			        (country, latitude, longitude, name, id) 
			    values
			        (?, ?, ?, ?, ?)                
			 * 
			 */
			City firstCity = new City("Tokyo", "Japan", 35.689487f, 139.691711f);
			City secondCity = new City("London", "United Kingdom", 51.507351f, -0.127758f);
			City thridCity = new City("Doha", "Unite Emirate Arab", 25.285446f, 51.531040f);
			
			/**
			 * 
			 * seq_name = "country_next_id"
			 * 
			Hibernate: 
			    select
			        tbl.seq_val 
			    from
			        place_generator tbl 
			    where
			        tbl.seq_name=? for update
			            
			Hibernate: 
			    insert 
			    into
			        place_generator
			        (seq_name, seq_val)  
			    values
			        (?,?)
			Hibernate: 
			    update
			        place_generator 
			    set
			        seq_val=?  
			    where
			        seq_val=? 
			        and seq_name=?
			        
			Hibernate: 
			    insert 
			    into
			        country
			        (independenceDate, name, id) 
			    values
			        (?, ?, ?)
			 * 
			 */
			entityManager.persist(firstCity);
			entityManager.persist(secondCity);
			entityManager.persist(thridCity);
			
			Country firstCountry = new Country("United State Of America", new GregorianCalendar(1776,  5, 4).getTime());
			Country secondCountry = new Country("Germany", new GregorianCalendar(1871,  11, 3).getTime());
			
			entityManager.persist(firstCountry);
			entityManager.persist(secondCountry);			

			/**
			 *
			 * 
				SELECT * FROM city
				---------- ------------------------------ ------------ ------------ --------------
				id         country                        latitude     longitude    name          
				---------- ------------------------------ ------------ ------------ --------------
				1          Japan                          35.6895      139.692      Tokyo         
				2          United Kingdom                 51.5074      -0.127758    London        
				3          Unite Emirate Arab             25.2854      51.531       Doha          

				SELECT * FROM country
				---------- ------------------------- -------------------------------
				id         independenceDate          name                           
				---------- ------------------------- -------------------------------
				1          1776-06-04 00:00:00.0     United State Of America        
				2          1871-12-03 00:00:00.0     Germany                        


				SELECT * FROM place_generator
				----------------------- ------------------- 
				seq_name                seq_val             
				----------------------- ------------------- 
				city_next_id            4                  
				country_next_id         3                  

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
		new InsertExample05().runMain();
	}
}
