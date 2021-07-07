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
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship Unidirectional Mapping (with a primary key)
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
 *  6.	Notice Annotation @OneToOne that declare in one of method/fields of Entity "Orders" to reference as foreign key Entity "Invoices"
 *  
 *  7. Notice How the insert and select statement is created by hibernate to persist or find an record entity and its reference entity
 * 
 *  Mapping : 
 *  	Orders (invoice_id) --> Invoices (id) 
 *
 */
public class Mapping01OneToOneUnidirectional {

	/**
	 * 
	  ==================================================================
	     create table invoice (
	        id integer not null auto_increment,
	         amount float,
	         primary key (id)
	     ) 
	  ==================================================================   
	 *
	 */
	@Entity(name="Invoices")
	@Table(name="invoice")
	public static class Invoice implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private Float amount;
		
		public Invoice() {
		}
		
		public Invoice(Float amount) {
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
	  	==================================================================
	     create table orders (
	        id integer not null auto_increment,
	         order_date date,
	         product varchar(255),
	         quantity integer,
	         invoice_id integer,
	         primary key (id)
	     ) 
	 
	     alter table orders 
	        add constraint FKjyanyp94x9j3fc526qhm6tipn 
	        foreign key (invoice_id) 
	        references invoice (id)
	    ==================================================================
	 * 
	 *	field "invoice_id" is auto generated
	 *
	 */
	@Entity(name = "Orders")
	@Table(name = "orders")
	public static class Order implements Serializable {

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
		private Invoice invoice;

		public Order() {
		}
		
		public Order(String product, Integer quantity, Date orderDate, Invoice invoice) {
			super();
			this.product = product;
			this.quantity = quantity;
			this.orderDate = orderDate;
			this.invoice = invoice;
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

		public Invoice getInvoice() {
			return invoice;
		}

		public void setInvoice(Invoice invoice) {
			this.invoice = invoice;
		}

		@Override
		public String toString() {
			if (this.orderDate!=null)
				return String.format(" { %d, %d, %s } ", this.id, this.quantity, new SimpleDateFormat("dd-MMM-yyyy").format(this.orderDate));
			else
				return String.format(" { %d, %d, %s } ", this.id, this.quantity, null);
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
	
	/**
	 Hibernate automatically construct insert statement as follows :
	 	1. Insert Invoice Data: when it persist / save the new Entity (invoice) :
	 		==================================================================
		    insert 
		    into
		        invoice
		        (amount) 
		    values
		        (?)
		   ==================================================================
		        
		2. Insert Orders data : when persisting or saving new Entity (order)
			==================================================================
		    insert 
		    into
		        orders
		        (invoice_id, orderDate, product, quantity) 
		    values
		        (?, ?, ?, ?)
		    ==================================================================
	 * 
	 */
	public void insertData() throws Exception {
		System.out.println("1. insert new data");
		em.getTransaction().begin();
		
		Invoice newInvoice = new Invoice(299.9f);
		em.persist(newInvoice);			
		Order newOrder = new Order("Apple Watch", 3, (new SimpleDateFormat("dd-MM-yyyy")).parse("12-04-2019"), newInvoice);
		em.persist(newOrder);
		
		em.getTransaction().commit();		
	}
	
	/**
	  
	  Notice How the select statement is created by hibernate to find an record entity and its reference entity
	  hibernate will generate following query statement :
	  	1. find Order Entity : 	...em.find(Order.class, 1)
	  		==================================================================
			select
			    mapping01o0_.id as id1_31_0_,
			    mapping01o0_.invoice_id as invoice_5_31_0_,
			    mapping01o0_.orderDate as orderDat2_31_0_,
			    mapping01o0_.product as product3_31_0_,
			    mapping01o0_.quantity as quantity4_31_0_,
			    mapping01o1_.id as id1_28_1_,
			    mapping01o1_.amount as amount2_28_1_ 
			from
			    orders mapping01o0_ 
			left outer join
			    invoice mapping01o1_ 
			        on mapping01o0_.invoice_id=mapping01o1_.id 
			where
			    mapping01o0_.id=?
			==================================================================
		    
		2. find Invoice Entity : ... em.find(Order.class, 2)
			==================================================================
			select
			    mapping01o0_.id as id1_20_0_,
			    mapping01o0_.amount as amount2_20_0_ 
			from
			    invoice mapping01o0_ 
			where
			    mapping01o0_.id=?
			==================================================================
		
	 * 
	 */
	public void retreiveData() {
		System.out.println("2. retrieve data");
		
		Order orderOne = em.find(Order.class, 1);
		System.out.println(orderOne); 					// output : { 1, 1, 2020-02-03 } 
		System.out.println(orderOne.getInvoice()); 		// output : { 1, 699.5900}  
		Order orderTwo = em.find(Order.class, 3);
		System.out.println(orderTwo);					// output :  { 3, 4, 2020-12-15 } 
		System.out.println(orderTwo.getInvoice());		// output :  { 3, 29.5600}  

		Invoice invoiceOne = em.find(Invoice.class, 2);
		System.out.println(invoiceOne.toString()); 		//  output : { 2, 67.2000}  
		Invoice invoiceTwo = em.find(Invoice.class, 5);
		System.out.println(invoiceTwo.toString()); 		//  output : { 5, 299.9000}  

		
		List<Order> orders = em.createQuery("SELECT a FROM Orders a", Order.class).getResultList();
		for (Order order : orders) {
			System.out.println(order.toString());
		}
		
	}
	
	public void runMain() {
		factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		em = factory.createEntityManager();

		try {
			insertData();

			retreiveData();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * 
			select * from invoice;
			---------- ------------ 
			id         amount       
			---------- ------------ 
			1          699.59       
			2          67.2         
			3          29.56        
			4          125.0        
			5          299.9        
					
			select * from orders;
			---------- ------------- -------------------- ---------- ---------- 
			id         orderDate     product              quantity   invoice_id 
			---------- ------------- -------------------- ---------- ---------- 
			1          2020-02-03    iPhone 6S            1          1          
			2          2020-03-05    Nike Sneakers        2          2          
			3          2020-12-15    Power Cord Cable     4          3          
			4          2020-08-17    Head Phone           1          4          
			5          2019-04-12    Apple Watch          3          5          
		 * 
		 */
		
	}
	
	
	public static void main(String[] args) {
		new Mapping01OneToOneUnidirectional().runMain();
	}

}
