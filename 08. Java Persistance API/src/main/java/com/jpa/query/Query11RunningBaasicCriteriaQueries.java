package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.jpa.query.Query10CriteriaAPI.Product;

/**
 * Demonstrate how to run basic criteria queries
 * without using JPQL
 *
 */
public class Query11RunningBaasicCriteriaQueries {
	

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Product> productCQ = cb.createQuery(Product.class);
			
			Root<Product> rootProduct = productCQ.from(Product.class);
			
			productCQ.select(rootProduct)
			//.where(cb.equal(rootProduct.get("id"), 1011));
			.where(cb.greaterThan(rootProduct.get("price"), 5));
			
			TypedQuery<Product> query = em.createQuery(productCQ);
			List<Product> resultList = query.getResultList();
			resultList.forEach(r -> System.out.println(r));

			/**
			 * output :
Hibernate: 
    select
        query10cri0_.id as id1_70_,
        query10cri0_.category_id as category5_70_,
        query10cri0_.inStock as inStock2_70_,
        query10cri0_.name as name3_70_,
        query10cri0_.price as price4_70_ 
    from
        Product2 query10cri0_ 
    where
        query10cri0_.id=1011
Hibernate: 
    select
        query10cri0_.id as id1_13_0_,
        query10cri0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cri0_ 
    left outer join
        Product2 products1_ 
            on query10cri0_.id=products1_.category_id 
    where
        query10cri0_.id=?
{ Awful Anties, 3.99 }

=============================================================================
Hibernate: 
    select
        query10cri0_.id as id1_70_,
        query10cri0_.category_id as category5_70_,
        query10cri0_.inStock as inStock2_70_,
        query10cri0_.name as name3_70_,
        query10cri0_.price as price4_70_ 
    from
        Product2 query10cri0_ 
    where
        query10cri0_.price>5.0
        
{ iPhone 6S, 699.00 }
{ Samsumg Galaxy, 299.00 }
{ Designer Skirt, 49.00 }
{ Jeans, 78.99 }
{ Scarf, 19.99 }
{ Belt, 9.90 }
{ Sporinkler, 89.00 }
{ Notebook, 9.00 }
{ Diary of a Wimpy Kid, 5.99 }        
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
			
			Predicate equaltToPredicate = cb.equal(rootProduct.get("category"), 261);
			
			productCQ.select(rootProduct)
			//	.where(equaltToPredicate);
				.where(equaltToPredicate.not());
			
			TypedQuery<Product> query = em.createQuery(productCQ);
			List<Product> resultList = query.getResultList();
			resultList.forEach(r -> System.out.println(r));

			/**
			 * output :
Hibernate: 
    select
        query10cri0_.id as id1_70_,
        query10cri0_.category_id as category5_70_,
        query10cri0_.inStock as inStock2_70_,
        query10cri0_.name as name3_70_,
        query10cri0_.price as price4_70_ 
    from
        Product2 query10cri0_ 
    where
        query10cri0_.category_id=261
Hibernate: 
    select
        query10cri0_.id as id1_13_0_,
        query10cri0_.name as name2_13_0_,
        products1_.category_id as category5_70_1_,
        products1_.id as id1_70_1_,
        products1_.id as id1_70_2_,
        products1_.category_id as category5_70_2_,
        products1_.inStock as inStock2_70_2_,
        products1_.name as name3_70_2_,
        products1_.price as price4_70_2_ 
    from
        Categories2 query10cri0_ 
    left outer join
        Product2 products1_ 
            on query10cri0_.id=products1_.category_id 
    where
        query10cri0_.id=?
        
{ 1010, Diary of a Wimpy Kid, 5.99 }
{ 1011, Awful Anties, 3.99 }
{ 1012, Timmy Failure, 4.99 }

=========================================================
Hibernate: 
    select
        query10cri0_.id as id1_70_,
        query10cri0_.category_id as category5_70_,
        query10cri0_.inStock as inStock2_70_,
        query10cri0_.name as name3_70_,
        query10cri0_.price as price4_70_ 
    from
        Product2 query10cri0_ 
    where
        query10cri0_.category_id<>261

{ 1001, iPhone 6S, 699.00 }
{ 1002, Samsumg Galaxy, 299.00 }
{ 1003, Designer Skirt, 49.00 }
{ 1004, Jeans, 78.99 }
{ 1005, Scarf, 19.99 }
{ 1006, Belt, 9.90 }
{ 1007, Sporinkler, 89.00 }
{ 1008, Notebook, 9.00 }
{ 1009, Pen, 4.99 }
{ 1013, Apple, 2.99 }
{ 1014, Orange, 1.29 }
{ 1015, Lemons, 0.99 }

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
