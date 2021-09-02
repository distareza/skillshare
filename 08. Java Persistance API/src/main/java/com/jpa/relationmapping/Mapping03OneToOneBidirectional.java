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
 *  		Field in Entity #1 [Bills] 					is set with @OneToOne annotation and added the "mappedBy" attribute with "reference field name of the Entity #2" ( TransactionStatements.billing )
 *  		Field in Entity #2 [TransactionStatement] 	is set with @OneToOne annotation and @JoinColumn Annotation to reference a column for Entity #1  (Entity #2 )
 *  
 *  Mapping : 
 *  	trans_statement (bill_id) <--> bills (id) 
 *  
 *  Table Representation, "trans_statement" table is having foreign key "bill_id", which reference to primary key of "bills" table column "id"
 *	
 *	JPA Entity Class representation: 
 *		TransactionStatements Entity is able to retrieve its Bill Entity, with getBilling() method
 *		Bills Entity is able to retrieve its TransactionStatement Entity, with getTransactionStatement() method
 *
 *  	TransStatements Entity declaring "OneToOne" mapping annotation with "JoinColumn" annotation to mapped a Reference Entity via column "bill_id" 
 *  	Bills Entity declaring "OneToOne" mapping annotation with "mappedBy" attribute as a signals key for relationship on other side.
 *  		MappedBy signals hibernate that the key for the relationship is on the other side.
 *  		This means that although you link 2 tables together, only 1 of those tables has a foreign key constraint to the other one. 
 *  		MappedBy allows you to still link from the table not containing the constraint to the other table.
 *  		
 *
 */
public class Mapping03OneToOneBidirectional {

	/**
	 * 
	 ==================================================================
    create table bills (
       id integer not null auto_increment,
        amount float,
        primary key (id)
    ) engine=MyISAM
	==================================================================    
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
		
		@OneToOne(mappedBy = "billing")
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

		public TransactionStatements getTransactionStatement() {
			return transactionStatement;
		}

		public void setTransactionStatement(TransactionStatements transactionStatement) {
			this.transactionStatement = transactionStatement;
		}

		@Override
		public String toString() {
			return String.format(" { %d, %,.4f } ", this.id, this.amount);
		}

	}
	
	/**
	 * 
	==================================================================    
    create table trans_statement (
       id integer not null auto_increment,
        item varchar(255),
        payment_date date,
        bill_id integer,
        primary key (id)
    ) engine=MyISAM
    
    alter table trans_statement 
       add constraint FKag569c5ryu5mx57ck8b43njps 
       foreign key (bill_id) 
       references bills (id)   
   ==================================================================
	 *       
	 *   notice : "bill_id" is reference key    
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
		@JoinColumn(name = "bill_id")
		private Bills billing;

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

		public Bills getBilling() {
			return billing;
		}

		public void setBilling(Bills billing) {
			this.billing = billing;
		}

		@Override
		public String toString() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateText = null;
			if (this.payementDate != null) dateText = sdf.format(payementDate);
			return String.format(" { %d, %s, %s } ", this.id, this.item, dateText);
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;

	/**
	 * 1. Find transaction
	 * 	  trxOne = em.find(TransactionStatements.class, 1)
	 * 	  System.out.println(trxOne);
	==================================================================
    select
        mapping03o0_.id as id1_63_0_,
        mapping03o0_.bill_id as bill_id4_63_0_,
        mapping03o0_.item as item2_63_0_,
        mapping03o0_.payment_date as payment_3_63_0_,
        mapping03o1_.id as id1_3_1_,
        mapping03o1_.amount as amount2_3_1_ 
    from
        trans_statement mapping03o0_ 
    left outer join
        bills mapping03o1_ 
            on mapping03o0_.bill_id=mapping03o1_.id 
    where
        mapping03o0_.id=?	-- notice the filter column 
	==================================================================
	* 
	* 2. get the billing from transaction
	*	 System.out.println(trxOne.getBilling());
	==================================================================
    select
        mapping03o0_.id as id1_63_1_,
        mapping03o0_.bill_id as bill_id4_63_1_,
        mapping03o0_.item as item2_63_1_,
        mapping03o0_.payment_date as payment_3_63_1_,
        mapping03o1_.id as id1_3_0_,
        mapping03o1_.amount as amount2_3_0_ 
    from
        trans_statement mapping03o0_ 
    left outer join
        bills mapping03o1_ 
            on mapping03o0_.bill_id=mapping03o1_.id 
    where
        mapping03o0_.bill_id=? -- notice the filter column
	==================================================================
	*
	* 3. Find The Billing Record, along with its transaction:
	* 	 Bills billOne = em.find(Bills.class, 3);
	* 	 System.out.println(billTwo);
	* 	 System.out.println(billOne.getTransactionStatement());
	==================================================================
	select
	   mapping03o0_.id as id1_3_0_,
	   mapping03o0_.amount as amount2_3_0_,
	   mapping03o1_.id as id1_63_1_,
	   mapping03o1_.bill_id as bill_id4_63_1_,
	   mapping03o1_.item as item2_63_1_,
	   mapping03o1_.payment_date as payment_3_63_1_ 
	from
	   bills mapping03o0_ 
	left outer join
	   trans_statement mapping03o1_ 
	       on mapping03o0_.id=mapping03o1_.bill_id 
	where
	   mapping03o0_.id=?
	==================================================================     
	 * 
	 */

