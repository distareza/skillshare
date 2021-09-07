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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

/**
 * Demonstrate How to inherited classes map to the underlying database
 * Use annotation @MappedSuperclass to allow Employee class in an inheritance relationship within your object oriented programming model
 * A MappedSuperclass cannot be the target of entity relationships .
 * Cannot have a one to many or many to one relationship with this class.
 * 
 * 
 */
public class Collection08MappedSuperClass {
	
	@MappedSuperclass
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

		@Override
		public String toString() {
			return String.format("{ id=%d, name=%s }", this.id, this.name);
		}
		
		
	}
	
	@Entity(name = "tbl_FulltimeEmployees")
	public static class FulltimeEmployee extends Employee implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private Integer salary;
		
		@ManyToOne
		@JoinColumn(name = "department_id")
		private Department department;

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

		public Department getDepartment() {
			return department;
		}

		public void setDepartment(Department department) {
			this.department = department;
		}
		
		@Override
		public String toString() {
			return String.format("{ id=%d, name=%s, salary=%,d }", this.getId(), this.getName(), this.getSalary());
		}

	}
	
	@Entity(name = "tbl_ContractEmployees")
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
	
		@Override
		public String toString() {
			return String.format("{ id=%d, name=%s, hourlyPay=%,d }", this.getId(), this.getName(), this.getHourlyPay());
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
	 * Demonstrate how hibernate is running the application
	 * 
Hibernate: 
    
    create table tbl_ContractEmployees (
       id integer not null,
        name varchar(255),
        hourlyPay integer,
        vendor_id integer,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl_departments (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table tbl_FulltimeEmployees (
       id integer not null,
        name varchar(255),
        salary integer,
        department_id integer,
        primary key (id)
    ) engine=MyISAM
    
    create table tbl_vendors (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    alter table tbl_ContractEmployees 
       add constraint FK8vs6qo6vcyf78vn1x0t8w9umd 
       foreign key (vendor_id) 
       references tbl_vendors (id)
    
    alter table tbl_FulltimeEmployees 
       add constraint FK9mxjl1yc1bqtsntbbxuhdg7tm 
       foreign key (department_id) 
       references tbl_departments (id)    
       
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
		 * 

SELECT * FROM tbl_FulltimeEmployees;
id         name              salary     department_id 
---------- ----------------- ---------- ------------- 
1          Ben               45000      1             
2          Alice             50000      1             

SELECT * FROM tbl_ContractEmployees;
id         name               hourlyPay  vendor_id  
---------- ------------------ ---------- ---------- 
3          Cora               50         1          
4          Dennis             60         1          

SELECT * FROM tbl_departments;
id         name                                                                                                                                                                                                                                                            
---------- ------------------
1          Engineering       


SELECT * FROM tbl_vendors;
id         name                                                                                                                                                                                                                                                            
---------- ------------------ 
1          Administrative    
		 * 
		 * 
		 */
	}
	
	/**
	 * Demonstrate how Mapped SuperClass can not be used for retrieval for search an Entity
	 *  
	 * 
	 */
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			// this causing error
			// 		java.lang.IllegalArgumentException: Unable to locate persister: com.jpa.nestedcollection.Collection08MappedSuperClass$Employee"
			// because Employee is Mapped SuperClass thus it can not be accessed as Entity
			Employee alice = em.find(Employee.class, 1);			
			System.out.println(alice.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			Employee alice = em.find(FulltimeEmployee.class, 1);
			System.out.println(alice.toString());
			
			/** 
			 * output :
			 *
Hibernate: 
    select
        collection0_.id as id1_80_0_,
        collection0_.name as name2_80_0_,
        collection0_.department_id as departme4_80_0_,
        collection0_.salary as salary3_80_0_,
        collection1_.id as id1_79_1_,
        collection1_.name as name2_79_1_ 
    from
        tbl_FulltimeEmployees collection0_ 
    left outer join
        tbl_departments collection1_ 
            on collection0_.department_id=collection1_.id 
    where
        collection0_.id=?
Hibernate: 
    select
        employees0_.department_id as departme4_80_0_,
        employees0_.id as id1_80_0_,
        employees0_.id as id1_80_1_,
        employees0_.name as name2_80_1_,
        employees0_.department_id as departme4_80_1_,
        employees0_.salary as salary3_80_1_ 
    from
        tbl_FulltimeEmployees employees0_ 
    where
        employees0_.department_id=?
        
{ id=1, name=Ben, salary=45,000 }

			 * 
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		em.close();
		factory.close();
		
	}

	public static void main(String[] args) {
		//insertData();
		retrieveData();
	}

}
