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
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship with Join Table
 * Hibernate modeled the mapping using separate Table to join both of Entities
 * The possible representations to model a one-to-one mapping relationship are
 * 		1. Using a foreign key reference to associated entities
 * 		2. By having the entities share a primary key
 * 	-->	3. Using a separate mapping table to model entity associations
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
 *  6.	Notice Annotation @OneToOne and @JoinTable that declare at method/fields on Owning Entity Classes   
 *     
 *  
 *  Mapping : 
 *  	purchases_invoice (id) <- purchases_mapping_order_invoice (invoice_id, order_id) -> purchases_order (id)
 *
 */
public class Mapping05OneToOneJoinTable {

	/**
	 * 

Hibernate: 
    
    create table purchases_invoice (
       id integer not null auto_increment,
        amount float,
        primary key (id)
    ) engine=MyISAM
    
           
	 * 
	 *
	 */
	@Entity(name="PurchaseInvoice")
	@Table(name="purchases_invoice")
	public static class Invoice implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private Float amount;
		
		@OneToOne(mappedBy = "invoice")
		private Order order;
		
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

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
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
    
    create table purchases_order (
       id integer not null auto_increment,
        order_date date,
        product varchar(255),
        quantity integer,
        primary key (id)
    ) engine=MyISAM
    
    create table purchase_mapping_order_invoice (
       invoice_id integer,
        order_id integer not null,
        primary key (order_id)
    ) engine=MyISAM
    
        
    alter table purchase_mapping_order_invoice 
       add constraint FK5pun02tw5jskih4j0uo7kknid 
       foreign key (invoice_id) 
       references purchases_invoice (id)
    
    alter table purchase_mapping_order_invoice 
       add constraint FK68wgw7f3vm70bdn0w4d4fcyr7 
       foreign key (order_id) 
       references purchases_order (id)    
       
	 *       
	 *
	 */
	@Entity(name = "PurchaseOrder")
	@Table(name = "purchases_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String product;
		
		private Integer quantity;
		
		@Column(name = "order_date")
		@Temporal(TemporalType.DATE)
		private Date orderDate;

		@OneToOne
		@JoinTable(	
				name = "purchases_mapping_order_invoice", 
				joinColumns = { @JoinColumn( name = "order_id", referencedColumnName = "id") },
				inverseJoinColumns = { @JoinColumn( name = "invoice_id", referencedColumnName = "id") })		
		private Invoice invoice;
		
		public Order() {
		}

		public Order(String product, Date orderDate) {
			this.product = product;
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

		public Invoice getInvoice() {
			return invoice;
		}

		public void setInvoice(Invoice invoice) {
			this.invoice = invoice;
		}

		@Override
		public String toString() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateText = null;
			if (this.orderDate != null) dateText = sdf.format(orderDate);
			
			String invoiceText = null;
			if (this.invoice != null) invoiceText = invoice.toString();
			
			return String.format(" { %d, %s, %d, %s, %s } ", this.id, this.product, this.quantity,  dateText, invoiceText);
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
			 
Hibernate: 
    select
        mapping05o0_.id as id1_28_0_,
        mapping05o0_.order_date as order_da2_28_0_,
        mapping05o0_.product as product3_28_0_,
        mapping05o0_.quantity as quantity4_28_0_,
        mapping05o0_1_.invoice_id as invoice_1_24_0_,
        mapping05o1_.id as id1_27_1_,
        mapping05o1_.amount as amount2_27_1_,
        mapping05o1_1_.order_id as order_id0_24_1_,
        mapping05o2_.id as id1_28_2_,
        mapping05o2_.order_date as order_da2_28_2_,
        mapping05o2_.product as product3_28_2_,
        mapping05o2_.quantity as quantity4_28_2_,
        mapping05o2_1_.invoice_id as invoice_1_24_2_ 
    from
        purchases_order mapping05o0_ 
    left outer join
        purchase_mapping_order_invoice mapping05o0_1_ 
            on mapping05o0_.id=mapping05o0_1_.order_id 
    left outer join
        purchases_invoice mapping05o1_ 
            on mapping05o0_1_.invoice_id=mapping05o1_.id 
    left outer join
        purchase_mapping_order_invoice mapping05o1_1_ 
            on mapping05o1_.id=mapping05o1_1_.invoice_id 
    left outer join
        purchases_order mapping05o2_ 
            on mapping05o1_1_.order_id=mapping05o2_.id 
    left outer join
        purchase_mapping_order_invoice mapping05o2_1_ 
            on mapping05o2_.id=mapping05o2_1_.order_id 
    where
        mapping05o0_.id=?
	         
			 * 
			 */
			Order orderOne = em.find(Order.class, 10);
			System.out.println(orderOne);
			
			Order orderTwo = em.find(Order.class, 12);
			System.out.println(orderTwo);
			
			
			List<Order> trxs = em.createQuery("SELECT a FROM PurchaseOrder a", Order.class).getResultList();
			System.out.println(trxs);
			
			List<Invoice> bills = em.createQuery("SELECT a FROM PurchaseInvoice a", Invoice.class).getResultList();
			System.out.println(bills);
			
			/**
			 * 
			 * 

		select * from purchases_order;
		---------- --------------- -------------------- ---------- 
		id         order_date      product              quantity   
		---------- --------------- -------------------- ---------- 
		10         2021-05-07      iMac 24-inch M1      1          
		11         2020-11-13      iPhone 12            1          
		12         2019-04-02      Apple Watch 6        2          
		13         2020-08-31      Original AirPod      3          
		
		select * from purchases_invoice;
		---------- ------------ 
		id         amount       
		---------- ------------ 
		1          1335.0       
		2          849.0        
		3          1398.0       
		4          477.0        
		
		select * from purchases_mapping_order_invoice;
		---------- ---------- 
		invoice_id order_id   
		---------- ---------- 
		1          10         
		2          11         
		3          12         
		4          13         

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
		new Mapping05OneToOneJoinTable().runMain();
	}

}
