package com.jpa.relationmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
 *  6.	Notice Annotation @OneToOne that describe in Order Entity Class
 * 
 * 	7.	To retrieve both table that join together uses @JoinColumn Annotation
 * 
 *  Mapping : 
 *  	Invoices (id) <-> Orders (invoice_id)
 *
 */
public class Mapping01OneToOneUnidirectional {

	/**
	 * 
	 *    create table invoice (
	 *       id integer not null auto_increment,
	 *        amount float,
	 *        primary key (id)
	 *    ) 
	 * 
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
	 *    create table product_order (
	 *       id integer not null auto_increment,
	 *        orderDate date,
	 *        product varchar(255),
	 *        quantity integer,
	 *        invoice_id integer,
	 *        primary key (id)
	 *    ) 
	 *
	 *    alter table product_order 
	 *       add constraint FKjyanyp94x9j3fc526qhm6tipn 
	 *       foreign key (invoice_id) 
	 *       references invoice (id)
	 * 
	 *	field "invoice_id" is auto generated
	 *
	 */
	@Entity(name = "Orders")
	@Table(name = "product_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String product;
		private Integer quantity;
		
		@Temporal(TemporalType.DATE)
		private Date orderDate;
		
		@OneToOne
		private Invoice invoice;

		public Order() {
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
			        mapping01o0_.id as id1_31_0_,
			        mapping01o0_.invoice_id as invoice_5_31_0_,
			        mapping01o0_.orderDate as orderDat2_31_0_,
			        mapping01o0_.product as product3_31_0_,
			        mapping01o0_.quantity as quantity4_31_0_,
			        mapping01o1_.id as id1_28_1_,
			        mapping01o1_.amount as amount2_28_1_ 
			    from
			        product_order mapping01o0_ 
			    left outer join
			        invoice mapping01o1_ 
			            on mapping01o0_.invoice_id=mapping01o1_.id 
			    where
			        mapping01o0_.id=?
			 * 
			 */
			Order orderOne = em.find(Order.class, 1);
			System.out.println(orderOne);
			
			Order orderTwo = em.find(Order.class, 2);
			System.out.println(orderTwo);
			
			List<Order> orders = em.createQuery("SELECT a FROM Orders a", Order.class).getResultList();
			for (Order order : orders) {
				System.out.println(order.toString());
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
		
		select * from product_order;
		---------- ------------------------- ---------------------------------------------------- ---------- ---------- 
		id         orderDate                 product                                              quantity   invoice_id 
		---------- ------------------------- ---------------------------------------------------- ---------- ---------- 
		1          2020-02-03                iPhone 6S                                            1          1          
		2          2020-03-05                Nike Sneakers                                        2          2          
		3          2020-12-15                3 Pin UK Power Cord Cable for Power Supply           4          3          
		4          2020-08-17                Head Phone                                           1          4          

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
		new Mapping01OneToOneUnidirectional().runMain();
	}

}
