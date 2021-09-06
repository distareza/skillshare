package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Persistence;

public class Collection04EmbeddableMap {
	
	@Embeddable
	public static class Course {
		
		private String level;
		private String name;
		
		public Course() {
		}
		
		public Course(String level, String name) {
			this.level = level;
			this.name = name;
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
	
	@Entity(name = "LearnerStudents")
	public static class Students implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@ElementCollection
		@CollectionTable(name = "LearnerCourses")
		@MapKeyColumn(name = "course_id")
		private Map<Integer, Course> courseMaps;

		public Students() {
		}

		public Students(String name, Map<Integer, Course> courseMaps) {
			this.name = name;
			this.courseMaps = courseMaps;
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

		public Map<Integer, Course> getCourseMaps() {
			return courseMaps;
		}

		public void setCourseMaps(Map<Integer, Course> courseMaps) {
			this.courseMaps = courseMaps;
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
			
			Map<Integer, Course> mapSarah = new HashMap<>();
			mapSarah.put(141, new Course("Intermediate", "Data Structures and Algorithms"));
			mapSarah.put(101, new Course("Basic", "Statistics"));
			mapSarah.put(104, new Course("Basic", "English"));
		
			Map<Integer, Course> mapTom = new HashMap<>();
			mapTom.put(40, new Course("Intermediate", "Geology"));
			mapTom.put(334, new Course("Advanced", "Math"));
			
			Students studentSarah = new Students("Sarah", mapSarah);
			Students studentTom = new Students("Tom", mapTom);
			
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
		
		/**
		 * output :
Hibernate: 
    
    create table LearnerCourses (
       LearnerStudents_id integer not null,
        level varchar(255),
        name varchar(255),
        course_id integer not null,
        primary key (LearnerStudents_id, course_id)
    ) engine=MyISAM
    
    create table LearnerStudents (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    alter table LearnerCourses 
       add constraint FKbswx02fbwfrxqrikguaqup9mb 
       foreign key (LearnerStudents_id) 
       references LearnerStudents (id)
       

id         name       
---------- ----------- 
1          Sarah      
2          Tom        

LearnerStudents_id level                name                               course_id  
------------------ -------------------- ---------------------------------- ----------- 
1                  Basic                Statistics                         101        
1                  Basic                English                            104        
1                  Intermediate         Data Structures and Algorithms     141        
2                  Intermediate         Geology                            40         
2                  Advanced             Math                               334        
           
    
		 * 
		 */
		
	}

	public static void main(String[] args) {
		createAndInsertStudents();

	}

}
