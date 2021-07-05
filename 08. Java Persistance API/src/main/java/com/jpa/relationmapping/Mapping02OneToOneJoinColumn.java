package com.jpa.relationmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
 * Demonstrate One To One Relationship Unidirectional Join Mapping (with other than primary key)
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
 * 	6.	Notice Annotation @OneToOne that describe in Order Entity Class
 * 
 * 	7.	To retrieve both table that join together uses @JoinColumn Annotation
 *  
 *  8.	ReferencedColumnName attribute in @JoinColumn Annotation defined a reference a particular column from a table as foreign key constrain of other table, which not necessary to a primary key, however it must be unique column
 *  	notice the reference column is marked with Annotation @OneToOne  
 * 
 *  Mapping : 
 *  	transactions (transaction_no) <-> purchase_products (invoice_key)
 *
 */
public class Mapping02OneToOneJoinColumn {

	/**
	 * 
Hibernate: 
    
    create table transactions (
       id integer not null auto_increment,
        amount float,
        transaction_no varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    alter table transactions 
       add constraint UK_2owah6sjdi57whllc89lox1v9 unique (transaction_no)       
	 * 
	 *
	 */
	@Entity(name="Transactions")
	@Table(name="transactions")
	public static class Transaction implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private Float amount;
		
		
		@Column(name="transaction_no", unique = true)
		private String transactionNumber;
		
		public Transaction() {
		}

		public Transaction(Float amount) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			this.amount = amount;
			
			// make sure this column has a unique value
			this.transactionNumber = String.format("%s%s", sdf.format(new Date()), UUID.randomUUID().toString());
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
			return String.format(" { %d, %s, %,.4f} ", this.id, this.transactionNumber, this.amount);
		}

	}
	
	/**
	 * 
	 *   
Hibernate: 
    
    create table purchase_products (
       id integer not null auto_increment,
        order_date date,
        product varchar(255),
        quantity integer,
        invoice_key varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    alter table purchase_products 
       add constraint FKoo4rdwabgw9ngqigd4vme4fh3 
       foreign key (invoice_key) 
       references transactions (transaction_no) 
       
	 *       
	 *   notice : "invoice_key" is reference key    
	 *
	 */
	@Entity(name = "Purchases")
	@Table(name = "purchase_products")
	public static class Purchases implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String product;
		private Integer quantity;
		
		@Temporal(TemporalType.DATE)
		@Column(name = "order_date")
		private Date orderDate;
		
		@OneToOne
		@JoinColumn(name = "invoice_key", referencedColumnName = "transaction_no")
		private Transaction invoice;

		public Purchases() {
		}

		public Purchases(String product, Integer quantity, Date orderDate) {
			this.product = product;
			this.quantity = quantity;
			this.orderDate = orderDate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getProduct() {
			return product;
		}

		public void setProduct(String product) {
			this.product = product;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		public Transaction getInvoice() {
			return invoice;
		}

		public void setInvoice(Transaction invoice) {
			this.invoice = invoice;
		}

		@Override
		public String toString() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateText = null;
			if (this.orderDate != null) dateText = sdf.format(orderDate);
			
			String invoiceText = null;
			if (this.invoice != null) invoiceText = invoice.toString();
			
			return String.format(" { %d, %s, %d, %s, %s } ", this.id, this.product, this.quantity, dateText, invoiceText);
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
	        mapping02o0_.id as id1_25_0_,
	        mapping02o0_.invoice_key as invoice_5_25_0_,
	        mapping02o0_.order_date as order_da2_25_0_,
	        mapping02o0_.product as product3_25_0_,
	        mapping02o0_.quantity as quantity4_25_0_,
	        mapping02o1_.id as id1_26_1_,
	        mapping02o1_.amount as amount2_26_1_,
	        mapping02o1_.transaction_no as transact3_26_1_ 
	    from
	        purchase_products mapping02o0_ 
	    left outer join
	        transactions mapping02o1_ 
	            on mapping02o0_.invoice_key=mapping02o1_.transaction_no 
	    where
	        mapping02o0_.id=?
        	 
			 * 
			 */
			Purchases orderOne = em.find(Purchases.class, 1);
			System.out.println(orderOne);
			
			Purchases orderTwo = em.find(Purchases.class, 2);
			System.out.println(orderTwo);
			
			List<Purchases> orders = em.createQuery("SELECT a FROM Purchases a", Purchases.class).getResultList();
			System.out.println(orders);
			
			/**
			 * 
		select * from transactions;
		---------- ------------ ------------------------------
		id         amount       transaction_no                
		---------- ------------ ------------------------------
		1          1335.0       TRANSACTION-20210507-01       
		2          849.0        TRANSACTION-20201113-21       
		3          1398.0       TRANSACTION-20190402-43       
		4          477.0        TRANSACTION-20200831-74       
		
		select * from purchase_products;
		---------- --------------- ------------------------------------ ---------- ------------------------------
		id         order_date      product                              quantity   invoice_key                   
		---------- --------------- ------------------------------------ ---------- ------------------------------
		1          2021-05-07      iMac 24-inch M1 7-core GPU 256GB     1          TRANSACTION-20210507-01       
		2          2020-11-13      iPhone 12                            1          TRANSACTION-20201113-21       
		3          2019-04-02      Apple Watch 6                        2          TRANSACTION-20190402-43       
		4          2020-08-31      Original AirPod                      3          TRANSACTION-20200831-74       
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
		new Mapping02OneToOneJoinColumn().runMain();
	}

}
