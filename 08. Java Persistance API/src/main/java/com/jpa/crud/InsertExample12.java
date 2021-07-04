package com.jpa.crud;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate how to deal with Large Object (Binary Large Object-BLOB and Characther Large Object-CLOB)
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  2.	Observe how the entities classes are defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity Class with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Defined how to defined custom Column definition with @Column Annotation
 *  		check how the column name can be altered or adjust,
 *  		Column Field Type, Column length
 *  
 *  5.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *  
 *  6 Demonstrate @Lob and @Basic(fetch=LAZY) Annotation for Large Object
 *  		uses of FecthType.LAZY means that the contents of the bio column will be lazily loaded when we explicitly use the getter or setter
 *  		Lob annotation is assign Large Object for the column
 *  
 */
public class InsertExample12 {
	
	/**
	 * 
	 * Spring JPA will run following query
	 * 
	 *	    create table birthday_photos (
	 *			id integer not null auto_increment,
	 *			bio longtext,
	 *			birth_date date,
	 *			exactBirthDate datetime,
	 *			exactBirthTime time,
	 *			image longblob,
	 *			name varchar(44),
	 *			primary key (id)
	 *		)
	 *  
	 *  alter table birthday_photos 
	 *  	add constraint UK_gbk6pw1g8x97j2khr1ba6cywp unique (name) 
	 *
	 */	
	@Entity
	@Table(name = "birthday_photos")
	public class BirthdayPhoto {
		
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
		
		@Basic(fetch = FetchType.LAZY)
		@Lob		
		private String bio;
		
		@Basic(fetch = FetchType.LAZY)
		@Lob
		private byte[] image;

		public BirthdayPhoto() {
		}
		
		public BirthdayPhoto(String name, Date birthDate) {
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

		public String getBio() {
			return bio;
		}

		public void setBio(String bio) {
			this.bio = bio;
		}

		public byte[] getImage() {
			return image;
		}

		public void setImage(byte[] image) {
			this.image = image;
		}
		
		
		
	}	
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			BirthdayPhoto firstPerson = new BirthdayPhoto("Gilad Barcha", new GregorianCalendar(1980,  1, 0, 14, 20, 56).getTime());
			firstPerson.setBio("Some very Long Personal Line of Biodata here");
			firstPerson.setImage("Pretend this is an image content".getBytes());
			
			BirthdayPhoto secondPerson = new BirthdayPhoto("James Goshling", new GregorianCalendar(1975,  2, 0, 04, 10, 01).getTime());
			secondPerson.setImage("Pretned this is also an image content".getBytes());
			
			entityManager.persist(firstPerson);
			entityManager.persist(secondPerson);			

			/**
			 * 
id         bio                                                  birth_date                exactBirthDate            exactBirthTime            image                       name                   
---------- ---------------------------------------------------- ------------------------- ------------------------- ------------------------- --------------------------- ---------------------- 
1          Some very Long Personal Line of Biodata here         1980-01-31                1980-01-31 14:20:56.0     14:20:56                  (BINARY DATA)               Gilad Barcha           
2                                                               1975-02-28                1975-02-28 04:10:01.0     04:10:01                  (BINARY DATA)               James Goshling         
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
		new InsertExample12().runMain();
	}
}
