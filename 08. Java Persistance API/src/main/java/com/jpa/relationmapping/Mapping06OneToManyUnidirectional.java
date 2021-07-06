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
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To Many Relationship Unidirectinal Mapping ( with default Table Join mapping )
 * Hibernate modeled the mapping using separate Table to join both of Entities
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
 *  6. Observe Annotation OneToMany is defined on Collections method or fields in Entity Class
 *    	
 *  7. Observe Join Table is default to  (  Table Name of Owning Entity + "_" + Table Name of Owned Entity , example : "products_order" + "_" + "products" )	
 *  	the join table having 2 foreign key relation 
 *  		1. Owning Entity Primary Key ( OneToMany Method name of Owning Entity + "_" + Owned Entity PK, example : "products" + "_" + "id")
 *  		2. Owned Entity Primary Key ( Owning Entity Name + "_" + its Primary Key , example : "ProductOrder" + "_" + "id" )
 *  
 *  Mapping : 
 *  	products_order (id) --< products_order_products -- products (id)
 *		
 *		products_order_products (ProductOrder_id, products_id)
 *
 */
public class Mapping06OneToManyUnidirectional {

	/**
	 * 

Hibernate: 
    
    create table products (
       id integer not null auto_increment,
        name varchar(255),
        quantity integer,
        primary key (id)
    ) engine=MyISAM
              
	 *
	 */
	@Entity(name="Products")
	@Table(name="products")
	public static class Product implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		private Integer quantity;

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

		@Override
		public String toString() {
			return String.format("{ %d, %s, %d }", this.id, this.name, this.quantity);
		}

	}
	
	/**
	 * 

    create table products_order (
       id integer not null auto_increment,
        order_date date,
        primary key (id)
    ) engine=MyISAM
    
    
    create table products_order_products (
       ProductOrder_id integer not null,
        products_id integer not null
    ) engine=MyISAM
      

    alter table products_order_products 
       add constraint FK2it7bty5vhoo98a3xldy5vttm 
       foreign key (products_id) 
       references products (id)
       
    alter table products_order_products 
       add constraint FKnsus52twf00jtietlxrkvdy4t 
       foreign key (ProductOrder_id) 
       references products_order (id)
              
	 *
	 */
	@Entity(name = "ProductOrder")
	@Table(name = "products_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@OneToMany
		private List<Product> products;
		
		@Column(name = "order_date")
		@Temporal(TemporalType.DATE)
		private Date orderDate;

		public Order() {
		}

		public Order(List<Product> products, Date orderDate) {
			super();
			this.products = products;
			this.orderDate = orderDate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}

		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		@Override
		public String toString() {
			return String.format("{ %d, %s, %s }", this.id, this.products, this.orderDate);
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
        products0_.ProductOrder_id as ProductO1_23_0_,
        products0_.products_id as products2_23_0_,
        mapping06o1_.id as id1_21_1_,
        mapping06o1_.name as name2_21_1_,
        mapping06o1_.quantity as quantity3_21_1_ 
    from
        products_order_products products0_ 
    inner join
        products mapping06o1_ 
            on products0_.products_id=mapping06o1_.id 
    where
        products0_.ProductOrder_id=?
			 * 
			 */
			
			List<Order> orders = em.createQuery("SELECT a FROM ProductOrder a", Order.class).getResultList();
			System.out.println(orders);

			/**
			 * 
		select * from products;
		---------- -------------------- ---------- 
		id         name                 quantity   
		---------- -------------------- ---------- 
		1          iPhone 6S            1          
		2          Nike Sneakers        2          
		3          iMac 24-inc M1       1          
		4          iPhone 12            1          
		5          Original AirPod      3          
		6          Apple Watch 6        2          
		
		select * from products_order;
		---------- ------------------------- 
		id         order_date                
		---------- ------------------------- 
		10         2021-05-07                
		11         2020-11-13                
		12         2019-04-02                
		
		select * from products_order_products;
		--------------- ----------- 
		ProductOrder_id products_id 
		--------------- ----------- 
		10              1           
		10              2           
		10              3           
		11              4           
		12              5           
		12              6           

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
		new Mapping06OneToManyUnidirectional().runMain();
	}

}
