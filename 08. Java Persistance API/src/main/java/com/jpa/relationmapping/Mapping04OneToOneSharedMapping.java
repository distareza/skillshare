package com.jpa.relationmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship with shared Primary key mapping 
 * Hibernate modeled the mapping one-to-one relationship using foreign key constraints, 
 * The possible representations to model a one-to-one mapping relationship are
 * 		1. Using a foreign key reference to associated entities
 * 	-->	2. By having the entities share a primary key
 * 		3. Using a separate mapping table to model entity associations
 *   
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  	Observe data is initialize through insert statement in preloaded-onlineshop-data.sql 
 *  		 <property name="javax.persistence.sql-load-script-source" value="META-INF/preload-query.sql"/>
 *  	insert statements are structure in one line per statement
 *  	only triggered if the schema generation action is set to drop and create
 * 
 *  2.	Observe how the entities classes are defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity class with different strategy assigning primary key generator
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
 * 	6.	Notice Annotation @OneToOne that declare at method/fields in both Entity Classes to reference as foreign key 
 * 
 * 	7.	Notice Annotation @MapsId that declare on one Entity, Id annotation as primary key will assign to the new column name with ( field name "MapsId" + "_id" )
 * 
 *  8. Notice One-To-One Bidirectional Mapping : the owning side defines the mapping the referencing side references the mapping
 *  	notice that Annotation @OneToOne is mapped on both table / entities
 *  		Field Entity #1 [countries] is set with @MapsId annotation indicating that the primary key of the reference Entity #2 will be shared  ( Entity #1 --> Owning Entity ), the PK of Entity #2 will also be PK Entity #1
 *  		Field Entity #2 [city] 	is set with additional "mappedBy" attribute with a "reference field/member name" of the Entity #2 ( capital )
 *  
 *  
 *  
 *  Mapping : 
 *  	countries (capital_id) = capital (id)
 *
 */
public class Mapping04OneToOneSharedMapping {

	/**
	 * 
    ==================================================================
    create table countries (
       continents varchar(255),
        name varchar(255),
        capital_id integer not null,
        primary key (capital_id)
    ) engine=MyISAM
    
    	 
    alter table countries 
       add constraint FKdixd5d2fqmfwiyw0htskq5sw 
       foreign key (capital_id) 
       references capital (id)
    ==================================================================              
	 * 
	 *
	 */
	@Entity(name="Countries")
	@Table(name="countries")
	public static class Countries implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		private Integer id;
		
		private String name;

		private String continents;
		
		@OneToOne
		@MapsId
		private Capital capital;
		
		public Countries() {
		}

		public Countries(String name, String continents) {
			this.name = name;
			this.continents = continents;
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

		public String getContinents() {
			return continents;
		}

		public void setContinents(String continents) {
			this.continents = continents;
		}

		public Capital getCapital() {
			return capital;
		}

		public void setCapital(Capital capital) {
			this.capital = capital;
		}

		@Override
		public String toString() {
			return String.format(" { %d, %s, %s } ", this.id, this.name, this.continents);
		}

	}
	
	/**
	 * 
	 *   
	==================================================================
    create table capital (
       id integer not null auto_increment,
        establish_date date,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    =================================================================   
	 *       
	 *
	 */
	@Entity(name = "Capital")
	@Table(name = "capital")
	public static class Capital implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@Column(name = "establish_date")
		@Temporal(TemporalType.DATE)
		private Date establishDate;
		
		@OneToOne(mappedBy = "capital")
		private Countries countries;

		public Capital() {
		}
		
