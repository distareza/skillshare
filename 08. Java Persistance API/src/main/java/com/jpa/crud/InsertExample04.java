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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity with Generated SEQUENCE as primary Key  
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
 *  3.	Observe how the entities class is defining Table name with annotation @Table(name="")
 *  
 *  4.	observe that the annotation @Id is required to identified a Primary Key 
 *  
 *  5.	Observe how the GeneratedValue is define in entity Class with different strategy (SEQUENCE) assigning primary key generator
 * 		demonstrated the entity that having generated value
 * 			@GeneratedValue(strategy = GenerationType.SEQUENCE) uses SEQUENCE as auto increment 
 * 
 *  		> AUTO 		: uses sequence
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence ("book_sequence")
 *  
 *  	This Sequence can uses same sequence generator for other table 
 *  
 */
public class InsertExample04 {

	/**
	 * Demonstrate the entity that having generated value
	 * @GeneratedValue
	 * 	strategy = GeneratedType.AUTO --> uses default sequence object (hibernate_sequence)  within your database to generate primary keys values , default
	 * 	strategy = GenerationType.IDENTITY --> uses auto incremented database column (not using sequence object), the efficient way (not optimum way)
	 *  strategy = GenerationType.SEQUENCE --> uses defined sequence object to generate primary keys values
	 *  	uses along with annotation @SequenceGenerator
	 *  
		Hibernate: 
		    
		    create table campus (
		       id integer not null,
		        address varchar(255),
		        enterFee float,
		        name varchar(255),
		        primary key (id)
		    ) engine=MyISAM
		    
		Hibernate: 
		    
		    create table studyplace_sequence (
		       next_val bigint
		    ) engine=MyISAM
		    
		    
		Hibernate: 
		    
		    insert into studyplace_sequence values ( 1 )		    
	 *  
	 *
	 */
	@Entity
	@Table(name = "campus")
	public class Campus {

		private Integer id;
		private String name;
		private String address;
		private Float enterFee;
		
		public Campus() {
		}

		public Campus(String name, String address, Float enterFee) {
			this.name = name;
			this.address = address;
			this.enterFee = enterFee;
		}

		@Id
		//@GeneratedValue(strategy = GenerationType.AUTO) // --> uses sequence generator to generate primary key
		//@GeneratedValue(strategy = GenerationType.IDENTITY) // --> uses auto incremented database column (not using sequence object)
		@SequenceGenerator(name = "my_seq_generator", sequenceName = "studyplace_sequence")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="my_seq_generator")
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Float getEnterFee() {
			return enterFee;
		}

		public void setEnterFee(Float enterFee) {
			this.enterFee = enterFee;
		}

	}
	
	/**
	 * Demonstrate multiple identity generator with existing sequence with previous entity 
	 * default allocation sequence for each different entity : 50
	 *
Hibernate: 
    
    create table college (
       id integer not null,
        name varchar(255),
        openingDate datetime,
        primary key (id)
    ) engine=MyISAM
	 * 
	 *
	 */

	@Entity
	@Table(name = "college")
	public class College {
		
		private Integer id;
		private String name;
		private Date openingDate;

		public College() {
		}
		
		public College(String name, Date openingDate) {
			this.name = name;
			this.openingDate = openingDate;
		}

		@Id
		@SequenceGenerator(name = "my_seq_generator", sequenceName = "studyplace_sequence")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="my_seq_generator")
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

		public Date getOpeningDate() {
			return openingDate;
		}

		public void setOpeningDate(Date openingDate) {
			this.openingDate = openingDate;
		}

	}	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			Campus firstCampus = new Campus("Harvard University", "United States", 99f);
			Campus secondCampus = new Campus("University of Cambridge", "United Kingdom", 119f);
			Campus thridCampus = new Campus("Massachusetss Intitues of Technology", "United States", 200f);

			/**
			 * 
			Hibernate: 
			    select
			        next_val as id_val 
			    from
			        studyplace_sequence for update
			            
			Hibernate: 
			    update
			        studyplace_sequence 
			    set
			        next_val= ? 
			    where
			        next_val=?
			        
			Hibernate: 
			    insert 
			    into
			        campus
			        (address, enterFee, name, id) 			        
			* 			
			*/
			entityManager.persist(firstCampus);
			entityManager.persist(secondCampus);
			entityManager.persist(thridCampus);
			
			College firstCollege = new College("Standford University", new GregorianCalendar(1980,  1, 0).getTime());
			College secondCollege = new College("California Institute of Technology", new GregorianCalendar(1975,  2, 0).getTime());
			
			/**
			 * 
			Hibernate: 
			    select
			        next_val as id_val 
			    from
			        studyplace_sequence for update
			            
			Hibernate: 
			    update
			        studyplace_sequence 
			    set
			        next_val= ? 
			    where
			        next_val=?
			        
			Hibernate: 
			    insert 
			    into
			        college
			        (name, openingDate, id) 
			    values
			        (?, ?, ?)
			 * 
			 */
			entityManager.persist(firstCollege);
			entityManager.persist(secondCollege);
			
			/**
			 *
			 * 
			select * from campus;
			---------- --------------------------------------- ------------------------------------------- ------------ 
			id         address                                 name                                        enterFee                                                                                                                                                                                                                         
			---------- --------------------------------------- ------------------------------------------- ------------ 
			1          United States                           Harvard University                          99.0                                                                                                                                                                                                                             
			2          United Kingdom                          University of Cambridge                     119.0                                                                                                                                                                                                                            
			3          United States                           Massachusetss Intitues of Technology        200.0                                                                                                                                                                                                                            

			select * from college;
			---------- --------------------------------------- ------------------------- 
			id         name                                    openingDate               
			---------- --------------------------------------- ------------------------- 
			52         Standford University                    1980-01-31 00:00:00.0     
			53         California Institute of Technology      1975-02-28 00:00:00.0     

			select * from studyplace_sequence;
			-------------------
			next_val            
			------------------- 
			151                 
			151                

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
		new InsertExample04().runMain();
	}

}
