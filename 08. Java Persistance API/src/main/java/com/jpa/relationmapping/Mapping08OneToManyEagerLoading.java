package com.jpa.relationmapping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To Many Relationship With Eager Loading
 * Several technique to loaded and fetch data references into an Entity:
 * 		1. Eager Loading
 * 		2. Lazy Loading
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
 *		@OneToMany (fetch = FetchType.EAGER)
 *  	notice there is an extra "fetch" attribute is added, to specifying a fetch with eagerly load mapped entities in owning entity
 *    	
 *  7. Observe , instead uses Join Table is default naming schema , we can specified the Name of Join Tables and its fields uses @JoinTable Annotation with following specified attribute:
 *		@JoinTable(
 *						name = "<mapping table name>",
 *						joinColumns = { @JoinColumn( name = "<mapping source field name>", referencedColumnName = "<primary key of source entity>") },
 *						inverseJoinColumns = { @JoinColumn( name = "<mapping target field name>", referencedColumnName = "<primary key of target entity>") }
 *		) 
 *  	the join table must having 2 foreign key relation 
 *  		1. Owning Entity Primary Key ( source Entity )
 *  		2. Owned Entity Primary Key ( target Entity )
 *  
 *  8. With FATCH.EAGER type, Observe how the hibernate generated a query to find an entity, the query statement is being  generated a join query 
 *  
 *  Mapping : 
 *  	my_products (id) --< my_mapping_products_order -- my_products_order (id)
 *		
 *		my_mapping_products_order (order_id, product_id)
 *
 */
public class Mapping08OneToManyEagerLoading {

	/**
	 * 

Hibernate: 
    
    create table my_products (
       id integer not null auto_increment,
        name varchar(255),
        quantity integer,
        primary key (id)
    ) engine=MyISAM
              
	 *
	 */
	@Entity(name="MyProductsNo2")
	@Table(name="my_2nd_products")
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

Hibernate: 
    
    create table my_products_order (
       id integer not null auto_increment,
        order_date date,
        primary key (id)
    ) engine=MyISAM
    
    
    create table my_mapping_products_order (
       order_id integer not null,
        product_id integer not null
    ) engine=MyISAM
      
    alter table my_mapping_products_order 
       add constraint FKhwlh8ugl0hgxcrg5h5e6ok48k 
       foreign key (product_id) 
       references my_products (id)
    
    alter table my_mapping_products_order 
       add constraint FKsfw9ee3kjc66jlf7bv4t1dl4 
       foreign key (order_id) 
       references my_products_order (id)
       
       
              
	 *
	 */
	@Entity(name = "MyOrderNo2")
	@Table(name = "my_2nd_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@OneToMany (fetch = FetchType.EAGER)
		@JoinTable(
						name = "my_2nd_mapping_products_order",
						joinColumns = { @JoinColumn( name = "order_id", referencedColumnName = "id") },
						inverseJoinColumns = { @JoinColumn( name = "product_id", referencedColumnName = "id") }
		)
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
			 * Notice when finding an single Entity by PrimaryKey, Hibernate will generate join query as follows :
			 *    
Hibernate: 

    select
        mapping08o0_.id as id1_20_0_,
        mapping08o0_.order_date as order_da2_20_0_,
        products1_.order_id as order_id1_19_1_,
        mapping08o2_.id as product_2_19_1_,
        mapping08o2_.id as id1_21_2_,
        mapping08o2_.name as name2_21_2_,
        mapping08o2_.quantity as quantity3_21_2_ 
    from
        my_2nd_order mapping08o0_ 
    left outer join
        my_2nd_mapping_products_order products1_ 
            on mapping08o0_.id=products1_.order_id 
    left outer join
        my_2nd_products mapping08o2_ 
            on products1_.product_id=mapping08o2_.id 
    where
        mapping08o0_.id=?        
        
			 * 
			 */
			
			Order orderOne = em.find(Order.class, 10);
			System.out.println(orderOne);
			
			List<Order> orders = em.createQuery("SELECT a FROM MyOrderNo2 a", Order.class).getResultList();
			System.out.println(orders);
			/**
			 * 

		output : 
		[	
			{ 10, [{ 1, iPhone 6S, 1 }, { 2, Nike Sneakers, 2 }, { 3, iMac 24-inc M1, 1 }], 2021-05-07 }, 
			{ 11, [{ 4, iPhone 12, 1 }], 2020-11-13 }, 
			{ 12, [{ 5, Original AirPod, 3 }, { 6, Apple Watch 6, 2 }], 2019-04-02 }
		]
			
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
		new Mapping08OneToManyEagerLoading().runMain();
	}

}
