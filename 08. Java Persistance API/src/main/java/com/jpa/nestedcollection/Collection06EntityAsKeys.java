package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Persistence;

public class Collection06EntityAsKeys {

	@Entity(name = "SampleCourses")
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

		@Override
		public int hashCode() {
			return Objects.hash(level, name);
		}

		@Override
		public boolean equals(Object obj) {
			try {
				return name.equals(((Course)obj).name) && level.equals(((Course)obj).level);
			} catch (Exception ex) {
				return false;
			}
		}

	}
	
	
	@Entity(name = "SampleStudents")
	public static class Students implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@ElementCollection
		@CollectionTable(name = "SampleStudentsCourseMapping")
		@MapKeyJoinColumn(name = "course_id")
		@Column(name = "course_code")
		private Map<Course, Integer> courseMap;

		public Students() {
		}

		public Students(String name, Map<Course, Integer> courseMap) {
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

		public Map<Course, Integer> getCourseMap() {
			return courseMap;
		}

		public void setCourseMap(Map<Course, Integer> courseMap) {
			this.courseMap = courseMap;
		}

		@Override
		public String toString() {
			return String.format("{ %s. %s }", this.id, this.name);
		}
		
	}
	
	public static void createAndInsert() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UniversityDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Map<Course, Integer> mapSarah = new HashMap<>();
			mapSarah.put(new Course("Intermediate", "Data Structures and Algorithms"), 141);
			mapSarah.put(new Course("Basic", "Statistics"), 101);
			mapSarah.put(new Course("Basic", "English"), 104);
		
			Map<Course, Integer> mapTom = new HashMap<>();
			mapTom.put(new Course("Intermediate", "Geology"), 40);
			mapTom.put(new Course("Advanced", "Math"), 334);
			
			Students studentSarah = new Students("Sarah", mapSarah);
			Students studentTom = new Students("Tom", mapTom);
			
			em.persist(studentSarah);
			em.persist(studentTom);

			
			for (Course course : mapSarah.keySet()) {
				em.persist(course);
			}
			for (Course course : mapTom.keySet()) {
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

    create table SampleCourses (
       id integer not null,
        level varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table SampleStudents (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table SampleStudentsCourseMapping (
       SampleStudents_id integer not null,
        course_code integer,
        course_id integer not null,
        primary key (SampleStudents_id, course_id)
    ) engine=MyISAM
    
    alter table SampleStudentsCourseMapping 
       add constraint FK84h51w8b2rxjltyd9h28q4k5a 
       foreign key (course_id) 
       references SampleCourses (id)
    
    alter table SampleStudentsCourseMapping 
       add constraint FK2h72cknribcb5yimsaek12mpe 
       foreign key (SampleStudents_id) 
       references SampleStudents (id)
    
SELECT * FROM SampleStudents;
id         name     
---------- --------- 
1          Sarah    
2          Tom      

SELECT * FROM SampleCourses;
id         level            name                               
---------- ---------------- -----------------------------------
1          Intermediate     Data Structures and Algorithms     
2          Basic            Statistics                         
3          Basic            English                            
4          Intermediate     Geology                            
5          Advanced         Math                               

SELECT * FROM SampleStudentsCourseMapping;    
SampleStudents_id course_code course_id  
----------------- ----------- ---------- 
1                 141         1          
1                 101         2          
1                 104         3          
2                 40          4          
2                 334         5          

    
		 * 
		 */
	}
	
	public static void main(String[] args) {
		createAndInsert();
	}
	
}
