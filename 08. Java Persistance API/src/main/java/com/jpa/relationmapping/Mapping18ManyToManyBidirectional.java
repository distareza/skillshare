package com.jpa.relationmapping;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate Many To Many Relation Bidirectinal Mapping
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
 *  6. Observe Annotation ManyToMany are declared both of Entity (The owning and the owned Entity)  	
 *  
 *  
 *  Mapping : 
 *		
 *
 */
public class Mapping18ManyToManyBidirectional {

	/**
	 * 
	 * 

Hibernate: 
    
    create table customer (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    
    create table customer_products (
       customer_id integer not null,
        product_id integer not null
    ) engine=MyISAM
    
    alter table customer_products 
       add constraint FKtb9d4eoa06x5m8rjwncaphr0f 
       foreign key (product_id) 
       references my_12th_products (id)
    
    alter table customer_products 
       add constraint FK3qu7t1ybtnjxhj5m0umtuyjja 
       foreign key (customer_id) 
       references customer (id)    
       
	 *
	 */
	@Entity(name = "Customer")
	@Table(name = "customer")
	public static class Customer implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@ManyToMany
		@JoinTable(
				name = "customer_products",
				joinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "id") },
				inverseJoinColumns = { @JoinColumn (name = "product_id", referencedColumnName = "id") }
		)	
		private List<Product> products;

		public Customer() {
		}

		public Customer(String name, List<Product> products) {
			super();
			this.name = name;
			this.products = products;
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

		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}

		@Override
		public String toString() {
			return String.format("{ %d, %s }", this.id, this.name);
		}
		
	}
	
	
	/**
	 * 
	 * 
Hibernate: 
    
    create table my_12th_products (
       id integer not null auto_increment,
        name varchar(255),
        quantity integer,
        primary key (id)
    ) engine=MyISAM
          
	 *
	 */
	@Entity(name="MyProducts12")
	@Table(name="my_12th_products")
	public static class Product implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		private Integer quantity;
		
		@ManyToMany(mappedBy = "products")
		private List<Customer> customers;

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
		
		public List<Customer> getCustomers() {
			return customers;
		}

		public void setCustomers(List<Customer> customers) {
			this.customers = customers;
		}

		@Override
		public String toString() {
			return String.format("{ %d, %s, %d }", this.id, this.name, this.quantity);
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
			 * Observe How the Query is being generated Lazy-ly (fetch data)
			 * 

    select
        mapping18m0_.id as id1_16_0_,
        mapping18m0_.name as name2_16_0_ 
    from
        customer mapping18m0_ 
    where
        mapping18m0_.id=?
                                
			 * 
			 */
			
			Customer customerOne = em.find(Customer.class, 1);
			System.out.println(customerOne);
			
			/**
			 * 
    select
        products0_.customer_id as customer1_17_0_,
        products0_.product_id as product_2_17_0_,
        mapping18m1_.id as id1_25_1_,
        mapping18m1_.name as name2_25_1_,
        mapping18m1_.quantity as quantity3_25_1_ 
    from
        customer_products products0_ 
    inner join
        my_12th_products mapping18m1_ 
            on products0_.product_id=mapping18m1_.id 
    where
        products0_.customer_id=?
			 * 
			 */
			System.out.println(customerOne.getProducts());
			
			Product productFive = em.find(Product.class, 5);
			System.out.println(productFive);

			System.out.println("retrieve order data");

			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	
	public static void main(String[] args) {
		new Mapping18ManyToManyBidirectional().runMain();
	}

}
