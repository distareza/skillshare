package com.jpa.crud;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
 * Demonstrate how to utilize a sharing fields with @Embeddable Annotation
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  2.	Observe how the entities Book and Author is defining Table name with annotation @Table(name="")
 *  
 *  3.	Observe how the GeneratedValue is define in entity Class with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Defined how to defined custom Column definition with @Column Annotation
 *  		check how the column name can be altered or adjust,
 *  		Column Field Type, Column length
 *  		specify the Floating point precision (max digit) and scale ( n digit after decimal ) 
 *  
 *  5.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *  
 *  
 *  6. Demonstrate @Embeddable Entity for Peristent Fields
 *  		Embedded Annotation tells Hibernate that this is a complex object that is embedded with the Entity
 *  		And it should map the fields of this complex nested object to the columns of the Entity Table
 *  
 *  
 */
public class InsertExample13 {
	
	@Embeddable
	public static class CityAndCountry {
		
		private String city;
		private String country;

		public CityAndCountry() {
		}
		
		public CityAndCountry(String city, String country) {
			this.city = city;
			this.country = country;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
		
	}
	
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 

	Hibernate: 
	    
	    create table address (
	       id integer not null auto_increment,
	        birth_date date,
	        city varchar(255),
	        country varchar(255),
	        name varchar(44),
	        primary key (id)
	    ) engine=MyISAM
	    
	     alter table address 
	       add constraint UK_lmac5ci4ou7jrdlnf8okh92m0 unique (name)
          
	 *
	 */	
	@Entity
	@Table(name = "address")
	public class Address {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "name", unique = true, length = 44)
		private String name;
		
		@Column(name = "birth_date")
		@Temporal(TemporalType.DATE)
		private Date birthDate;

		@Embedded
		private CityAndCountry cityAndCountry;
		
		public Address() {
		}
		
		public Address(String name, Date birthDate) {
			this.name = name;
			this.birthDate = birthDate;
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

		public CityAndCountry getCityAndCountry() {
			return cityAndCountry;
		}

		public void setCityAndCountry(CityAndCountry cityAndCountry) {
			this.cityAndCountry = cityAndCountry;
		}

	}	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			Address firstAuthor = new Address("Gilad Barcha", new GregorianCalendar(1980,  1, 0, 14, 20, 56).getTime());
			Address secondAuthor = new Address("James Goshling", new GregorianCalendar(1975,  2, 0, 04, 10, 01).getTime());

			CityAndCountry firstCity = new CityAndCountry("New York", "USA");
			CityAndCountry secondCity = new CityAndCountry("Paris", "France");

			firstAuthor.setCityAndCountry(firstCity);
			secondAuthor.setCityAndCountry(secondCity);
			
			entityManager.persist(firstAuthor);
			entityManager.persist(secondAuthor);
			
			/**
			 * 
id         birth_date                city                      country           name                  
---------- ------------------------- ------------------------- ----------------- ----------------------
1          1980-01-31                New York                  USA               Gilad Barcha          
2          1975-02-28                Paris                     France            James Goshling        
			 * 
			 */
			
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
			ex.printStackTrace();
		} finally {

			entityManager.getTransaction().commit();

			entityManager.clear();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new InsertExample13().runMain();
	}
}
