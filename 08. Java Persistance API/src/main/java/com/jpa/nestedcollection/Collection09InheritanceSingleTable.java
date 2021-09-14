package com.jpa.nestedcollection;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
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
 * Demonstrate how existing inheritance relationship between based entites and its derived class can be setup as the default inheritance type  that is SINGLE_TABLE inheritance
 * The derived class entites will all be stored in the same table
 * 
 * Type of Inheritance :
 * 	@Inheritance Annotation no need to declare if you want to have default SINGLE_TABLE inheritance
 * 			default DiscramantorColumn : DTYPE , String
 *  
 *  @DesciminatorValue("null") When no Discriminator Value is present when the disciminator value is null , that employee automatically is instantiated as an Employeee based class object
 * 
 */
public class Collection09InheritanceSingleTable {

	@Entity(name = "tbl_employees")
	@Inheritance(strategy = InheritanceType.SINGLE_TABLE)										// *) Optional to define Inheritance Type
	@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING) 	// *) Optional to define Discriminatior Colunmn name (default is DTYPE (String) ) 
	@DiscriminatorValue("null")																	// *) Optional to define any entity in underlying table which has null in the employee_typ field (discriminator column) will be mapped to employee base class entity
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
	@DiscriminatorValue("fulltime") // Optional
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

		@Override
		public String toString() {
			return String.format("{ name=%s, salary=%d }", this.getName(), this.getSalary());
		}
		
	}

	@Entity(name = "tbl_Contractemployees")
	@DiscriminatorValue("contract") // optional
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
			return String.format("{ name=%s, hourlyPay=%d }", this.getName(), this.getHourlyPay());
		}
		
	}
	
	@Entity
	@DiscriminatorValue("not null") // Any record in underlying table which has discriminator value that is not mapped to any of the other entities will be mapped to this entity   
	public static class UnknownEmployee extends Employee implements Serializable {

		private static final long serialVersionUID = 1L;

		public UnknownEmployee() {
		}

		public UnknownEmployee(String name) {
			super(name);
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
	 * 1. Default :
	 * 		Inheritance Type Annotation not declared,
	 * 		Discriminator Column Annotation not declared  
	 *  
Hibernate: 
    
    create table tbl_employees (
       DTYPE varchar(31) not null,	-- DTYPE VARCHAR = default discriminator type
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
	 * 	Notice tbl_Fulltimeemployees and tbl_Contractemployees are not created as tabkes in database
	 * 	instead it is created as DTYPE fields or Disciminatior Type Field
	 * 
	 * 2. Inheritance strategy = SINGLE_TABLE
	 * 		Discriminator Column Name = "employee_type", 
	 * 		Discriminator Type = DiscriminatorType.STRING
	 * 		Discriminator Value = "fulltime" & "contract"  
	 * 
Hibernate:
 
    create table tbl_employees (
       employee_type varchar(31) not null,
        id integer not null,
        name varchar(255),
        salary integer,
        hourlyPay integer,
        department_id integer,
        vendor_id integer,
        primary key (id)
    ) engine=MyISAM
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
		 * 1. Default 
		 * 		Inheritance Type Annotation not declared,
		 * 		Discriminator Column Annotation not declared  
		 * 
select * from tbl_employees;
DTYPE                           id         name              salary     hourlyPay  department_id vendor_id  
------------------------------- ---------- ----------------- ---------- ---------- ------------- ---------- 
tbl_Fulltimeemployees           1          Alice             50000	    (null)     1			 (null)
tbl_Fulltimeemployees           2          Ben               45000      (null)     1             (null)          
tbl_Contractemployees           3          Cora              (null)     50         (null)        1          
tbl_Contractemployees           4          Dennis            (null)     60         (null)        1          


select * from tbl_departments;
id         name             
---------- ----------------- 
1          Engineering      

select * from tbl_vendors;
id         name                                                                                                                                                                                                                                                            
---------- ----------------- 
1          Administrative   
		 *		
		 *		notice DTYPE field is consist of the name of Entity Name 
		 *
		 *
		 * 
		 * 2. Inheritance strategy = SINGLE_TABLE
		 * 		Discriminator Column Name = "employee_type", 
		 * 		Discriminator Type = DiscriminatorType.STRING
		 * 		Discriminator Value = "fulltime" & "contract"  		 
		 * 
select * from tbl_employees;
employee_type                   id         name            salary     hourlyPay  department_id vendor_id  
------------------------------- ---------- --------------- ---------- ---------- ------------- ---------- 
fulltime                        1          Alice           50000      (null)     1             (null)           
fulltime                        2          Ben             45000      (null)     1             (null)
contract                        3          Cora            (null)     50         (null)        1          
contract                        4          Dennis          (null)     60         (null)        1          
		 *  		
		 *  	notice that "employee_type" column (disciminator column) consist of the name of Discriminator Value
		 *  
		 *  
		 */
	}
	
	/**
	 * Demonstrate how to mapped a null and unknown Discriminator values
	 */	
	public static void retrieveData() {
		// insertData(); --> run this first before retrieve data
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			// to Retrive Normal Inheritance Entity 
			Employee alice = em.find(FulltimeEmployee.class, 1);			
			System.out.println(alice.toString());

			/**
			 * Output :
			 * 
Hibernate: 
    select
        collection0_.id as id2_80_0_,
        collection0_.name as name3_80_0_,
        collection0_.department_id as departme6_80_0_,
        collection0_.salary as salary4_80_0_,
        collection1_.id as id1_79_1_,
        collection1_.name as name2_79_1_ 
    from
        tbl_employees collection0_ 
    left outer join
        tbl_departments collection1_ 
            on collection0_.department_id=collection1_.id 
    where
        collection0_.id=? 
        and collection0_.employee_type='fulltime'

    select
        employees0_.department_id as departme6_80_0_,
        employees0_.id as id2_80_0_,
        employees0_.id as id2_80_1_,
        employees0_.name as name3_80_1_,
        employees0_.department_id as departme6_80_1_,
        employees0_.salary as salary4_80_1_ 
    from
        tbl_employees employees0_ 
    where
        employees0_.department_id=?
        
{ name=Alice, salary=50000 }
        
			 *
			 */

			Employee cora = em.find(ContractEmployee.class, 3);			
			System.out.println(cora.toString());

			/**
			 * Output :
			 *  
Hibernate: 
    select
        collection0_.id as id2_80_0_,
        collection0_.name as name3_80_0_,
        collection0_.hourlyPay as hourlyPa5_80_0_,
        collection0_.vendor_id as vendor_i7_80_0_,
        collection1_.id as id1_82_1_,
        collection1_.name as name2_82_1_ 
    from
        tbl_employees collection0_ 
    left outer join
        tbl_vendors collection1_ 
            on collection0_.vendor_id=collection1_.id 
    where
        collection0_.id=? 
        and collection0_.employee_type='contract'

    select
        employees0_.vendor_id as vendor_i7_80_0_,
        employees0_.id as id2_80_0_,
        employees0_.id as id2_80_1_,
        employees0_.name as name3_80_1_,
        employees0_.hourlyPay as hourlyPa5_80_1_,
        employees0_.vendor_id as vendor_i7_80_1_ 
    from
        tbl_employees employees0_ 
    where
        employees0_.vendor_id=?
        
{ name=Cora, hourlyPay=50 }
			 *  
			 */

			/**
			 * execute this oracle query command 
			 * 
alter table  tbl_employees modify employee_type varchar(31);
insert into tbl_employees (employee_type, id, name) values ('temporary', 222, 'Zara');
insert into tbl_employees (employee_type, id, name) values (null, 333, 'Zoe');
			 * 
			 * 
show columns from tbl_employees;
COLUMN_NAME          COLUMN_TYPE   IS_NULLABLE COLUMN_KEY COLUMN_DEFAULT      EXTRA                          
-------------------- ------------- ----------- ---------- ------------------- ------------------------------ 
employee_type        varchar(31)   YES                                       
id                   int(11)       NO          PRI                           
name                 varchar(255)  YES                                       
salary               int(11)       YES                                       
hourlyPay            int(11)       YES                                       
department_id        int(11)       YES         MUL                           
vendor_id            int(11)       YES         MUL                           

select * from tbl_employees;
employee_type                   id         name        salary     hourlyPay  department_id vendor_id  
------------------------------- ---------- ----------- ---------- ---------- ------------- ---------- 
fulltime                        1          Alice       50000      (null)     1              (null)                
fulltime                        2          Ben         45000      (null)     1              (null)                
contract                        3          Cora        (null)      50        (null)         1          
contract                        4          Dennis      (null)      60        (null)         1          
temporary                       222        Zara        (null)      (null)    (null)         (null)          
(null)                          333        Zoe         (null)      (null)    (null)         (null)      

			 * 
			 */
			
			Employee zoe = em.find(Employee.class, 333);
			System.out.println(String.format("employee type is null : %s", zoe.getName()));
			
			/**
			 * output :
			 * 
Hibernate: 
    select
        collection0_.id as id2_80_0_,
        collection0_.name as name3_80_0_,
        collection0_.department_id as departme6_80_0_,
        collection0_.salary as salary4_80_0_,
        collection0_.hourlyPay as hourlyPa5_80_0_,
        collection0_.vendor_id as vendor_i7_80_0_,
        collection0_.employee_type as employee1_80_0_,
        collection1_.id as id1_82_1_,
        collection1_.name as name2_82_1_,
        collection2_.id as id1_79_2_,
        collection2_.name as name2_79_2_ 
    from
        tbl_employees collection0_ 
    left outer join
        tbl_vendors collection1_ 
            on collection0_.vendor_id=collection1_.id 
    left outer join
        tbl_departments collection2_ 
            on collection0_.department_id=collection2_.id 
    where
        collection0_.id=?
        
employee type is null : Zoe
			 *
			 */
			
			UnknownEmployee zara = em.find(UnknownEmployee.class, 222);
			System.out.println(String.format("employee_type is not known value : %s", zara.getName()));
			
			/**
			 * output :
			 * 
Hibernate: 
    select
        collection0_.id as id2_80_0_,
        collection0_.name as name3_80_0_ 
    from
        tbl_employees collection0_ 
    where
        collection0_.id=? 
        and collection0_.employee_type is not null
        
employee_type is not known value : Zara			  

			 * 
			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void main (String args[]) {
		//insertData();
		retrieveData();
	}
}
