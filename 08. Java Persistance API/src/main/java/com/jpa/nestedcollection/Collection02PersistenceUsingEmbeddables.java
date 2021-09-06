package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;


/**
 * Demonstrate nested @ElementCollection works 
 * Demonstrate how to use Embeddable Class Persistence in Nested Element Collection
 * 
 *
 */
public class Collection02PersistenceUsingEmbeddables {

	/**
	 * Declaring Embeddable Annotation on Embeddable Class for nexted element collection
	 */
	@Embeddable
	public static class Course {
		
		private String code;
		private String name;

		public Course() {
		}

		public Course(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public boolean equals(Object obj) {
			try {
				return this.code.equals(((Course)obj).getCode());
			} catch (Exception ex) {
				return false;
			}
		}

		@Override
		public String toString() {
			return String.format("{  %s, %s }", this.code, this.name);
		}

		
		
	}
	
	@Entity(name = "ColleageStudents")
	public static class Students implements Serializable{

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		/**
		 * Declaring Annotation Element Collection with Interface Type (in this case uses String.class, not only List object)
		 * notice that it also required to declaring Annotation Collection Table allows you to configure collection mapping table
		 */
		@ElementCollection //(fetch = FetchType.EAGER) 
		@CollectionTable(name = "CollageCourses")
		private Set<Course> courses;

		public Students() {
		}

		public Students(String name, Set<Course> courses) {
			this.name = name;
			this.courses = courses;
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

		public Set<Course> getCourses() {
			return courses;
		}

		public void setCourses(Set<Course> courses) {
			this.courses = courses;
		}

		@Override
		public String toString() {
			return String.format("{  %s, %s } ", this.id, this.name);
		}
		
	}
	
	public static void createAndInsertStudents() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversityDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Set<Course> listSarah = new HashSet<>();
			listSarah.add(new Course("CS141", "Data Structures and Algorithms"));
			listSarah.add(new Course("STAT101", "Statistics"));
			listSarah.add(new Course("EN104", "English"));
			
			Set<Course> listTom = new HashSet<>();
			listTom.add(new Course("GEO041", "Geology"));
			listTom.add(new Course("MATH090", "Math"));
			listTom.add(new Course("EN104", "English"));
			
			Students studentSarah = new Students("Sarah", listSarah);
			Students studentTom = new Students("Tom", listTom);
			
			/**
			 * note : Two Entities can not share a same reference of collection instance
			 */
			
			em.persist(studentSarah);
			em.persist(studentTom);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.getTransaction().commit();
			em.clear();
			factory.close();
		}
		
		/** Output 
		 *
Hibernate: 
    
    create table CollageCourses (
       ColleageStudents_id integer not null,
        code varchar(255),
        name varchar(255)
    ) engine=MyISAM
    
    create table ColleageStudents (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM

		
    alter table CollageCourses 
       add constraint FKb02icdgqkkie12p939gvjmgpc 
       foreign key (ColleageStudents_id) 
       references ColleageStudents (id)
       		
Select Query:		
id         name                                                                                                                                                                                                                                                            
---------- -------------------------- 
1          Sarah                                                                                                                                                                                                                                                           
2          Tom                                                                                                                                                                                                                                                             

ColleageStudents_id code          name                                                                                                                                                                                                                                                            
------------------- ------------- --------------------------------------- 
1                   CS141         Data Structures and Algorithms                                                                                                                                                                                                                                  
1                   STAT101       Statistics                                                                                                                                                                                                                                                      
1                   EN104         English                                                                                                                                                                                                                                                         
2                   GEO041        Geology                                                                                                                                                                                                                                                         
2                   MATH090       Math                                                                                                                                                                                                                                                            
2                   EN104         English                                 
		 * 
		 * 
		 */		
	}
	
	public static void selectStudents() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversityDB_Unit_Read");
		EntityManager em = factory.createEntityManager();

		try {
			 Students s1 = em.find(Students.class, 1);
			 System.out.println(s1);
			 
			 /**
			  * output : 
Hibernate: 
    select
        collection0_.id as id1_15_0_,
        collection0_.name as name2_15_0_ 
    from
        ColleageStudents collection0_ 
    where
        collection0_.id=?
{  1, Sarah } 
            		
        	  *
        	  * notice it only accessing particular students, yet to accessing courses collection
        	  * By default collections are lazily loaded 
        	  *	
			  */
			 
			 System.out.println();
			 System.out.println(s1.getCourses());
			 
			 /**
			  * output
Hibernate: 
    select
        courses0_.ColleageStudents_id as Colleage1_14_0_,
        courses0_.code as code2_14_0_,
        courses0_.name as name3_14_0_ 
    from
        CollageCourses courses0_ 
    where
        courses0_.ColleageStudents_id=?
[{  CS141, Data Structures and Algorithms }, {  STAT101, Statistics }, {  EN104, English }]
			  *
			  * notice hibernate will generate 2nd query statement when accessing the collections
			  * 
			  * 
			  * if you do not want to use default "Lazy" loaded use
			  * @ElementCollection(fetch=FetchType.EAGER)
			  * on the nested collection field
			  * 
			  * the output will as follows :
Hibernate: 
    select
        collection0_.id as id1_15_0_,
        collection0_.name as name2_15_0_,
        courses1_.ColleageStudents_id as Colleage1_14_1_,
        courses1_.code as code2_14_1_,
        courses1_.name as name3_14_1_ 
    from
        ColleageStudents collection0_ 
    left outer join
        CollageCourses courses1_ 
            on collection0_.id=courses1_.ColleageStudents_id 
    where
        collection0_.id=?
{  1, Sarah } 

[{  CS141, Data Structures and Algorithms }, {  STAT101, Statistics }, {  EN104, English }]
			  * 
			  * notice that no 2nd Query is being made when accessing collections 
			  * 
			  * 
			  */
			 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}	
	public static void main(String[] args) {
		//createAndInsertStudents();
		selectStudents();
	}

}
