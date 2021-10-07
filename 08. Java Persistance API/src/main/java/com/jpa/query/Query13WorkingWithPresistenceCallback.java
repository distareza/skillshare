package com.jpa.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Demonstrate how to hook into the entity lifecycle before and after entity persistence
 * callback methods are a way that we can be notified or called during certain events in the entitites life cycle  
 * 
 *
 */
public class Query13WorkingWithPresistenceCallback {

	/**
	 * 
Hibernate: 
    
    create table departments (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table employee (
       id integer not null,
        name varchar(255),
        department_id integer,
        primary key (id)
    ) engine=MyISAM    
    
    alter table employee 
       add constraint FKo1uiovdf54iyqrovb6soq8yl6 
       foreign key (department_id) 
       references departments (id)
           
	 * 
	 * 
	 */
	
	@Entity(name = "departments")
	public static class Department implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@OneToMany(cascade = CascadeType.ALL)
		@JoinColumn(name="department_id")
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

		public void addEmployees(Employee employee) {
			if (this.employees == null) this.employees = new HashSet<>();
			this.employees.add(employee);
		}

		@Override
		public String toString() {
			return String.format("{ Name: %s, Employeess : %s }", this.name, this.employees);
		}
		
		@PrePersist
		public void onPrePersist() {
			System.out.println("====================== Before perisisting department object : " + name);
		}
		
		@PostPersist
		public void onPostPersist() {
			System.out.println("====================== After perisisting department object : " + name);
		}
		
		/**
		 * for example Query14WorkingWithLoadCallbacks
		 */
		@PostLoad
		public void onPostLoad() {
			System.out.println("====================== After loading department object : " + name);
		}
		
		/**
		 * for example Query15WorkingWithUpdateCallbacks
		 */
		@PreUpdate
		public void onPreUpdate() {
			System.out.println("====================== Before update department object : " + name);
		}
		
		/**
		 * for example Query15WorkingWithUpdateCallbacks
		 */
		@PostUpdate
		public void onPostUpdate() {
			System.out.println("====================== After update department object : " + name);
		}
		
		/**
		 * for example Query16WorkingWithRemoveCallbacks
		 */
		@PreRemove
		public void onPreRemove() {
			System.out.println("====================== Before remove department object : " + name);
		}

		/**
		 * for example Query16WorkingWithRemoveCallbacks
		 */
		@PostRemove
		public void onPostRemove() {
			System.out.println("====================== After remove department object : " + name);
		}

	}
	
	@Entity(name = "employee")
	public static class Employee implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		
		private String name;
		
		@ManyToOne
		@JoinColumn(name="department_id")
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

		@Override
		public String toString() {
			return String.format("Name : %s", this.name);
		}
	
		@PrePersist
		public void onPrePersist() {
			System.out.println("====================== Before perisisting employee object : " + name);
		}
		
		@PostPersist
		public void onPostPersist() {
			System.out.println("====================== After perisisting employee object : " + name);
		}
		
		/**
		 * for example Query14WorkingWithLoadCallbacks
		 */
		@PostLoad
		public void onPostLoad() {
			System.out.println("====================== After loading employee object : " + name);
		}
		
		/**
		 * for example Query15WorkingWithUpdateCallbacks
		 */
		@PreUpdate
		public void onPreUpdate() {
			System.out.println("====================== Before update employee object : " + name);
		}
		
		/**
		 * for example Query15WorkingWithUpdateCallbacks
		 */
		@PostUpdate
		public void onPostUpdate() {
			System.out.println("====================== After update employee object : " + name);
		}
		
		/**
		 * for example Query16WorkingWithRemoveCallbacks
		 */
		@PreRemove
		public void onPreRemove() {
			System.out.println("====================== Before remove employee object : " + name);
		}

		/**
		 * for example Query16WorkingWithRemoveCallbacks
		 */
		@PostRemove
		public void onPostRemove() {
			System.out.println("====================== After remove employee object : " + name);
		}
		
	}
	
	public static void insertData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Employee alice = new Employee("Alice");
			Employee ben = new Employee("Ben");
			Employee cora = new Employee("Cora");
			Employee dennis = new Employee("Dennis");
			
			Department tech = new Department("Tech");
			tech.addEmployees(alice);
			tech.addEmployees(ben);
			
			Department operation = new Department("Operations");
			operation.addEmployees(cora);
			operation.addEmployees(dennis);
			
			em.persist(tech);
			em.persist(operation);
			
			
			/**
			 * 
			 * 
====================== Before perisisting department object : Tech
Hibernate: 
    insert 
    into
        departments
        (name) 
    values
        (?)
====================== After perisisting department object : Tech
====================== Before perisisting employee object : Alice
Hibernate: 
    select
        next_val as id_val 
    from
        hibernate_sequence for update
            
Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
====================== Before perisisting employee object : Ben
Hibernate: 
    select
        next_val as id_val 
    from
        hibernate_sequence for update
            
Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
====================== Before perisisting department object : Operations
Hibernate: 
    insert 
    into
        employee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Alice
Hibernate: 
    insert 
    into
        employee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Ben
Hibernate: 
    insert 
    into
        departments
        (name) 
    values
        (?)
====================== After perisisting department object : Operations
====================== Before perisisting employee object : Dennis
Hibernate: 
    select
        next_val as id_val 
    from
        hibernate_sequence for update
            
Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
====================== Before perisisting employee object : Cora
Hibernate: 
    select
        next_val as id_val 
    from
        hibernate_sequence for update
            
Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
Hibernate: 
    insert 
    into
        employee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Dennis
Hibernate: 
    insert 
    into
        employee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Cora
Hibernate: 
    update
        employee 
    set
        department_id=? 
    where
        id=?
Hibernate: 
    update
        employee 
    set
        department_id=? 
    where
        id=?
Hibernate: 
    update
        employee 
    set
        department_id=? 
    where
        id=?
Hibernate: 
    update
        employee 
    set
        department_id=? 
    where
        id=?

SELECT * FROM departments;
id         name          
---------- -------------- 
1          Tech          
2          Operations    

SELECT * FROM employee;
id         name           department_id 
---------- -------------- ------------- 
1          Ben            1             
2          Alice          1             
3          Dennis         2             
4          Cora           2             


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
	
	
	public static void main(String[] args) {
		insertData();
	}

}
