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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Demonstrate how to summarize the basic structure of the Criteria API
 * without using JPQL
 *
 */
public class Query10CriteriaAPI {
	
	@Entity(name = "Categories2")
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
	
	@Entity(name = "Product2")
	public static class Product implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		private Float price;
		
		private boolean inStock;
		
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
		
		public boolean isInStock() {
			return inStock;
		}

		public void setInStock(boolean inStock) {
			this.inStock = inStock;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		@Override
		public String toString() {
			return String.format("{ %d, %s, %.2f }", this.id, this.name, this.price);
		}
		
	}
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		EntityManager em = factory.createEntityManager();
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Category> categoryCQ = cb.createQuery(Category.class);
			
			Root<Category> rootCategory = categoryCQ.from(Category.class);
			categoryCQ.select(rootCategory);

			TypedQuery<Category> query = em.createQuery(categoryCQ);			
			List<Category> resultList = query.getResultList();
			resultList.forEach(r -> System.out.println(r));

			/**
			 * output :
Hibernate: 
    select
        query10cre0_.id as id1_13_,
        query10cre0_.name as name2_13_ 
    from
        Categories2 query10cre0_
Hibernate: 
    select
        products0_.category_id as category5_70_0_,
        products0_.id as id1_70_0_,
        products0_.id as id1_70_1_,
        products0_.category_id as category5_70_1_,
        products0_.inStock as inStock2_70_1_,
        products0_.name as name3_70_1_,
        products0_.price as price4_70_1_ 
    from
        Product2 products0_ 
    where
        products0_.category_id=?
Hibernate: 
    select
        products0_.category_id as category5_70_0_,
        products0_.id as id1_70_0_,
        products0_.id as id1_70_1_,
        products0_.category_id as category5_70_1_,
        products0_.inStock as inStock2_70_1_,
        products0_.name as name3_70_1_,
        products0_.price as price4_70_1_ 
    from
        Product2 products0_ 
    where
        products0_.category_id=?
Hibernate: 
    select
        products0_.category_id as category5_70_0_,
        products0_.id as id1_70_0_,
        products0_.id as id1_70_1_,
        products0_.category_id as category5_70_1_,
        products0_.inStock as inStock2_70_1_,
        products0_.name as name3_70_1_,
        products0_.price as price4_70_1_ 
    from
        Product2 products0_ 
    where
        products0_.category_id=?
Hibernate: 
    select
        products0_.category_id as category5_70_0_,
        products0_.id as id1_70_0_,
        products0_.id as id1_70_1_,
        products0_.category_id as category5_70_1_,
        products0_.inStock as inStock2_70_1_,
        products0_.name as name3_70_1_,
        products0_.price as price4_70_1_ 
    from
        Product2 products0_ 
    where
        products0_.category_id=?
Hibernate: 
    select
        products0_.category_id as category5_70_0_,
        products0_.id as id1_70_0_,
        products0_.id as id1_70_1_,
        products0_.category_id as category5_70_1_,
        products0_.inStock as inStock2_70_1_,
        products0_.name as name3_70_1_,
        products0_.price as price4_70_1_ 
    from
        Product2 products0_ 
    where
        products0_.category_id=?
Hibernate: 
    select
        products0_.category_id as category5_70_0_,
        products0_.id as id1_70_0_,
        products0_.id as id1_70_1_,
        products0_.category_id as category5_70_1_,
        products0_.inStock as inStock2_70_1_,
        products0_.name as name3_70_1_,
        products0_.price as price4_70_1_ 
    from
        Product2 products0_ 
    where
        products0_.category_id=?
{ Mobile Phones, [{ iPhone 6S, 699.00 }, { Samsumg Galaxy, 299.00 }] }
{ Fashion, [{ Belt, 9.90 }, { Designer Skirt, 49.00 }, { Jeans, 78.99 }, { Scarf, 19.99 }] }
{ Home, [{ Notebook, 9.00 }, { Sporinkler, 89.00 }] }
{ School, [{ Pen, 4.99 }] }
{ Books, [{ Diary of a Wimpy Kid, 5.99 }, { Awful Anties, 3.99 }, { Timmy Failure, 4.99 }] }
{ Groceries, [{ Apple, 2.99 }, { Lemons, 0.99 }, { Orange, 1.29 }] }
			 * 
			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}			
		
	}
	
	public static void retrieveData2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Product> productCQ = cb.createQuery(Product.class);
			
			Root<Product> rootProduct = productCQ.from(Product.class);
			
			productCQ.select(rootProduct);

			TypedQuery<Product> query = em.createQuery(productCQ);
			List<Product> resultList = query.getResultList();
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
        query10cre0_.id as id1_70_,
        query10cre0_.category_id as category5_70_,
        query10cre0_.inStock as inStock2_70_,
        query10cre0_.name as name3_70_,
        query10cre0_.price as price4_70_ 
    from
        Product2 query10cre0_
Hibernate: 
    select
        query10cre0_.id as id1_13_0_,
        query10cre0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cre0_ 
    left outer join
        Product2 products1_ 
            on query10cre0_.id=products1_.category_id 
    where
        query10cre0_.id=?
Hibernate: 
    select
        query10cre0_.id as id1_13_0_,
        query10cre0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cre0_ 
    left outer join
        Product2 products1_ 
            on query10cre0_.id=products1_.category_id 
    where
        query10cre0_.id=?
Hibernate: 
    select
        query10cre0_.id as id1_13_0_,
        query10cre0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cre0_ 
    left outer join
        Product2 products1_ 
            on query10cre0_.id=products1_.category_id 
    where
        query10cre0_.id=?
Hibernate: 
    select
        query10cre0_.id as id1_13_0_,
        query10cre0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cre0_ 
    left outer join
        Product2 products1_ 
            on query10cre0_.id=products1_.category_id 
    where
        query10cre0_.id=?
Hibernate: 
    select
        query10cre0_.id as id1_13_0_,
        query10cre0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cre0_ 
    left outer join
        Product2 products1_ 
            on query10cre0_.id=products1_.category_id 
    where
        query10cre0_.id=?
Hibernate: 
    select
        query10cre0_.id as id1_13_0_,
        query10cre0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cre0_ 
    left outer join
        Product2 products1_ 
            on query10cre0_.id=products1_.category_id 
    where
        query10cre0_.id=?
{ iPhone 6S, 699.00 }
{ Samsumg Galaxy, 299.00 }
{ Designer Skirt, 49.00 }
{ Jeans, 78.99 }
{ Scarf, 19.99 }
{ Belt, 9.90 }
{ Sporinkler, 89.00 }
{ Notebook, 9.00 }
{ Pen, 4.99 }
{ Diary of a Wimpy Kid, 5.99 }
{ Awful Anties, 3.99 }
{ Timmy Failure, 4.99 }
{ Apple, 2.99 }
{ Orange, 1.29 }
{ Lemons, 0.99 }
		 * 
		 */
	}
	
	public static void main(String[] args) {
		//retrieveData();
		retrieveData2();
	}

}
