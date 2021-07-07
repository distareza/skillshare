package com.jpa.relationmapping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate Many To One Relationship 
 * Hibernate modeled the mapping using foreign key constraints, the foreign key is setup in the Owning Entity which references in Non Owning Entity 
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
 *  6. Observe Annotation @ManyToOne is defined on a method or field
 *    	
 *  7. Observe , we can specified the Name of @JoinColuumns Annotation with following specified attribute: name as "the foreign key" of the reference target entities  
 *  
 *  Mapping : 
 *  	my_products (order_id) -- my_order (order_id)
 *		
 *
 */
public class Mapping13ManyToOneUnidrectionalMapping {

	/**
	 * 

Hibernate: 
    
    create table my_7th_products (
       id integer not null auto_increment,
        name varchar(255),
        quantity integer,
        order_id integer,
        primary key (id)
    ) engine=MyISAM
    
    alter table my_7th_products 
       add constraint FKnvjl88f858n2b3v56ne6wnscd 
       foreign key (order_id) 
       references my_7th_order (id)    

	 *
	 */
	@Entity(name="MyProducts7")
	@Table(name="my_7th_products")
	public static class Product implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		private Integer quantity;
		
		@ManyToOne
		private Order order;

		public Product() {
		}

		public Product(Order order, String name, Integer quantity) {
			this.order = order;
			this.name = name;
			this.quantity = quantity;
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

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		
		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		@Override
		public String toString() {
			return String.format("{ %d, %s, %d }", this.id, this.name, this.quantity);
		}

	}
	
	/**
	 * 

Hibernate: 
    
    create table my_7th_order (
       id integer not null auto_increment,
        order_date date,
        primary key (id)
    ) engine=MyISAM
     
	 *
	 */
	@Entity(name = "MyOrder7")
	@Table(name = "my_7th_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "order_date")
		@Temporal(TemporalType.DATE)
		private Date orderDate;

		public Order() {
		}

		public Order(List<Product> products, Date orderDate) {
			super();
			this.orderDate = orderDate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		@Override
		public String toString() {
			return String.format("{ %d, %s }", this.id, this.orderDate);
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
			 * Observe How the Query is being generated 
			 * 

Hibernate: 
    select
        mapping13m0_.id as id1_31_0_,
        mapping13m0_.order_date as order_da2_31_0_ 
    from
        my_7th_order mapping13m0_ 
    where
        mapping13m0_.id=?
        
                
			 * 
			 */
			
			Order orderOne = em.find(Order.class, 10);
			System.out.println(orderOne);
			
			Order orderTwo = em.find(Order.class, 12);
			System.out.println(orderTwo);
			
			/**
			 * 

Hibernate: 
    select
        mapping13m0_.id as id1_32_,
        mapping13m0_.name as name2_32_,
        mapping13m0_.order_id as order_id4_32_,
        mapping13m0_.quantity as quantity3_32_ 
    from
        my_7th_products mapping13m0_
Hibernate: 
    select
        mapping13m0_.id as id1_31_0_,
        mapping13m0_.order_date as order_da2_31_0_ 
    from
        my_7th_order mapping13m0_ 
    where
        mapping13m0_.id=?

			 * 			
			 */
			
			List<Product> products = em.createQuery("SELECT a FROM MyProducts7 a", Product.class).getResultList();
			System.out.println(products);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	
	public static void main(String[] args) {
		new Mapping13ManyToOneUnidrectionalMapping().runMain();
	}

}
