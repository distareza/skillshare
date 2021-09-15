package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.jpa.query.Query01Native.Product;

/**
 * Demonstrate how JPQL Queries and parameters in Hibernate
 * 	uses method createQuery() 
 *
 */
public class Query03JPQLWithParameters {

	/**
	 * demonstrate how to using Named Parameters in JPQL
	 */
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Product> productQuery = em.createQuery("select p from tb_products p where p.id = :pid ", Product.class); // tb_products is the name of Entity Class
			productQuery.setParameter("pid", 1003);
			
			Product product = productQuery.getSingleResult();
			System.out.println(product);

			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_79_,
        query01nat0_.category_id as category4_79_,
        query01nat0_.name as name2_79_,
        query01nat0_.price as price3_79_ 
    from
        tb_products query01nat0_ 
    where
        query01nat0_.id=?
Hibernate: 
    select
        query01nat0_.id as id1_78_0_,
        query01nat0_.name as name2_78_0_,
        products1_.category_id as category4_79_1_,
        products1_.id as id1_79_1_,
        products1_.id as id1_79_2_,
        products1_.category_id as category4_79_2_,
        products1_.name as name2_79_2_,
        products1_.price as price3_79_2_ 
    from
        tb_categories query01nat0_ 
    left outer join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        query01nat0_.id=?
{ Designer Skirt, 49.00 }
			 * 
			 */
			
			
			productQuery = em.createQuery("select p from tb_products p where p.id > :pid order by price", Product.class);
			productQuery.setParameter("pid", 1003);
			List<Product> products = productQuery.getResultList();
			System.out.println(products);
			
			/**
			 * output:
Hibernate: 
    select
        query01nat0_.id as id1_79_,
        query01nat0_.category_id as category4_79_,
        query01nat0_.name as name2_79_,
        query01nat0_.price as price3_79_ 
    from
        tb_products query01nat0_ 
    where
        query01nat0_.id>? 
    order by
        query01nat0_.price
Hibernate: 
    select
        query01nat0_.id as id1_78_0_,
        query01nat0_.name as name2_78_0_,
        products1_.category_id as category4_79_1_,
        products1_.id as id1_79_1_,
        products1_.id as id1_79_2_,
        products1_.category_id as category4_79_2_,
        products1_.name as name2_79_2_,
        products1_.price as price3_79_2_ 
    from
        tb_categories query01nat0_ 
    left outer join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        query01nat0_.id=?
[{ Pen, 4.99 }, { Notebook, 9.00 }, { Belt, 9.90 }, { Scarf, 19.99 }, { Jeans, 78.99 }, { Sporinkler, 89.00 }]        
			 * 
			 */
			
			productQuery = em.createQuery("select p from tb_products p where p.name like :name order by price", Product.class);
			productQuery.setParameter("name", "iPho%");
			products = productQuery.getResultList();
			System.out.println(products);
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_79_,
        query01nat0_.category_id as category4_79_,
        query01nat0_.name as name2_79_,
        query01nat0_.price as price3_79_ 
    from
        tb_products query01nat0_ 
    where
        query01nat0_.name like ? 
    order by
        query01nat0_.price
Hibernate: 
    select
        query01nat0_.id as id1_78_0_,
        query01nat0_.name as name2_78_0_,
        products1_.category_id as category4_79_1_,
        products1_.id as id1_79_1_,
        products1_.id as id1_79_2_,
        products1_.category_id as category4_79_2_,
        products1_.name as name2_79_2_,
        products1_.price as price3_79_2_ 
    from
        tb_categories query01nat0_ 
    left outer join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        query01nat0_.id=?
[{ iPhone 6S, 699.00 }]
			 * 
			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}

	/**
	 * demonstrate how to using Positional Argumanet Parameters in JPQL
	 */
	public static void retrieveData2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Product> productQuery = em.createQuery("select p from tb_products p where p.id = ?1 ", Product.class); // tb_products is the name of Entity Class
			productQuery.setParameter(1, 1005);
			
			Product product = productQuery.getSingleResult();
			System.out.println(product);

			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_79_,
        query01nat0_.category_id as category4_79_,
        query01nat0_.name as name2_79_,
        query01nat0_.price as price3_79_ 
    from
        tb_products query01nat0_ 
    where
        query01nat0_.id=?
Hibernate: 
    select
        query01nat0_.id as id1_78_0_,
        query01nat0_.name as name2_78_0_,
        products1_.category_id as category4_79_1_,
        products1_.id as id1_79_1_,
        products1_.id as id1_79_2_,
        products1_.category_id as category4_79_2_,
        products1_.name as name2_79_2_,
        products1_.price as price3_79_2_ 
    from
        tb_categories query01nat0_ 
    left outer join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        query01nat0_.id=?
{ Scarf, 19.99 }
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
		retrieveData();
		retrieveData2();
	}

}
