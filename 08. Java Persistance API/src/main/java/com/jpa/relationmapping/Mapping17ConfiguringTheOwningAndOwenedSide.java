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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate Configure The Owning Side and The Owned Side
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
 *  6. Observe Annotation OneToMany are declared both of Entity (The owning and the owned Entity)  	
 *  
 *  7. Observe when using ManyToOne and JoinColumn declared on the Entity,
 *  	the select statement fatches the refrence data with EAGER-ly stratergy
 *  
 *  8.	Observe when using OneToMany and JoinColumn declared on Entity
 *  	the select statement fatches the reference data with LAZY strategy
 *  
 *  Mapping : 
 *		
 *
 */
public class Mapping17ConfiguringTheOwningAndOwenedSide {

	/**
	 * 
	 * 

Hibernate: 
    
    create table my_11th_products (
       id integer not null auto_increment,
        name varchar(255),
        quantity integer,
        order_id integer,
        primary key (id)
    ) engine=MyISAM
       
    alter table my_11th_products 
       add constraint FK8xuunachjrdmosttd74cjlc5o 
       foreign key (order_id) 
       references my_11th_order (id)           
           
	 *
	 */
	@Entity(name="MyProducts11")
	@Table(name="my_11th_products")
	public static class Product implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		private Integer quantity;
		
		@ManyToOne
		@JoinColumn(name = "order_id")
		private Order order;

		public Product() {
		}

		public Product(String name, Integer quantity) {
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
    
    create table my_11th_order (
       id integer not null auto_increment,
        order_date date,
        primary key (id)
    ) engine=MyISAM
    
	 *
	 */
	@Entity(name = "MyOrder11")
	@Table(name = "my_11th_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "order_date")
		@Temporal(TemporalType.DATE)
		private Date orderDate;

		@OneToMany
		@JoinColumn(name = "order_id")
		private List<Product> products;
		
		
		public Order() {
		}

		public Order(List<Product> products, Date orderDate) {
			this.products = products;
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
		
		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
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
			System.out.println("retrieve product data");
			
			/**
			 * Observe How the Query is being generated EAGER-ly (fetch data)
			 * 
Hibernate: 
    select
        mapping17c0_.id as id1_22_0_,
        mapping17c0_.name as name2_22_0_,
        mapping17c0_.order_id as order_id4_22_0_,
        mapping17c0_.quantity as quantity3_22_0_,
        mapping17c1_.id as id1_21_1_,
        mapping17c1_.order_date as order_da2_21_1_ 
    from
        my_11th_products mapping17c0_ 
    left outer join
        my_11th_order mapping17c1_ 
            on mapping17c0_.order_id=mapping17c1_.id 
    where
        mapping17c0_.id=?
                        
			 * 
			 */
			
			Product productOne = em.find(Product.class, 1);
			System.out.println(productOne);			
			System.out.println(productOne.getOrder());
			
			Product productFive = em.find(Product.class, 5);
			System.out.println(productFive);

			System.out.println("retrieve order data");

			/**
			 * 
			 * Demonstrate how the hibernate generated query statement LAZY-ly
			 * only query the order table
			 * 

Hibernate: 
    select
        mapping17c0_.id as id1_21_0_,
        mapping17c0_.order_date as order_da2_21_0_ 
    from
        my_11th_order mapping17c0_ 
    where
        mapping17c0_.id=?

			 * 			
			 */
			Order orderOne = em.find(Order.class, 10);
			System.out.println(orderOne);
			
			/**
			 * However, the hibernate generated the 2nd query statement if they are triggered on demand
			 * 

Hibernate: 
    select
        products0_.order_id as order_id4_22_0_,
        products0_.id as id1_22_0_,
        products0_.id as id1_22_1_,
        products0_.name as name2_22_1_,
        products0_.order_id as order_id4_22_1_,
        products0_.quantity as quantity3_22_1_ 
    from
        my_11th_products products0_ 
    where
        products0_.order_id=?
                
			 * 			
			 */
			System.out.println(orderOne.getProducts());
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	
	public static void main(String[] args) {
		new Mapping17ConfiguringTheOwningAndOwenedSide().runMain();
	}

}
