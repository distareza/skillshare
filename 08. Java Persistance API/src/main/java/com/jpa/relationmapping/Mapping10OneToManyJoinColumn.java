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
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To Many Relationship Unidirectinal Mapping Join Column
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
 *  6. Observe Annotation @OneToMany is defined on Collections method or fields in Entity Class
 *    	
 *  7. Observe , we can specified the Name of @JoinColuumn Annotation with following specified attribute: name as "the foreign key" of the reference target entities
 *  
 *  Mapping : 
 *  	my_products (order_id) --< my_order (order_id)
 *		
 *
 */
public class Mapping10OneToManyJoinColumn {

	/**
	 * 
Hibernate: 

    create table my_4th_products (
       id integer not null auto_increment,
        name varchar(255),
        quantity integer,
        order_id integer,
        primary key (id)
    ) engine=MyISAM
   
    alter table my_4th_products 
       add constraint FKj80hub064fkvhb4yr44x2lh65 
       foreign key (order_id) 
       references my_4th_order (id)              
	 *
	 */
	@Entity(name="MyProducts4")
	@Table(name="my_4th_products")
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
    
    create table my_4th_order (
       id integer not null auto_increment,
        order_date date,
        primary key (id)
    ) engine=MyISAM
              
	 *
	 */
	@Entity(name = "MyOrder4")
	@Table(name = "my_4th_order")
	public static class Order implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@OneToMany
		@JoinColumn(name = "order_id")
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
Hibernate: 
    select
        mapping10o0_.id as id1_25_0_,
        mapping10o0_.order_date as order_da2_25_0_ 
    from
        my_4th_order mapping10o0_ 
    where
        mapping10o0_.id=?
Hibernate: 
    select
        products0_.order_id as order_id4_26_0_,
        products0_.id as id1_26_0_,
        products0_.id as id1_26_1_,
        products0_.name as name2_26_1_,
        products0_.quantity as quantity3_26_1_ 
    from
        my_4th_products products0_ 
    where
        products0_.order_id=?
                
			 * 
			 */
			
			Order orderOne = em.find(Order.class, 10);
			System.out.println(orderOne);
			
			List<Order> orders = em.createQuery("SELECT a FROM MyOrder4 a", Order.class).getResultList();
			System.out.println(orders);
			/**
			 * 

select * from my_4th_products;
---------- ----------------------- ---------- ---------- 
id         name                    quantity   order_id   
---------- ----------------------- ---------- ---------- 
1          iPhone 6S               1          10         
2          Nike Sneakers           2          10         
3          iMac 24-inc M1          1          10         
4          iPhone 12               1          11         
5          Original AirPod         3          12         
6          Apple Watch 6           2          12         


select * from my_4th_order;
---------- -----------------
id         order_date       
---------- -----------------
10         2021-05-07       
11         2020-11-13       
12         2019-04-02       


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
		new Mapping10OneToManyJoinColumn().runMain();
	}

}