	public void retrieveData() {
		System.out.println("retrieve data");
		
		TransactionStatements trxOne = em.find(TransactionStatements.class, 1);
		System.out.println(trxOne);					// { 1, Electricity Usage, 05-12-2020 }
		System.out.println(trxOne.getBilling());	// { 1, 182.5500 } 
		TransactionStatements trxTwo = em.find(TransactionStatements.class, 2);
		System.out.println(trxTwo);					// { 2, Water Charges, 01-12-2020 } 
		System.out.println(trxTwo.getBilling());	// { 2, 5.0000 } 
		
		Bills billOne = em.find(Bills.class, 3);
		System.out.println(billOne);								// { 3, 129.1500 }
		System.out.println(billOne.getTransactionStatement());		// { 3, Internet Charge, 07-12-2020 } 

		Bills billTwo = em.find(Bills.class, 4);		
		System.out.println(billTwo);								// { 4, 30.0000 }  
		System.out.println(billTwo.getTransactionStatement());		// { 4, Phone Usage, 15-12-2020 } 

		List<TransactionStatements> trxs = em.createQuery("SELECT a FROM TransStatements a", TransactionStatements.class).getResultList();
		System.out.println(trxs);
		
		List<Bills> bills = em.createQuery("SELECT a FROM Bills a", Bills.class).getResultList();
		System.out.println(bills);
	}
	
	/**
	 * 
	==================================================================
    insert 
    into
        trans_statement
        (bill_id, item, payment_date) 
    values
        (?, ?, ?)
    ==================================================================
    insert 
    into
        bills
        (amount) 
    values
        (?)
	==================================================================
    update
        trans_statement 
    set
        bill_id=?,
        item=?,
        payment_date=? 
    where
        id=?
   	==================================================================
	 * 
	 */
	public void insertData() throws Exception {
		em.getTransaction().begin();
		
		TransactionStatements newTrx = new TransactionStatements("Road Tax", new SimpleDateFormat("dd-MM-yyyy").parse("05-08-2021"));
		Bills newBill = new Bills(249.99f);
		newTrx.setBilling(newBill);
		
		em.persist(newTrx);
		em.persist(newBill);
		
		em.getTransaction().commit();
	}
	
	public void runMain() {
		factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		em = factory.createEntityManager();

		try {
			
			insertData();
			retrieveData();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}

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
	5          249.99       
	
	select * from trans_statement;
	---------- -------------------------- ------------------------- ----------
	id         item                       payment_date              bill_id    
	---------- -------------------------- ------------------------- ---------- 
	1          Electricity Usage          2020-12-05                1          
	2          Water Charges              2020-12-01                2          
	3          Internet Charge            2020-12-07                3          
	4          Phone Usage                2020-12-15                4          
	5          Road Tax                   2021-08-05                5          
		 * 
		 */

	}
	
	
	public static void main(String[] args) {
		new Mapping03OneToOneBidirectional().runMain();
	}

}
