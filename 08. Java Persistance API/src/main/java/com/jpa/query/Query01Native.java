package com.jpa.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 
 * Demonstrate Native Query in Hibernate,
 * Native Queries are queries which depend on the underlying database SQL scripting Support
 * 	If connection made to MySQL database --> use MySQL SQL Script
 *  If connection made to Oracle database --> use Oracle SQL Script
 *  If connection made to SQLServer database --> use SQL Server Script
 * 	
 */
public class Query01Native {

	/**
	 * 
Hibernate: 
    
    create table tb_categories (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
	 * 
	 *
	 */
	
	@Entity(name = "tb_categories")
	public static class Category implements Serializable {
		
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinColumn(name = "category_id")
		private Set<Product> products;

		public Category() {
		}

		public Category(String name) {
			this.name = name;
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

		public Set<Product> getProducts() {
			return products;
		}

		public void addProducts(Product product) {
			if (this.products == null) this.products = new HashSet<>();
			this.products.add(product);
		}

		@Override
		public String toString() {
			return String.format("{ %s, %s }", this.name, this.products);
		}
		
	}
	
	/**
	 * 
Hibernate: 
    
    create table tb_products (
       id integer not null auto_increment,
        name varchar(255),
        price float,
        category_id integer,
        primary key (id)
    ) engine=MyISAM
    
    alter table tb_products 
       add constraint FKcthu194f0icof6cxprcukmotk 
       foreign key (category_id) 
       references tb_categories (id)    
	 * 
	 *
	 */
	@Entity(name = "tb_products")
	public static class Product implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		private Float price;
		
		@ManyToOne
		@JoinColumn(name = "category_id")
		private Category category;

		public Product() {
		}

		public Product(String name, Float price) {
			this.name = name;
			this.price = price;
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

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		@Override
		public String toString() {
			return String.format("{ %s, %.2f }", this.name, this.price);
		}
		
	}
	
	/**
	 * demonstrate how to use createNativeQuery method
	 */
	@SuppressWarnings("unchecked")
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		EntityManager em = factory.createEntityManager();

		try {
			
			Query query = em.createNativeQuery("select * from tb_categories");
			List<?> categories = query.getResultList();
			categories.forEach(System.out::println);
			/**
			 * output :
Hibernate: 
    select
        * 
    from
        tb_categories
[Ljava.lang.Object;@1f387978
[Ljava.lang.Object;@7cb2651f
[Ljava.lang.Object;@4441d567
[Ljava.lang.Object;@3e1624c7
			 * 
			 */
			
			Query queryCategory = em.createNativeQuery("select * from tb_categories", Category.class);
			List<Category> categories2 = (List<Category>) queryCategory.getResultList();
			categories2.forEach(System.out::println);
			
			/**
			 * output :
Hibernate: 
    select
        * 
    from
        tb_categories
Hibernate: 
    select
        products0_.category_id as category4_79_0_,
        products0_.id as id1_79_0_,
        products0_.id as id1_79_1_,
        products0_.category_id as category4_79_1_,
        products0_.name as name2_79_1_,
        products0_.price as price3_79_1_ 
    from
        tb_products products0_ 
    where
        products0_.category_id=?
        
{ Mobile Phones, [{ iPhone 6S, 699.00 }, { Samsumg Galaxy, 299.00 }] }
{ Fashion, [{ Scarf, 19.99 }, { Jeans, 78.99 }, { Belt, 9.90 }, { Designer Skirt, 49.00 }] }
{ Home, [{ Sporinkler, 89.00 }, { Notebook, 9.00 }] }
{ School, [{ Pen, 4.99 }] }
			 * 
			 */
			
			Query query2 = em.createNativeQuery("select * from tb_products");
			List<?> products = query.getResultList();
			products.forEach(System.out::println);
			/**
			 * output :
Hibernate: 
    select
        * 
    from
        tb_categories
[Ljava.lang.Object;@2125ad3
[Ljava.lang.Object;@7a5b769b
[Ljava.lang.Object;@f4c0e4e
[Ljava.lang.Object;@24361cfc
			 * 
			 */

			
			Query productQuery = em.createNativeQuery("select * from tb_products", Product.class);
			List<Product> products2 = (List<Product>) productQuery.getResultList();
			products2.forEach(System.out::println);
			
			/**
			 * output :
Hibernate: 
    select
        * 
    from
        tb_products
{ iPhone 6S, 699.00 }
{ Samsumg Galaxy, 299.00 }
{ Designer Skirt, 49.00 }
{ Jeans, 78.99 }
{ Scarf, 19.99 }
{ Belt, 9.90 }
{ Sporinkler, 89.00 }
{ Notebook, 9.00 }
{ Pen, 4.99 }
			 * 
			 */
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		 
	}
	
	public static void main(String[] args) {
		retrieveData();
	}

}
