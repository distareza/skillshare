package com.jpa.relationmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship Unidirectional Join Mapping (with other than primary key)
 * Hibernate modeled the mapping one-to-one relationship using foreign key constraints, 
 * The possible representations to model a one-to-one mapping relationship are
 * 	--> 1. Using a foreign key reference to associated entities
 * 		2. By having the entities share a primary key
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
 * 	6.	Notice Annotation @OneToOne that declare in one of method/fields of Entity "Driver" Class to reference as foreign key to Entity "DrivingLicense"
 * 
 * 	7.	To Map both table that join together which not uses a PrimaryKey Field as reference, it should uses @JoinColumn Annotation 
 *  
 *  8.	ReferencedColumnName attribute in @JoinColumn Annotation defined a reference a particular column from a table as foreign key constrain of other table, which not necessary to a primary key, however it must be unique column
 *  	notice the reference column is marked with Annotation @OneToOne  
 * 
 *  Mapping : 
 *  	driver (license_key) -> driving_license (license_no)
 *  
 *  In Table representation, "driver" table is having column "license_key", which foreign key to the unique-"license_no" column of "driving_license" table 
 *  
 *  In JPA representation,
 *  	Driver Entity is able to retrieve its License Entity using getLicense() method
 *  	Driver Entity declaring "OneToOne" mapping annotation with "JoinColumn" annotation to mapped a Reference Entity via column "license_key" 
 *
 */
public class Mapping02OneToOneJoinColumn {

	/**
	 * 
 	==================================================================
    create table driving_license (
       id integer not null auto_increment,
        expiry_date date not null,
        license_no varchar(255) not null,
        license_type varchar(255) not null,
        primary key (id)
    ) engine=MyISAM
    
    alter table driving_license 
       add constraint UK_bs4gktew3gee72irl8b75erhf unique (license_no)
    ==================================================================
	 * 
	 *
	 */
	@Entity(name="DrivingLicense")
	@Table(name="driving_license")
	public static class DrivingLicense implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;

		@Temporal(TemporalType.DATE)
		@Column(name = "expiry_date", nullable = false)
		private Date expiryDate;
		
		@Column(name="license_no", unique = true, nullable = false)
		private String licenseNo;
		
		@Column(name = "license_type", nullable = false)
		private String licenseType;
		
		public DrivingLicense() {
		}

		public DrivingLicense(Date expiryDate, String licenseType) {
			// make sure this column has a unique value
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			this.licenseNo = String.format("%s%s", sdf.format(new Date()), UUID.randomUUID().toString());
			
			this.expiryDate = expiryDate;
			this.licenseType = licenseType;
			
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
		
		public Date getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
		}

		public String getLicenseNo() {
			return licenseNo;
		}

		public void setLicenseNo(String licenseNo) {
			this.licenseNo = licenseNo;
		}

