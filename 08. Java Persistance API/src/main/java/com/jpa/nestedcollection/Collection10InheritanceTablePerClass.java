package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

/**
 * Demonstrate the use of TABLE_PER_CLASS inheritance to store entities in multiple tables
 * For Every Class maked with annotation Entity a seperate table is created
 * 
 * demonstrate TABLE_PER_CLASS inheritance is often is in the real world, because of performance when hibernate retrieve data that always uses union
 * 
 */
public class Collection10InheritanceTablePerClass {

	
	/**
	 * @author mwirman2
	 *
	 */
	@Entity(name = "tbl2_employees")
	@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
	public static class Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		
		private String name;

		@ManyToOne
		@JoinColumn(name = "department_id")
		public Department department;
		
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
			return String.format("{ %s, %s, %s }", this.getId(), this.getName(), this.getClass().getName());
		}
		
	}

	@Entity(name = "tbl2_departments")
	public static class Department implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

		public void addEmployees(Employee employee) {
			if (this.employees == null) this.employees = new HashSet<>();
			this.employees.add(employee);
		}
		
	}
	
	@Entity(name = "tbl2_Fulltimeemployees")
	public static class FulltimeEmployee extends Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Integer salary;
		
		public FulltimeEmployee() {
		}

		public FulltimeEmployee(String name, Integer salary) {
			super(name);
			this.salary = salary;
		}

		public Integer getSalary() {
			return salary;
		}

		public void setSalary(Integer salary) {
			this.salary = salary;
		}

		@Override
		public String toString() {
			return String.format("{ name=%s, salary=%d, %s }", this.getName(), this.getSalary(), this.getClass().getName());
		}
		
	}
	
	@Entity(name = "tbl2_Contractemployees")
	public static class ContractEmployee extends Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Integer hourlyPay;
		
		public ContractEmployee() {
		}

		public ContractEmployee(String name, Integer hourlyPay) {
			super(name);
			this.hourlyPay = hourlyPay;
		}

		public Integer getHourlyPay() {
			return hourlyPay;
		}

		public void setHourlyPay(Integer hourlyPay) {
			this.hourlyPay = hourlyPay;
		}

		@Override
		public String toString() {
			return String.format("{ name=%s, hourlyPay=%d, %s }", this.getName(), this.getHourlyPay(), this.getClass().getName());
		}
		
	}
	
	/**
	 * 
Hibernate: 
    
    create table tbl2_Contractemployees (
       id integer not null,
        name varchar(255),
        department_id integer,
        hourlyPay integer,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl2_departments (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table tbl2_employees (
       id integer not null,
        name varchar(255),
        department_id integer,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl2_Fulltimeemployees (
       id integer not null,
        name varchar(255),
        department_id integer,
        salary integer,
        primary key (id)
    ) engine=MyISAM
    
	 * 
	 */
	public static void insertData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Department engineering = new Department("Engineering");
			
			Employee elsa = new Employee("Elsa");
			engineering.addEmployees(elsa);
			
			FulltimeEmployee alice = new FulltimeEmployee("Alice", 50000);
			alice.setDepartment(engineering);
			ContractEmployee cora = new ContractEmployee("Cora", 50);
			cora.setDepartment(engineering);
			
			em.persist(engineering);
			em.persist(alice);
			em.persist(cora);
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output :
		 * 

select * from tbl2_employees;
id         name      department_id 
---------- --------- ------------- 
1          Elsa      1             

select * from tbl2_Fulltimeemployees;
id         name      department_id salary     
---------- --------- ------------- ---------- 
2          Alice     1             50000      

select * from tbl2_Contractemployees;
id         name      department_id hourlyPay  
---------- --------- ------------- ---------- 
3          Cora      1             50         

select * from tbl2_departments;
id         name                                                                                                                                                                                                                                                            
---------- --------------
1          Engineering   

S
		 * 
		 */
	}
	
	/**
	 * When retrieving TABLE_PER_CLASS inheritence, hibernate uses Union causes the poor performance 
	 * TABLE_PER_CLASS is not often used in the real world
	 * 
Hibernate: 
    select
        collection0_.id as id1_80_,
        collection0_.department_id as departme3_80_,
        collection0_.name as name2_80_,
        collection0_.salary as salary1_81_,
        collection0_.hourlyPay as hourlyPa1_78_,
        collection0_.clazz_ as clazz_ 
    from
        ( select
            id,
            name,
            department_id,
            null as salary,
            null as hourlyPay,
            0 as clazz_ 
        from
            tbl2_employees 
        union
        select
            id,
            name,
            department_id,
            salary,
            null as hourlyPay,
            1 as clazz_ 
        from
            tbl2_Fulltimeemployees 
        union
        select
            id,
            name,
            department_id,
            null as salary,
            hourlyPay,
            2 as clazz_ 
        from
            tbl2_Contractemployees 
    ) collection0_
    
Hibernate: 
    select
        collection0_.id as id1_79_0_,
        collection0_.name as name2_79_0_,
        employees1_.department_id as departme3_80_1_,
        employees1_.id as id1_80_1_,
        employees1_.id as id1_80_2_,
        employees1_.department_id as departme3_80_2_,
        employees1_.name as name2_80_2_,
        employees1_.salary as salary1_81_2_,
        employees1_.hourlyPay as hourlyPa1_78_2_,
        employees1_.clazz_ as clazz_2_ 
    from
        tbl2_departments collection0_ 
    left outer join
        (
            select
                id,
                name,
                department_id,
                null as salary,
                null as hourlyPay,
                0 as clazz_ 
            from
                tbl2_employees 
            union
            select
                id,
                name,
                department_id,
                salary,
                null as hourlyPay,
                1 as clazz_ 
            from
                tbl2_Fulltimeemployees 
            union
            select
                id,
                name,
                department_id,
                null as salary,
                hourlyPay,
                2 as clazz_ 
            from
                tbl2_Contractemployees 
        ) employees1_ 
            on collection0_.id=employees1_.department_id 
    where
        collection0_.id=?
	 * 
	 */
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			List<Employee> employees = (List<Employee>) em.createQuery("from tbl2_employees", Employee.class).getResultList();
			for (Employee e : employees) {
				System.out.println(e.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		/**
		 * output :
		 * 
{ 1, Elsa, com.jpa.nestedcollection.Collection10TablePerClass$Employee }
{ name=Alice, salary=50000, com.jpa.nestedcollection.Collection10TablePerClass$FulltimeEmployee }
{ name=Cora, hourlyPay=50, com.jpa.nestedcollection.Collection10TablePerClass$ContractEmployee }
		 * 
		 */
	}
	
	public static void main(String args[]) {
		//insertData();
		retrieveData();
	}
	
}
