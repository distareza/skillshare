package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
//import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.MapKeyColumn;
import javax.persistence.Persistence;

/**
 * Demonstrate nested @ElementCollection works
 * Using Map Persistance
 * 
 */
public class Collection03PersistenceUsingMaps {

	@Entity(name = "HighSchoolStudents")
	public static class Students implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;

		@ElementCollection
		@CollectionTable(name = "HighSchoolCourses")
//		@MapKeyColumn(name = "course_id") //--> default: courseMap_KEY
//		@Column(name = "course_name") // --> default: courseMap
		private Map<Integer, String> courseMap;

		public Students() {
		}

		public Students(String name, Map<Integer, String> courseMap) {
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

		public Map<Integer, String> getCourseMap() {
			return courseMap;
		}

		public void setCourseMap(Map<Integer, String> courseMap) {
			this.courseMap = courseMap;
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
			
			Map<Integer, String> mapSarah = new HashMap<>();
			mapSarah.put(141, "Data Structures and Algorithms");
			mapSarah.put(101, "Statistics");
			mapSarah.put(104, "English");

			
			Map<Integer, String> mapTom = new HashMap<>();
			mapTom.put(40, "Geology");
			mapTom.put(334, "Math");
			mapTom.put(104, "English");
			
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
		
		/** Output 
		 *
Hibernate: 
    
    create table HighSchoolCourses (
       HighSchoolStudents_id integer not null,
        courseMap varchar(255),
        courseMap_KEY integer not null,
        primary key (HighSchoolStudents_id, courseMap_KEY)
    ) engine=MyISAM
    
    create table HighSchoolStudents (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM

		
    alter table HighSchoolCourses 
       add constraint FKkiedpvxn3sgocknobgvk81swf 
       foreign key (HighSchoolStudents_id) 
       references HighSchoolStudents (id)
       		
Select Query:		
id         name                                                                                                                                                                                                                                                            
---------- ----------------------------- 
1          Sarah                        
2          Tom                          

HighSchoolStudents_id courseMap                             courseMap_KEY 
--------------------- ------------------------------------- ------------- 
1                     Statistics                            101           
1                     English                               104           
1                     Data Structures and Algorithms        141           
2                     Geology                               40            
2                     English                               104           
2                     Math                                  334           
 
		 * 
		 * 
		 */		
	}
	
	public static void main(String[] args) {
		createAndInsertStudents();
	}

}
