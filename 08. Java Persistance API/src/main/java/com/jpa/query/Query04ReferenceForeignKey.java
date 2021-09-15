package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.jpa.query.Query01Native.Product;

/**
 * Demonstrate how to perform join operations using JPQL queries
 *
 */
public class Query04ReferenceForeignKey {

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Product> productQuery = em.createQuery("select p from tb_products p where p.category.id = :category_id", Product.class);
			productQuery.setParameter("category_id", 231);
			
			List<Product> products = productQuery.getResultList();
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
        query01nat0_.category_id=?
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
[{ Designer Skirt, 49.00 }, { Jeans, 78.99 }, { Scarf, 19.99 }, { Belt, 9.90 }]
			 * 
			 */
			
			productQuery = em.createQuery("select p from tb_products p where p.category.id = ?1 and p.price > ?2", Product.class);
			productQuery.setParameter(1, 231);
			productQuery.setParameter(2, 10f);
			
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
        query01nat0_.category_id=? 
        and query01nat0_.price>?
[{ Designer Skirt, 49.00 }, { Jeans, 78.99 }, { Scarf, 19.99 }]
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
	}

}
