package com.jpa.relationmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship with shared Primary key mapping 
 * Hibernate modeled the mapping one-to-one relationship using foreign key constraints, 
 * The possible representations to model a one-to-one mapping relationship are
 * 		1. Using a foreign key reference to associated entities
 * 	-->	2. By having the entities share a primary key
 * 		3. Using a separate mapping table to model entity associations
 *   
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  	Observe data is initialize through insert statement in preloaded-onlineshop-data.sql 
 *  		 <property name="javax.persistence.sql-load-script-source" value="META-INF/preload-query.sql"/>
 *  	insert statements are structure in one line per statement
 *  	only triggered if the schema generation action is set to drop and create
 * 
 *  2.	Observe how the entities classes are defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity class with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Defined how to defined custom Column definition with @Column Annotation
 *  		check how the column name can be altered or adjust,
 *  		Column Field Type, Column length
 *  		specify the Floating point precision (max digit) and scale ( n digit after decimal ) 
 *  
 *  5.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *   
 * 	6.	Notice Annotation @OneToOne that declare at method/fields in both Entity Classes to reference as foreign key 
 * 
 * 	7.	To retrieve both table that join together uses @JoinColumn Annotation
 *  
 *  8.	ReferencedColumnName attribute in @JoinColumn Annotation defined a reference a particular column from a table as foreign key constrain of other table, which not necessary to a primary key, however it must be unique column
 * 
 *  9. Notice One-To-One Bidirectional Mapping : the owning side defines the mapping the referencing side references the mapping
 *  	notice that Annotation @OneToOne is mapped on both table / entities
 *  		Field Entity #1 [statement] is set along with @MapsId annotation indicating that the primary key of the reference Entity #2 will be shared  ( Entity #1 --> Owning Entity ), the PK of Entity #2 will also be PK Entity #1
 *  		Field Entity #2 [bills] 	is set with additional "mappedBy" attribute with a "reference field/member name" of the Entity #2 ( statement )
 *  
 *  
 *  Mapping : 
 *  	purchases_bills (statement_id) <-> purchases_statement (id)
 *
 */
public class Mapping04OneToOneSharedMapping {

	/**
	 * 
Hibernate: 
    
    create table purchases_bills (
       amount float,
        statement_id integer not null,
        primary key (statement_id)
    ) engine=MyISAM
    
    	 
    alter table purchases_bills 
       add constraint FK7ptw0i96n7cwd9q7b6lg4vga5 
       foreign key (statement_id) 
       references purchases_statement (id)
           
	 * 
	 *
	 */
	@Entity(name="PurhaseBills")
	@Table(name="purchases_bills")
	public static class Bills implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		private Integer id;
		
		private Float amount;
		
		@OneToOne
		@MapsId
		private PurchasesStatement statement;
		
		public Bills() {
		}

		public Bills(Float amount) {
			this.amount = amount;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Float getAmount() {
			return amount;
		}

		public void setAmount(Float amount) {
			this.amount = amount;
		}

		
		
		public PurchasesStatement getStatement() {
			return statement;
		}

		public void setStatement(PurchasesStatement statement) {
			this.statement = statement;
		}

		@Override
		public String toString() {
			//return String.format(" { %d, %,.4f} ", this.id, this.amount);
			return String.format(" { %d, %,.4f} ", this.id, this.amount);
		}

	}
	
	/**
	 * 
	 *   
Hibernate: 
    
    create table purchases_statement (
       id integer not null auto_increment,
        item varchar(255),
        payment_date date,
        primary key (id)
    ) engine=MyISAM
       
	 *       
	 *
	 */
	@Entity(name = "PurchasesStatements")
	@Table(name = "purchases_statement")
	public static class PurchasesStatement implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String item;
		
		@Column(name = "payment_date")
		@Temporal(TemporalType.DATE)
		private Date payementDate;
		
		@OneToOne(mappedBy = "statement")
		private Bills bills;

		public PurchasesStatement() {
		}

		public PurchasesStatement(String product, Date paymentDate) {
			this.item = product;
			this.payementDate = paymentDate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public Date getPayementDate() {
			return payementDate;
		}

		public void setPayementDate(Date payementDate) {
			this.payementDate = payementDate;
		}

		public Bills getBills() {
			return bills;
		}

		public void setBills(Bills bills) {
			this.bills = bills;
		}

		@Override
		public String toString() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateText = null;
			if (this.payementDate != null) dateText = sdf.format(payementDate);
			
			String billsText = null;
			if (this.bills != null) billsText = bills.toString();
			
			return String.format(" { %d, %s, %s, %s } ", this.id, this.item, dateText, billsText);
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
	public void runMain() {
		factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		em = factory.createEntityManager();

		try {
			System.out.println("retrieve data");
			
			/**
			 *
			 
    select
        mapping04o0_.id as id1_26_0_,
        mapping04o0_.item as item2_26_0_,
        mapping04o0_.payment_date as payment_3_26_0_,
        mapping04o1_.statement_id as statemen2_25_1_,
        mapping04o1_.amount as amount1_25_1_ 
    from
        purchases_statement mapping04o0_ 
    left outer join
        purchases_bills mapping04o1_ 
            on mapping04o0_.id=mapping04o1_.statement_id 
    where
        mapping04o0_.id=?
	         
			 * 
			 */
			PurchasesStatement orderOne = em.find(PurchasesStatement.class, 1);
			System.out.println(orderOne);
			
			PurchasesStatement orderTwo = em.find(PurchasesStatement.class, 2);
			System.out.println(orderTwo);
			
			
			List<PurchasesStatement> trxs = em.createQuery("SELECT a FROM PurchasesStatements a", PurchasesStatement.class).getResultList();
			System.out.println(trxs);
			
			List<Bills> bills = em.createQuery("SELECT a FROM PurhaseBills a", Bills.class).getResultList();
			System.out.println(bills);
			
			/**
			 * 
			 * 

select * from purchases_bills;
------------ ------------ 
statement_id amount       
------------ ------------ 
10           182.55       
12           5.0          
13           129.15       
14           30.0         

select * from purchases_statement;
---------- ---------------------- ---------------- 
id         item                   payment_date     
---------- ---------------------- ---------------- 
10         Electricity Usage      2020-12-05       
12         Water Charges          2020-12-01       
13         Internet Charge        2020-12-07       
14         Phone Usage            2020-12-15       



			 * 
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	
	public static void main(String[] args) {
		new Mapping04OneToOneSharedMapping().runMain();
	}

}
