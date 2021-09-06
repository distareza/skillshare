package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

/**
 * Demonstrate model relationships in JPA either between entities or between an entity and a nested collection
 *  1. Cascade Default operation is None : all entity must be persisted  
 *  2. Cascade Persist : reference entity are inserted when the parent entity is inserted / saved
 *  3. Cascade Remove : reference entities are removed when the parent entity is deleted
 *  4. Cascade Merge : references entities are updated when the parent entity is merged / saved
 *  5. Cascade Detach : Detaching an entity means to removes the entity from the persistence context.
 *  	changes that you make to the entity after the entity has been detached, including removal of the entity, will not be synchronized to the database. Detach does not affect the database.
 *  6. Cascade All : cascade down to child / reference entities
 *  	Persist, Remove. Merge And Detach Operation will cascaded down to the childs or references entities
 *  
 */
public class Collection07CascadeType {

	@Entity(name = "department")
	public static class Department implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		//@OneToMany --> default Cascade
		//@OneToMany (cascade = CascadeType.PERSIST) // --> declaring persist cascade
		//@OneToMany (cascade = CascadeType.REMOVE) // --> declaring Remove cascade
		//@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // --> declaring Merge cascade
		//@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.DETACH) --> declaring Detach cascade
		@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL) // --> declaring All cascade
		@JoinColumn(name = "department_id")
		private Set<Employee> employees;

		public Department() {
		}

		public Department(String name) {
			this.name = name;
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

		public Set<Employee> getEmployees() {
			return employees;
		}

		public void addEmployee(Employee employee) {
			if (this.employees == null) this.employees = new HashSet<>();
			this.employees.add(employee);
		}
		
		
	}
	
	@Entity(name = "employees")
	public static class Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		
		private String name;
		
		@ManyToOne
		@JoinColumn(name = "department_id")
		private Department department;

		public Employee() {
		}

		public Employee(String name) {
			this.name = name;
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

		public Department getDepartment() {
			return department;
		}

		public void setDepartment(Department department) {
			this.department = department;
		}
		
	}
	
	/**
	 * Insert data with declaring all its reference entities
	 * 
SELECT * FROM department;       
id         name              
---------- ------------------
1          Engineering       
2          sales             

SELECT * FROM employees;
id         name        department_id 
---------- ----------- ------------- 
1          Alice       1             
2          Ben         1             
3          Cora        2             
4          Dennis      2             

	 * 
	 */
	public static void insertWithReference() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Employee alice = new Employee("Alice");
			Employee ben = new Employee("Ben");
			Employee cora = new Employee("Cora");
			Employee dennis = new Employee("Dennis");
			
			Department engineering = new Department("Engineering");
			engineering.addEmployee(alice);
			engineering.addEmployee(ben);
			
			Department sales = new Department("sales");
			sales.addEmployee(cora);
			sales.addEmployee(dennis);
			
			em.persist(engineering);
			em.persist(sales);
			
			em.persist(alice);
			em.persist(ben);
			em.persist(cora);
			em.persist(dennis);

			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}

	}

	
	/**
	 * 
	 * Insert data without declaring a persistence to its references entity 
	 * 
	 * this method will retrun depend on cascade type:
	 * 
	 * 1. Set Cascade as Default or None :
	 * 		this methods will returning following error when cascade set to default or None 
	 * 		org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
	 * 
	 * 		to solve the issue is to explicitly persist every entity, even though it's part of a relationship mapping
	 * 
	 * 		however, both table entity are created but only department entity is having the value as follows
	 * 
Hibernate: 
    
    create table department (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table employees (
       id integer not null,
        name varchar(255),
        department_id integer,
        primary key (id)
    ) engine=MyISAM
       
    alter table employees 
       add constraint FK8if1byloc650qvkaxabyjveap 
       foreign key (department_id) 
       references department (id)
       
SELECT * FROM department;       
id         name                                                                                                                                                                                                                                                            
---------- ----------------- 
1          Engineering      
2          sales            

SELECT * FROM employees;
no rows selected               
	 * 
	 * 2. Set Cascade as Persisted :
	 * 		will run without issue
	 * 		The persist operation on the department entity will cascade to the owned employee entities on the other side of this OneToMany relationship mapping.
	 * 
SELECT * FROM department;       
id         name              
---------- ------------------
1          Engineering       
2          sales             

SELECT * FROM employees;
id         name        department_id 
---------- ----------- ------------- 
1          Alice       1             
2          Ben         1             
3          Cora        2             
4          Dennis      2             
	 * 
	 *  3. set Cascade as All :
	 *  	will run without issue
	 *  	The persist operation on the department entity will cascade to the owned employee entities on the other side of this OneToMany relationship mapping.
	 *
SELECT * FROM department;       
id         name              
---------- ------------------
1          Engineering       
2          sales             

SELECT * FROM employees;
id         name        department_id 
---------- ----------- ------------- 
1          Alice       1             
2          Ben         1             
3          Cora        2             
4          Dennis      2             
	 * 
	 * 
	 */
	public static void insertWithoutReference() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Employee alice = new Employee("Alice");
			Employee ben = new Employee("Ben");
			Employee cora = new Employee("Cora");
			Employee dennis = new Employee("Dennis");
			
			Department engineering = new Department("Engineering");
			engineering.addEmployee(alice);
			engineering.addEmployee(ben);
			
			Department sales = new Department("sales");
			sales.addEmployee(cora);
			sales.addEmployee(dennis);
			
			em.persist(engineering);
			em.persist(sales);
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
	}

	
	/**
	 * This method will return depend on the cascade type :
	 * 
	 * 1. Cascade Persist:
	 * 		remove only removing selected department and update the foreign key department_id to null for entity employee
	 * 
SELECT * FROM department;
id         name                                                                                                                                                                                                                                                            
---------- --------------------- 
1          Engineering                                                                                                                                                                                                                                                     

SELECT * FROM employees;
id         name                  department_id 
---------- --------------------- ------------- 
1          Ben                   1             
2          Alice                 1             
3          Cora                  (null)
4          Dennis                (null)

	 *
	 * 2. Cascade Remove:
	 * 		will remove selected department and all its referenced employee entities as well
	 * 
SELECT * FROM department;
id         name                                                                                                                                                                                                                                                            
---------- --------------------- 
1          Engineering                                                                                                                                                                                                                                                     

SELECT * FROM employees;
id         name                  department_id 
---------- --------------------- ------------- 
1          Ben                   1             
2          Alice                 1            
	 * 
	 * 3. Cascade ALL:
	 * 		will remove selected department and all its referenced employee entities as well
	 * 
SELECT * FROM department;
id         name                                                                                                                                                                                                                                                            
---------- --------------------- 
1          Engineering                                                                                                                                                                                                                                                     

SELECT * FROM employees;
id         name                  department_id 
---------- --------------------- ------------- 
1          Ben                   1             
2          Alice                 1            
	 * 
	 */
	public static void removeEntity() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Department sales = em.find(Department.class, 2);
			em.remove(sales);

			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}		
	}
	
	/**
	 * This method to demonstrate on how to update an entity references using Cascade Merge
	 * any updates to the parent entity is propagated down to child entities
	 * 
SELECT * FROM department;
id         name                                                                                                                                                                                                                                                            
---------- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
1          Engineering                                                                                                                                                                                                                                                     
2          sales                                                                                                                                                                                                                                                           

SELECT * FROM employees;
id         name                                                                                                                                                                                                                                                            department_id 
---------- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ------------- 
1          Alice                                                                                                                                                                                                                                                           1             
2          Ben                                                                                                                                                                                                                                                             1             
3          Cora                                                                                                                                                                                                                                                            2             
4          Dennis                                                                                                                                                                                                                                                          2             
5          Elsa                                                                                                                                                                                                                                                            2             

	 * 
	 * 
	 */
	public static void mergeEntity() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Department sales = em.find(Department.class, 2);
			Employee elsa = new Employee("Elsa");
			sales.addEmployee(elsa);

			em.merge(sales);
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	/**
	 * Demonstrate how Cascade Detach is working
	 * 1. Department Cascade Type set to MERGE:
	 * 		The Detaching entity does not affect reference entity
	 * 2. Department Cascade Type = DETACH
	 * 		The Detaching entity affect reference entity
	 * 3. Department Cascade Type = ALL
	 * 		The Detaching entity affect reference entity
	 */
	public static void detachEntity() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();

		try {
			em.getTransaction().begin();
			
			Department sales = em.find(Department.class, 2);
			Employee cora = em.find(Employee.class, 3);
			Employee alice = em.find(Employee.class, 1);
			
			em.detach(sales);
			
			System.out.println("is sales attached : " + em.contains(sales)); 
			System.out.println("is Cora attached : " + em.contains(cora));
			System.out.println("is Alice attached : " + em.contains(alice));

			/**
			 *  
			 * 1. Cascade MERGE on Department Entity :
			 * 	  output :
					is sales attached : false
					is Cora attached : true
					is Alice attached : true
			 * 
			 *  2. Cascade DETACH on Department Entity :
			 *     output :  
					is sales attached : false
					is Cora attached : false
					is Alice attached : true
			 * 
			 * 
			 */
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
	}

	public static void main (String[] args) {
		insertWithoutReference();
		//insertWithReference();
		//removeEntity();
		//mergeEntity();
		//detachEntity();
		
	}
	
}
