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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship Bidirectional Mapping with Mapped By parameter
 * Hibernate modeled the mapping one-to-one relationship using foreign key constraints, 
 * The possible representations to model a one-to-one mapping relationship are
 * 	--> 1. Using a foreign key reference to associated entities
 * 		2. By having the entities share a primary key
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
 *  	notice that Annotation @OneToOne is mapped on both table
 *  		Field in Entity #1 [transactionStatement] 	is set with @OneToOne annotation and added the mappedBy attribute with "reference field name of the Entity #2" ( TransactionStatements.bills )
 *  		Field in Entity #2 [bills] 					is set with @OneToOne annotation that provided the field reference for Entity #1  (Entity #2 --> Owning Entity)
 *  
 *  Mapping : 
 *  	bills (id) <-> trans_statement (bill_Id)
 *
 */
public class Mapping03OneToOneBidirectional {

	/**
	 * 
	 
Hibernate: 
    
    create table bills (
       id integer not null auto_increment,
        amount float,
        primary key (id)
    ) engine=MyISAM
    
	 * 
	 *
	 */
	@Entity(name="Bills")
	@Table(name="bills")
	public static class Bills implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private Float amount;
		
		@OneToOne(mappedBy = "bills")
		private TransactionStatements transactionStatement;
		
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

		
		@Override
		public String toString() {
			return String.format(" { %d, %,.4f} ", this.id, this.amount);
		}

	}
	
	/**
	 * 
	 *   
Hibernate: 
    
    create table trans_statement (
       id integer not null auto_increment,
        item varchar(255),
        payment_date date,
        bill_Id integer,
        primary key (id)
    ) engine=MyISAM
    
    alter table trans_statement 
       add constraint FKag569c5ryu5mx57ck8b43njps 
       foreign key (bill_Id) 
       references bills (id)   
       
	 *       
	 *   notice : "invoice_id" is reference key    
	 *
	 */
	@Entity(name = "TransStatements")
	@Table(name = "trans_statement")
	public static class TransactionStatements implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String item;
		
		@Column(name = "payment_date")
		@Temporal(TemporalType.DATE)
		private Date payementDate;
		
		@OneToOne
		@JoinColumn(name = "bill_Id")
		private Bills bills;

		public TransactionStatements() {
		}

		public TransactionStatements(String product, Date paymentDate) {
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
	        mapping03o0_.id as id1_25_0_,
	        mapping03o0_.bill_Id as bill_Id4_25_0_,
	        mapping03o0_.item as item2_25_0_,
	        mapping03o0_.payment_date as payment_3_25_0_,
	        mapping03o1_.id as id1_3_1_,
	        mapping03o1_.amount as amount2_3_1_ 
	    from
	        trans_statement mapping03o0_ 
	    left outer join
	        bills mapping03o1_ 
	            on mapping03o0_.bill_Id=mapping03o1_.id 
	    where
	        mapping03o0_.id=?
	         
			 * 
			 */
			TransactionStatements orderOne = em.find(TransactionStatements.class, 1);
			System.out.println(orderOne);
			
			TransactionStatements orderTwo = em.find(TransactionStatements.class, 2);
			System.out.println(orderTwo);
			
			
			List<TransactionStatements> trxs = em.createQuery("SELECT a FROM TransStatements a", TransactionStatements.class).getResultList();
			System.out.println(trxs);
			
			List<Bills> bills = em.createQuery("SELECT a FROM Bills a", Bills.class).getResultList();
			System.out.println(bills);
			
			/**
			 * 

select * from bills;
---------- ------------ 
id         amount       
---------- ------------ 
1          182.55       
2          5.0          
3          129.15       
4          30.0         

select * from trans_statement;
---------- ------------------------------- ----------------- ---------- 
id         item                            payment_date      bill_Id    
---------- ------------------------------- ----------------- ---------- 
1          Electricity Usage               2020-12-05        1          
2          Water Charges                   2020-12-01        2          
3          Internet Broadband Charge       2020-12-07        3          
4          Phone Usage                     2020-12-15        4          


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
		new Mapping03OneToOneBidirectional().runMain();
	}

}