		/**
		 * @param name
		 * @param establishDate
		 */
		public Capital(String name, Date establishDate) {
			super();
			this.name = name;
			this.establishDate = establishDate;
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

		public Date getEstablishDate() {
			return establishDate;
		}

		public void setEstablishDate(Date establishDate) {
			this.establishDate = establishDate;
		}

		public Countries getCountries() {
			return countries;
		}

		public void setCountries(Countries countries) {
			this.countries = countries;
		}

		@Override
		public String toString() {
			return String.format(" { %d, %s, %s } ", this.id, this.name, establishDate);
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;

	/**
	 * 
 	==================================================================
    insert 
    into
        capital
        (establish_date, name) 
    values
        (?, ?)
	==================================================================
    insert 
    into
        countries
        (continents, name, capital_id) 
    values
        (?, ?, ?)
    ==================================================================
	 * 
	 */
	public void insertData() throws Exception {
		System.out.println("insert data");
		
		em.getTransaction().begin();
		
		Capital newCapital = new Capital("Jakarta", new SimpleDateFormat("dd-MM-yyyy").parse("17-08-1945"));
		Countries newCountries = new Countries("Indonesia", "Asia");
		newCountries.setCapital(newCapital);
		
		em.persist(newCapital);
		em.persist(newCountries);

		em.getTransaction().commit();
	}
	
	/**
	 * 1. find Capital :
	==================================================================
    select
        mapping04o0_.id as id1_9_0_,
        mapping04o0_.establish_date as establis2_9_0_,
        mapping04o0_.name as name3_9_0_,
        mapping04o1_.capital_id as capital_3_16_1_,
        mapping04o1_.continents as continen1_16_1_,
        mapping04o1_.name as name2_16_1_ 
    from
        capital mapping04o0_ 
    left outer join
        countries mapping04o1_ 
            on mapping04o0_.id=mapping04o1_.capital_id 
    where
        mapping04o0_.id=?
    ==================================================================
	 * 
	 * 2. find Countries
	==================================================================
    select
        mapping04o0_.capital_id as capital_3_16_0_,
        mapping04o0_.continents as continen1_16_0_,
        mapping04o0_.name as name2_16_0_ 
    from
        countries mapping04o0_ 
    where
        mapping04o0_.capital_id=?
    ==================================================================    
    select
        mapping04o0_.id as id1_9_0_,
        mapping04o0_.establish_date as establis2_9_0_,
        mapping04o0_.name as name3_9_0_,
        mapping04o1_.capital_id as capital_3_16_1_,
        mapping04o1_.continents as continen1_16_1_,
        mapping04o1_.name as name2_16_1_ 
    from
        capital mapping04o0_ 
    left outer join
        countries mapping04o1_ 
            on mapping04o0_.id=mapping04o1_.capital_id 
    where
        mapping04o0_.id=?        
    ==================================================================
	 * 
	 * 3. SELECT Capital Query
	==================================================================
    select
        mapping04o0_.id as id1_9_,
        mapping04o0_.establish_date as establis2_9_,
        mapping04o0_.name as name3_9_ 
    from
        capital mapping04o0_
    ==================================================================    
     *
     * 4. Select Countires Query
    ==================================================================    
    select
        mapping04o0_.capital_id as capital_3_16_,
        mapping04o0_.continents as continen1_16_,
        mapping04o0_.name as name2_16_ 
    from
        countries mapping04o0_        
    ==================================================================
	 * 
	 * 
	 */
	public void retrieveData() {
		
		System.out.println("retrieve data");
		Capital capitalOne = em.find(Capital.class, 10);
		System.out.println(capitalOne);						// { 10, Tokyo, 1889-12-05 } 
		System.out.println(capitalOne.getCountries());		// { 10, Japan, Asia } 
		Capital capitalTwo = em.find(Capital.class, 12);
		System.out.println(capitalTwo);						// { 12, Beijing, 1754-02-01 } 
		System.out.println(capitalTwo.getCountries());		// { 12, China, Asia } 
		
		Countries countriesOne = em.find(Countries.class, 13);
		System.out.println(countriesOne);					// { 13, Germany, Europe } 
		System.out.println(countriesOne.getCapital());		// { 13, Berlin, 1945-04-07 } 
		Countries countriesTwo = em.find(Countries.class, 14);
		System.out.println(countriesTwo);					// { 14, South Africa, Africa } 	
		System.out.println(countriesTwo.getCapital());		// { 14, Cape Town, 1956-08-15 }
		
		List<Capital> trxs = em.createQuery("SELECT a FROM Capital a", Capital.class).getResultList();
		System.out.println(trxs);
		
		List<Countries> bills = em.createQuery("SELECT a FROM Countries a", Countries.class).getResultList();
		System.out.println(bills);
	}
	
	public void runMain() {
		factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		em = factory.createEntityManager();

		try {
		
			insertData();
			retrieveData();   

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * 

	select * from countries;
	------------------- ---------- 
	continents          capital_id 
	------------------- ---------- 
	Asia                10         
	Asia                12         
	Europe              13         
	Africa              14         
	Asia                15         
	
	select * from capital;
	---------- ------------------------- ------------------
	id         establish_date            name              
	---------- ------------------------- ------------------
	10         1889-12-05                Tokyo             
	12         1754-02-01                Beijing           
	13         1945-04-07                Berlin            
	14         1956-08-15                Cape Town         
	15         1945-08-17                Jakarta           

		 * 
		 */
	}
	
	
	public static void main(String[] args) {
		new Mapping04OneToOneSharedMapping().runMain();
	}

}
