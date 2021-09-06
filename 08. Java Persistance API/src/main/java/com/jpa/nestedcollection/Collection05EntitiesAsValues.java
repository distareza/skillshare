package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

public class Collection05EntitiesAsValues {

	@Entity(name = "EmbeddableCourses")
	public static class Course {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		
		private String level;
		private String name;

		public Course() {
		}
		
		public Course(String level, String name) {
			this.level = level;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
	
	@Entity(name = "EmbeddableStudents")
	public static class Students implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@OneToMany
		@CollectionTable(name = "EmbeddableStudentCourseMaping")
		@MapKeyColumn(name = "course_code")
		private Map<Integer, Course> courseMap;

		public Students() {
		}

		public Students(String name, Map<Integer, Course> courseMap) {
			this.name = name;
			this.courseMap = courseMap;
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

		public Map<Integer, Course> getCourseMap() {
			return courseMap;
		}

		public void setCourseMap(Map<Integer, Course> courseMap) {
			this.courseMap = courseMap;
		}
		
	}
	
	public static void createAndInsert() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversityDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Map<Integer, Course> mapSarah = new HashMap<>();
			mapSarah.put(141, new Course("Intermediate", "Data Structures and Algorithms"));
			mapSarah.put(101, new Course("Basic", "Statistics"));
			mapSarah.put(104, new Course("Basic", "English"));
		
			Map<Integer, Course> mapTom = new HashMap<>();
			mapTom.put(40, new Course("Intermediate", "Geology"));
			mapTom.put(334, new Course("Advanced", "Math"));
			
			Students studentSarah = new Students("Sarah", mapSarah);
			Students studentTom = new Students("Tom", mapTom);
			
			em.persist(studentSarah);
			em.persist(studentTom);

			
			for (Course course : mapSarah.values()) {
				em.persist(course);
			}
			for (Course course : mapTom.values()) {
				em.persist(course);
			}
			
			/**
			 * note : Two Entities need to persist object separately
			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.getTransaction().commit();
			em.clear();
			factory.close();
		}
		
		/**
		 * output :
Hibernate: 
    
    create table EmbeddableCourses (
       id integer not null,
        level varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table EmbeddableStudentCourseMaping (
       EmbeddableStudents_id integer not null,
        courseMap_id integer not null,
        course_code integer not null,
        primary key (EmbeddableStudents_id, course_code)
    ) engine=MyISAM
    
    create table EmbeddableStudents (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    alter table EmbeddableStudentCourseMaping 
       add constraint UK_7bec5lfn2xem3y7cy47f9w3lo unique (courseMap_id)

    alter table EmbeddableStudentCourseMaping 
       add constraint FKj13e2plmdamkvgij5mk7ee7hg 
       foreign key (courseMap_id) 
       references EmbeddableCourses (id)
    
    alter table EmbeddableStudentCourseMaping 
       add constraint FK6569f56wt9gs4kuj7rr99a7r8 
       foreign key (EmbeddableStudents_id) 
       references EmbeddableStudents (id)    
    
SELECT * FROM EmbeddableStudents;
id         name                                                                                                                                                                                                                                                            
---------- --------------------------------- 
1          Sarah                            
2          Tom                              

SELECT * FROM EmbeddableCourses;
id         level                   name                                  
---------- ----------------------- ------------------------------------- 
1          Basic                   Statistics                           
2          Basic                   English                              
3          Intermediate            Data Structures and Algorithms       
4          Intermediate            Geology                              
5          Advanced                Math                                 

SELECT * FROM EmbeddableStudentCourseMaping;
EmbeddableStudents_id courseMap_id course_code 
--------------------- ------------ ----------- 
1                     1            101         
1                     2            104         
1                     3            141         
2                     4            40          
2                     5            334         

		 * 
		 */
		
	}
	
	public static void main(String[] args) {
		createAndInsert();
	}

}