		@Override
		public String toString() {
			return String.format(" { %d, %s, %s, %s} ", this.id, this.licenseNo, this.licenseType, new SimpleDateFormat("dd-MMM-yyyyy").format(this.expiryDate));
		}

	}
	
	/**
	 * 
	==================================================================
    create table driver (
       id integer not null auto_increment,
        birth_date date,
        gender varchar(255),
        name varchar(255),
        license_key varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    alter table driver 
       add constraint FKeqk0du5uthxi7ckthuco6ptnw 
       foreign key (license_key) 
       references driving_license (license_no)
    ==================================================================   
	 *
	 */
	@Entity(name = "Driver")
	@Table(name = "driver")
	public static class Driver implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		private String gender;
		
		@Temporal(TemporalType.DATE)
		@Column(name = "birth_date")
		private Date birthDate;
		
		@OneToOne
		@JoinColumn(name = "license_key", referencedColumnName = "license_no")
		private DrivingLicense license;

		public Driver() {
		}
		
		public Driver(String name, String gender, Date birthDate) {
			super();
			this.name = name;
			this.gender = gender;
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

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}

		public DrivingLicense getLicense() {
			return license;
		}

		public void setLicense(DrivingLicense license) {
			this.license = license;
		}

		@Override
		public String toString() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String birthDate = null;
			if (this.birthDate != null) birthDate = sdf.format(this.birthDate);
			
			return String.format(" { %d, %s, %s, %s } ", this.id, this.name, this.gender, birthDate);
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;

	/**
	 * 
	==================================================================
    select
        mapping02o0_.id as id1_19_0_,
        mapping02o0_.birth_date as birth_da2_19_0_,
        mapping02o0_.gender as gender3_19_0_,
        mapping02o0_.license_key as license_5_19_0_,
        mapping02o0_.name as name4_19_0_,
        mapping02o1_.id as id1_20_1_,
        mapping02o1_.expiry_date as expiry_d2_20_1_,
        mapping02o1_.license_no as license_3_20_1_,
        mapping02o1_.license_type as license_4_20_1_ 
    from
        driver mapping02o0_ 
    left outer join
        driving_license mapping02o1_ 
            on mapping02o0_.license_key=mapping02o1_.license_no 
    where
        mapping02o0_.id=?	
	==================================================================
    select
        mapping02o0_.id as id1_20_0_,
        mapping02o0_.expiry_date as expiry_d2_20_0_,
        mapping02o0_.license_no as license_3_20_0_,
        mapping02o0_.license_type as license_4_20_0_ 
    from
        driving_license mapping02o0_ 
    where
        mapping02o0_.id=?
    ==================================================================    
	 * 
	 */
	public void retrieveData() {
		System.out.println("retrieve data");

		Driver driverOne = em.find(Driver.class, 1);
		System.out.println(driverOne);				// { 1, Matt Robinson, Male, 07-05-1998 } 
		System.out.println(driverOne.getLicense()); // { 1, TRANSACTION-20210507-01, Car, 07-May-02025} 
		Driver driverTwo = em.find(Driver.class, 3);
		System.out.println(driverTwo);					// { 3, Jessica Parker, Female, 02-04-2005 } 
		System.out.println(driverTwo.getLicense());		// { 3, TRANSACTION-20190402-43, Bus, 02-Apr-02025}

		DrivingLicense licenseOne = em.find(DrivingLicense.class, 2);
		System.out.println(licenseOne);  // { 2, TRANSACTION-20201113-21, Motorbike, 13-Nov-02026} 
		DrivingLicense licenseTwo = em.find(DrivingLicense.class, 4);
		System.out.println(licenseTwo); // 	{ 4, TRANSACTION-20200831-74, Car, 31-Aug-02024} 

		List<DrivingLicense> licenses = em.createQuery("SELECT a FROM DrivingLicense a", DrivingLicense.class).getResultList();
		System.out.println(licenses);

		List<Driver> drivers = em.createQuery("SELECT a FROM Driver a", Driver.class).getResultList();
		System.out.println(drivers);

	}
	
	/**
	 * 
	==================================================================
    insert 
    into
        driving_license
        (expiry_date, license_no, license_type) 
    values
        (?, ?, ?)
	==================================================================
    insert 
    into
        driver
        (birth_date, gender, license_key, name) 
    values
        (?, ?, ?, ?)	
	==================================================================
	 * 
	 */
	public void insertData() throws Exception {
		System.out.println("insert data");
		
		em.getTransaction().begin();
		
		DrivingLicense newLicense = new DrivingLicense(new SimpleDateFormat("dd-MM-yyyy").parse("31-12-2030"), "Car");
		em.persist(newLicense);
		Driver newDriver = new Driver("Joan Kirby", "Female", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2004"));
		newDriver.setLicense(newLicense);
		em.persist(newDriver);
		
		em.getTransaction().commit();
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
select * from driver; 
---------- ----------------- ------------- ------------------------ ------------------------------------------------------
id         birth_date        gender        name                     license_key                                           
---------- ----------------- ------------- ------------------------ ------------------------------------------------------
1          1998-05-07        Male          Matt Robinson            TRANSACTION-20210507-01                               
2          2003-11-13        Male          Jack Richer              TRANSACTION-20201113-21                               
3          2005-04-02        Female        Jessica Parker           TRANSACTION-20190402-43                               
4          2008-08-31        Male          Chad Groom               TRANSACTION-20200831-74                               
5          2004-01-01        Female        Joan Kirby               20210707223341c71e165f-8626-48f2-a71a-a3d10d7adce0    

select * from driving_license;
---------- ----------------- ------------------------------------------------------ ---------------
id         expiry_date       license_no                                             license_type   
---------- ----------------- ------------------------------------------------------ ---------------
1          2025-05-07        TRANSACTION-20210507-01                                Car            
2          2026-11-13        TRANSACTION-20201113-21                                Motorbike      
3          2025-04-02        TRANSACTION-20190402-43                                Bus            
4          2024-08-31        TRANSACTION-20200831-74                                Car            
5          2030-12-31        20210707223341c71e165f-8626-48f2-a71a-a3d10d7adce0     Car            

		 * 
		 */
	}
	
	
	public static void main(String[] args) {
		new Mapping02OneToOneJoinColumn().runMain();
	}

}
