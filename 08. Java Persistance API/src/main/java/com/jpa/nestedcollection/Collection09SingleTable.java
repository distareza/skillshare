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
 * Demonstrate how existing inheritance relationship between based entites and its derived class can be setup as the default inheritance type  that is SINGLE_TABLE inheritance
 * The derived class entites will all be stored in the same table
 *
 */
public class Collection09SingleTable {

	@Entity(name = "tbl_employees")
	public static class Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		
		private String name;

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
		
	}
	
	@Entity(name = "tbl_Fulltimeemployees")
	public static class FulltimeEmployee extends Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Integer salary;
		
		@ManyToOne
		@JoinColumn(name = "department_id")
		private Department depoartment;

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

		public Department getDepoartment() {
			return depoartment;
		}

		public void setDepoartment(Department depoartment) {
			this.depoartment = depoartment;
		}
		
		
	}

	@Entity(name = "tbl_Contractemployees")
	public static class ContractEmployee extends Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Integer hourlyPay;
		
		@ManyToOne
		@JoinColumn(name = "vendor_id")
		private Vendor vendor;

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

		public Vendor getVendor() {
			return vendor;
		}

		public void setVendor(Vendor vendor) {
			this.vendor = vendor;
		}
		
	}
	
	@Entity(name = "tbl_departments")
	public static class Department implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinColumn(name = "department_id")
		private Set<FulltimeEmployee> employees;
		
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

		public Set<FulltimeEmployee> getEmployees() {
			return employees;
		}

		public void addEmployees(FulltimeEmployee employee) {
			if (this.employees == null) this.employees = new HashSet<>();
			this.employees.add(employee);
		}
		
	}

	@Entity(name = "tbl_vendors")
	public static class Vendor implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinColumn(name = "vendor_id")
		private Set<ContractEmployee> employees;

		public Vendor() {
		}

		public Vendor(String name) {
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

		public Set<ContractEmployee> getEmployees() {
			return employees;
		}

		public void addEmployees(ContractEmployee employee) {
			if (this.employees == null) this.employees = new HashSet<>();
			this.employees.add(employee);
		}
		
	}
	

	/**
	 * Demonstrate how hibernate generate the query statement
	 * 
Hibernate: 
    
    create table tbl_employees (
       DTYPE varchar(31) not null,	-- DTYPE = discriminator type
        id integer not null,
        name varchar(255),
        salary integer,
        hourlyPay integer,
        department_id integer,
        vendor_id integer,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl_departments (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM    
    
    create table tbl_vendors (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    

    alter table tbl_employees 
       add constraint FKt7f0eccsg9eujld961h8nfinj 
       foreign key (department_id) 
       references tbl_departments (id)
    
    alter table tbl_employees 
       add constraint FK2rellipyokgrt4qhw7crw7q5n 
       foreign key (vendor_id) 
       references tbl_vendors (id)
               
	 * 
	 * Notice tbl_Fulltimeemployees and tbl_Contractemployees are not created as tabkes in database
	 * instead it is created as DTYPE fields or Disciminatior Type Field
	 * 
	 * 
	 * 
	 */
	public static void insertData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit");
		EntityManager em = factory.createEntityManager();
		
		try {
			em.getTransaction().begin();
			FulltimeEmployee alice = new FulltimeEmployee("Alice", 50000);
			FulltimeEmployee ben = new FulltimeEmployee("Ben", 45000);
			
			ContractEmployee cora = new ContractEmployee("Cora", 50);
			ContractEmployee dennis = new ContractEmployee("Dennis", 60);
			
			Department engineering = new Department("Engineering");
			Vendor admin = new Vendor("Administrative");
			
			engineering.addEmployees(alice);
			engineering.addEmployees(ben);
			admin.addEmployees(cora);
			admin.addEmployees(dennis);
			
			em.persist(engineering);
			em.persist(admin);
			
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
select * from tbl_employees;
DTYPE                           id         name              salary     hourlyPay  department_id vendor_id  
------------------------------- ---------- ----------------- ---------- ---------- ------------- ---------- 
tbl_Fulltimeemployees           1          Alice             50000	               1			
tbl_Fulltimeemployees           2          Ben               45000                 1                        
tbl_Contractemployees           3          Cora                         50                       1          
tbl_Contractemployees           4          Dennis                       60                       1          


select * from tbl_departments;
id         name             
---------- ----------------- 
1          Engineering      

select * from tbl_vendors;
id         name                                                                                                                                                                                                                                                            
---------- ----------------- 
1          Administrative   

		 * 
		 */
	}
	
	public static void main (String args[]) {
		insertData();
	}
}
