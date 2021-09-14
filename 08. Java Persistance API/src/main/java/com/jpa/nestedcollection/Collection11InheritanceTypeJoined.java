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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

/**
 * Demonstrate use the JOINED inheritance to store entities in multiple tables
 * 
 *
 */
public class Collection11InheritanceTypeJoined {
	
	@Entity(name = "tbl3_employees")
	@Inheritance(strategy = InheritanceType.JOINED)
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
	
	@Entity(name = "tbl3_departments")
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
	
	@Entity(name = "tbl3_Fulltimeemployees")
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
	
	@Entity(name = "tbl3_Contractemployees")
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
    
    create table tbl3_employees (
       id integer not null,
        name varchar(255),
        department_id integer,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl3_Fulltimeemployees (
       salary integer,
        id integer not null,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl3_Contractemployees (
       hourlyPay integer,
        id integer not null,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl3_departments (
       id integer not null auto_increment,
        name varchar(255),
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

			FulltimeEmployee alice = new FulltimeEmployee("Alice", 50000);
			FulltimeEmployee ben = new FulltimeEmployee("Ben", 45000);

			ContractEmployee cora = new ContractEmployee("Cora", 50);
			ContractEmployee dennis = new ContractEmployee("Dennis", 60);

			Employee elsa = new Employee("Elsa");
			
			engineering.addEmployees(alice);
			engineering.addEmployees(ben);
			engineering.addEmployees(cora);
			engineering.addEmployees(dennis);
			engineering.addEmployees(elsa);
			
			em.persist(engineering);
			
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
select * from tbl3_employees;
id         name        department_id 
---------- ----------- ------------- 
1          Elsa        1             
2          Alice       1             
3          Cora        1             
4          Ben         1             
5          Dennis      1             

select * from tbl3_Fulltimeemployees;
salary     id         
---------- ---------- 
50000      2          
45000      4          

select * from tbl3_Contractemployees;
hourlyPay  id         
---------- ---------- 
50         3          
60         5          

select * from tbl3_departments;
id         name        
---------- --------------- 
1          Engineering    
		 * 
		 */

	}

	/**
	 * 
Hibernate: 
    select
        collection0_.id as id1_84_0_,
        collection0_.department_id as departme3_84_0_,
        collection0_.name as name2_84_0_,
        collection0_1_.hourlyPay as hourlyPa1_82_0_,
        collection0_2_.salary as salary1_85_0_,
        case 
            when collection0_1_.id is not null then 1 
            when collection0_2_.id is not null then 2 
            when collection0_.id is not null then 0 
        end as clazz_0_,
        collection1_.id as id1_83_1_,
        collection1_.name as name2_83_1_ 
    from
        tbl3_employees collection0_ 
    left outer join
        tbl3_Contractemployees collection0_1_ 
            on collection0_.id=collection0_1_.id 
    left outer join
        tbl3_Fulltimeemployees collection0_2_ 
            on collection0_.id=collection0_2_.id 
    left outer join
        tbl3_departments collection1_ 
            on collection0_.department_id=collection1_.id 
    where
        collection0_.id=?
        
Hibernate: 
    select
        collection0_.id as id1_84_0_,
        collection0_1_.department_id as departme3_84_0_,
        collection0_1_.name as name2_84_0_,
        collection0_.salary as salary1_85_0_,
        collection1_.id as id1_83_1_,
        collection1_.name as name2_83_1_ 
    from
        tbl3_Fulltimeemployees collection0_ 
    inner join
        tbl3_employees collection0_1_ 
            on collection0_.id=collection0_1_.id 
    left outer join
        tbl3_departments collection1_ 
            on collection0_1_.department_id=collection1_.id 
    where
        collection0_.id=?
        
Hibernate: 
    select
        employees0_.department_id as departme3_84_0_,
        employees0_.id as id1_84_0_,
        employees0_.id as id1_84_1_,
        employees0_.department_id as departme3_84_1_,
        employees0_.name as name2_84_1_,
        employees0_1_.hourlyPay as hourlyPa1_82_1_,
        employees0_2_.salary as salary1_85_1_,
        case 
            when employees0_1_.id is not null then 1 
            when employees0_2_.id is not null then 2 
            when employees0_.id is not null then 0 
        end as clazz_1_ 
    from
        tbl3_employees employees0_ 
    left outer join
        tbl3_Contractemployees employees0_1_ 
            on employees0_.id=employees0_1_.id 
    left outer join
        tbl3_Fulltimeemployees employees0_2_ 
            on employees0_.id=employees0_2_.id 
    where
        employees0_.department_id=?
	 * 
	 */
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			Employee e = em.find(Employee.class, 1);
			System.out.println(e.toString());
			
			FulltimeEmployee ft = em.find(FulltimeEmployee.class, 2);
			System.out.println(ft.toString());
			
			ContractEmployee ct = em.find(ContractEmployee.class, 3);
			System.out.println(ct.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output :
		 * 
{ 1, Elsa, com.jpa.nestedcollection.Collection11InheritanceTypeJoined$Employee }
{ name=Alice, salary=50000, com.jpa.nestedcollection.Collection11InheritanceTypeJoined$FulltimeEmployee }
{ name=Cora, hourlyPay=50, com.jpa.nestedcollection.Collection11InheritanceTypeJoined$ContractEmployee }
		 * 
		 */

	}
	public static void main(String[] args) {
		//insertData();
		retrieveData();
	}

}
