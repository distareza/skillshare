package com.jpa.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

public class Query17ConfigureEnitiyListenersforCallbackMethods {

	public static class EmployeeListener {
		
		@PrePersist
		public void onPrePersist(Employee employee) {
			System.out.println("====================== Before perisisting employee object : " + employee.name);
		}
		
		@PostPersist
		public void onPostPersist(Employee employee) {
			System.out.println("====================== After perisisting employee object : " + employee.name);
		}
		
		@PostLoad
		public void onPostLoad(Employee employee) {
			System.out.println("====================== After loading employee object : " + employee.name);
		}
		
		@PreUpdate
		public void onPreUpdate(Employee employee) {
			System.out.println("====================== Before update employee object : " + employee.name);
		}
		
		@PostUpdate
		public void onPostUpdate(Employee employee) {
			System.out.println("====================== After update employee object : " + employee.name);
		}
		
		@PreRemove
		public void onPreRemove(Employee employee) {
			System.out.println("====================== Before remove employee object : " + employee.name);
		}

		@PostRemove
		public void onPostRemove(Employee employee) {
			System.out.println("====================== After remove employee object : " + employee.name);
		}
	}
	
	public static class DepartmentListener { 
		@PrePersist
		public void onPrePersist(Department department) {
			System.out.println("====================== Before perisisting department object : " + department.name);
		}
		
		@PostPersist
		public void onPostPersist(Department department) {
			System.out.println("====================== After perisisting department object : " + department.name);
		}
		
		@PostLoad
		public void onPostLoad(Department department) {
			System.out.println("====================== After loading department object : " + department.name);
		}
		
		@PreUpdate
		public void onPreUpdate(Department department) {
			System.out.println("====================== Before update department object : " + department.name);
		}
		
		@PostUpdate
		public void onPostUpdate(Department department) {
			System.out.println("====================== After update department object : " + department.name);
		}
		
		@PreRemove
		public void onPreRemove(Department department) {
			System.out.println("====================== Before remove department object : " + department.name);
		}

		@PostRemove
		public void onPostRemove(Department department) {
			System.out.println("====================== After remove department object : " + department.name);
		}

	}
	
	@Entity(name = "employeee")
	@EntityListeners(EmployeeListener.class)
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

	}
	
	@Entity(name = "departmeents")
	@EntityListeners(DepartmentListener.class)
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
	}
	
	public static void insertData( ) {
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

			em.getTransaction().commit();
		} catch (Exception ex) {
			
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output :
====================== Before perisisting department object : Tech
Hibernate: 
    insert 
    into
        departmeents
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
        employeee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Alice
Hibernate: 
    insert 
    into
        employeee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Ben
Hibernate: 
    insert 
    into
        departmeents
        (name) 
    values
        (?)
====================== After perisisting department object : Operations
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
Hibernate: 
    insert 
    into
        employeee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Cora
Hibernate: 
    insert 
    into
        employeee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Dennis
Hibernate: 
    update
        employeee 
    set
        department_id=? 
    where
        id=?
Hibernate: 
    update
        employeee 
    set
        department_id=? 
    where
        id=?
Hibernate: 
    update
        employeee 
    set
        department_id=? 
    where
        id=?
Hibernate: 
    update
        employeee 
    set
        department_id=? 
    where
        id=?
		 * 
		 */

	}
	
	public static void main(String[] args) {
		insertData();
	}

}
