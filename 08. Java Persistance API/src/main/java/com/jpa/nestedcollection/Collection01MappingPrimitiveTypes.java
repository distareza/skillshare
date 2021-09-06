package com.jpa.nestedcollection;

/**
 * Demonstrate nested @ElementCollection works
 * when student deleted, the courses associated with a student will also deleted
 * 
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

public class Collection01MappingPrimitiveTypes {

	@Entity(name = "Students")
	public static class Student implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		/**
		 * Declaring Annotation Element Collection with Interface Type (in this case uses String.class, not only List object) 
		 */
		@ElementCollection 
		//(fetch = FetchType.EAGER) --> to enable EAGER-ly loaded (by default : LAZY-ly loaded
		private List<String> courses;

		public Student() {
		}
		
		public Student(String name, List<String> courses) {
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

		public List<String> getCourses() {
			return courses;
		}

		public void setCourses(List<String> courses) {
			this.courses = courses;
		}

		@Override
		public String toString() {
			return String.format("{ %s, %s }", this.id, this.name);
		}
		
		
	}
	
	public static void createAndInsertStudents() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversityDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			List<String> listSarah = new ArrayList<>();
			listSarah.add("Data Structures and Algorithms");
			listSarah.add("Statistics");
			listSarah.add("English");
			listSarah.add("Data Structures and Algorithms");
			
			List<String> listTom = new ArrayList<>();
			listTom.add("Geology");
			listTom.add("Math");
			
			Student studentSarah = new Student("Sarah", listSarah);
			Student studentTom = new Student("Tom", listTom);
			
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
    
    create table Students (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table Students_courses (
       Students_id integer not null,
        courses varchar(255)
    ) engine=MyISAM

		
    alter table Students_courses 
       add constraint FKuyidncnxppciun6xdpffre58 
       foreign key (Students_id) 
       references Students (id)		
		
Select Query:		
		 	SELECT * FROM Students;
			id         name                                                                                                                                                                                                                                                            
			---------- --------------------------- 
			1          Sarah                                                                                                                                                                                                                                                           
			2          Tom                                                                                                                                                                                                                                                             
			
			SELECT * FROM Students_courses;
			Students_id courses                                                                                                                                                                                                                                                         
			----------- ------------------------------------ 
			1           Data Structures and Algorithms                                                                                                                                                                                                                                  
			1           Statistics                                                                                                                                                                                                                                                      
			1           English                                                                                                                                                                                                                                                         
			1           Data Structures and Algorithms                                                                                                                                                                                                                                  
			2           Geology                                                                                                                                                                                                                                                         
			2           Math             
		 * 
		 * 
		 */		
	}
	
	public static void selectStudents() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversityDB_Unit_Read");
		EntityManager em = factory.createEntityManager();

		try {
			 Student s1 = em.find(Student.class, 1);
			 System.out.println(s1);
			 
			 /**
			  * output : 
Hibernate: 
    select
        collection0_.id as id1_62_0_,
        collection0_.name as name2_62_0_ 
    from
        Students collection0_ 
    where
        collection0_.id=?
        
{ 1, Sarah }                		
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
        courses0_.Students_id as Students1_63_0_,
        courses0_.courses as courses2_63_0_ 
    from
        Students_courses courses0_ 
    where
        courses0_.Students_id=?
        
[Data Structures and Algorithms, Statistics, English, Data Structures and Algorithms]
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
        collection0_.id as id1_62_0_,
        collection0_.name as name2_62_0_,
        courses1_.Students_id as Students1_63_1_,
        courses1_.courses as courses2_63_1_ 
    from
        Students collection0_ 
    left outer join
        Students_courses courses1_ 
            on collection0_.id=courses1_.Students_id 
    where
        collection0_.id=?
{ 1, Sarah }

[Data Structures and Algorithms, Statistics, English, Data Structures and Algorithms]
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
