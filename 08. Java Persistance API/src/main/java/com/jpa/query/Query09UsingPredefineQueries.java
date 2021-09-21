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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Demonstrate how to define and use named queries
 * 
 *
 */
public class Query09UsingPredefineQueries {
	
	@Entity(name = "Categories")
	@NamedQuery(name = "selectSpecificCategory", query = "select c from Categories c where c.name = :categoryName")
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
	
	@Entity(name = "Product")
	@NamedQueries({
			@NamedQuery(name = Product.SELECT_PRODUCTS_IN_CATEGORY, query = "select p from Product p where p.category.id = :categoryId"),
			@NamedQuery(name = Product.SELECT_PRODICTS_IN_PRICE_RANGE, query = "select  p from Product p where p.price > :low and p.price <= :high")
	})
	public static class Product implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String SELECT_PRODUCTS_IN_CATEGORY = "selectProductInCategory";
		public static final String SELECT_PRODICTS_IN_PRICE_RANGE = "selectProductInPriceRange";
		
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

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Category> query = em.createNamedQuery("selectSpecificCategory", Category.class);
			query.setParameter("categoryName",  "Fashion");
			List<Category> resultList = query.getResultList();
			resultList.forEach(r -> System.out.println(r));

			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}			
		
		/**
		 * output :
Hibernate: 
    select
        query09usi0_.id as id1_12_,
        query09usi0_.name as name2_12_ 
    from
        Categories query09usi0_ 
    where
        query09usi0_.name=?
Hibernate: 
    select
        products0_.category_id as category4_80_0_,
        products0_.id as id1_80_0_,
        products0_.id as id1_80_1_,
        products0_.category_id as category4_80_1_,
        products0_.name as name2_80_1_,
        products0_.price as price3_80_1_ 
    from
        tb_products products0_ 
    where
        products0_.category_id=?
Hibernate: 
    select
        query01nat0_.id as id1_79_0_,
        query01nat0_.name as name2_79_0_,
        products1_.category_id as category4_80_1_,
        products1_.id as id1_80_1_,
        products1_.id as id1_80_2_,
        products1_.category_id as category4_80_2_,
        products1_.name as name2_80_2_,
        products1_.price as price3_80_2_ 
    from
        tb_categories query01nat0_ 
    left outer join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        query01nat0_.id=?
{ Fashion, [{ Belt, 9.90 }, { Designer Skirt, 49.00 }, { Jeans, 78.99 }, { Scarf, 19.99 }] }
		 * 
		 */
	}
	
	public static void retrieveData2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Product> query = em.createNamedQuery(Product.SELECT_PRODUCTS_IN_CATEGORY, Product.class);
			query.setParameter("categoryId",  231);
			List<Product> resultList = query.getResultList();
			resultList.forEach(r -> System.out.println(r));
			
			/**
			 * output :
Hibernate: 
    select
        query09usi0_.id as id1_68_,
        query09usi0_.category_id as category4_68_,
        query09usi0_.name as name2_68_,
        query09usi0_.price as price3_68_ 
    from
        Product query09usi0_ 
    where
        query09usi0_.category_id=?
Hibernate: 
    select
        query09usi0_.id as id1_12_0_,
        query09usi0_.name as name2_12_0_,
        products1_.category_id as category4_68_1_,
        products1_.id as id1_68_1_,
        products1_.id as id1_68_2_,
        products1_.category_id as category4_68_2_,
        products1_.name as name2_68_2_,
        products1_.price as price3_68_2_ 
    from
        Categories query09usi0_ 
    left outer join
        Product products1_ 
            on query09usi0_.id=products1_.category_id 
    where
        query09usi0_.id=?
{ Designer Skirt, 49.00 }
{ Jeans, 78.99 }
{ Scarf, 19.99 }
{ Belt, 9.90 }
			 * 
			 */
			
			TypedQuery<Product> query2 = em.createNamedQuery(Product.SELECT_PRODICTS_IN_PRICE_RANGE, Product.class);
			query2.setParameter("low", 100f);
			query2.setParameter("high", 1000f);
			
			resultList = query2.getResultList();
			resultList.forEach(r -> System.out.println(r));
			
			/**
			 * output :
Hibernate: 
    select
        query09usi0_.id as id1_68_,
        query09usi0_.category_id as category4_68_,
        query09usi0_.name as name2_68_,
        query09usi0_.price as price3_68_ 
    from
        Product query09usi0_ 
    where
        query09usi0_.price>? 
        and query09usi0_.price<=?
Hibernate: 
    select
        query09usi0_.id as id1_12_0_,
        query09usi0_.name as name2_12_0_,
        products1_.category_id as category4_68_1_,
        products1_.id as id1_68_1_,
        products1_.id as id1_68_2_,
        products1_.category_id as category4_68_2_,
        products1_.name as name2_68_2_,
        products1_.price as price3_68_2_ 
    from
        Categories query09usi0_ 
    left outer join
        Product products1_ 
            on query09usi0_.id=products1_.category_id 
    where
        query09usi0_.id=?
{ iPhone 6S, 699.00 }
{ Samsumg Galaxy, 299.00 }
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
		//retrieveData();
		retrieveData2();
	}

}
