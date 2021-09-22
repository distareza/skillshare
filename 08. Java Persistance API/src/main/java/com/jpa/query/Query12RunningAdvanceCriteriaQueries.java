package com.jpa.query;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jpa.query.Query10CriteriaAPI.Product;

/**
 * Demonstrate how to run advanced criteria queries
 * without using JPQL
 *
 */
public class Query12RunningAdvanceCriteriaQueries {
	

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<String> productCQ = cb.createQuery(String.class);			
			Root<Product> rootProduct = productCQ.from(Product.class);			
			productCQ.select(rootProduct.get("name"));			
			TypedQuery<String> query = em.createQuery(productCQ);
			query.getResultList().forEach(System.out::println);

			/**
			 * output:
Hibernate: 
    select
        query10cri0_.name as col_0_0_ 
    from
        Product2 query10cri0_
        
iPhone 6S
Samsumg Galaxy
Designer Skirt
Jeans
Scarf
Belt
Sporinkler
Notebook
Pen
Diary of a Wimpy Kid
Awful Anties
Timmy Failure
Apple
Orange
Lemons
			 * 
			 */
			
			
			CriteriaBuilder cb2 = em.getCriteriaBuilder();
			CriteriaQuery<Object[]> productCQ2 = cb2.createQuery(Object[].class);			
			Root<Product> rootProduct2 = productCQ2.from(Product.class);			
			//productCQ2.select(cb2.array( rootProduct2.get("name"), rootProduct2.get("price"))); // option #1
			productCQ2.multiselect( rootProduct2.get("name"), rootProduct2.get("price") ) ; // option #2
			TypedQuery<Object[]> query2 = em.createQuery(productCQ2);
			query2.getResultList().forEach(r -> System.out.println(Arrays.toString(r)));
			
			/**
			 * output :
Hibernate: 
    select
        query10cri0_.name as col_0_0_,
        query10cri0_.price as col_1_0_ 
    from
        Product2 query10cri0_
        
[iPhone 6S, 699.0]
[Samsumg Galaxy, 299.0]
[Designer Skirt, 49.0]
[Jeans, 78.99]
[Scarf, 19.99]
[Belt, 9.9]
[Sporinkler, 89.0]
[Notebook, 9.0]
[Pen, 4.99]
[Diary of a Wimpy Kid, 5.99]
[Awful Anties, 3.99]
[Timmy Failure, 4.99]
[Apple, 2.99]
[Orange, 1.29]
[Lemons, 0.99]
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
			CriteriaQuery<Object[]> productCQ = cb.createQuery(Object[].class);
			
			Root<Product> rootProduct = productCQ.from(Product.class);
			
			productCQ.multiselect(rootProduct.get("category"), cb.count(rootProduct)).groupBy(rootProduct.get("category"));
			
			TypedQuery<Object[]> query = em.createQuery(productCQ);
			query.getResultList().forEach(r -> System.out.println(Arrays.toString(r)));

			/**
			 * output :
Hibernate: 
    select
        query10cri0_.category_id as col_0_0_,
        count(query10cri0_.id) as col_1_0_,
        query10cri1_.id as id1_13_,
        query10cri1_.name as name2_13_ 
    from
        Product2 query10cri0_ 
    inner join
        Categories2 query10cri1_ 
            on query10cri0_.category_id=query10cri1_.id 
    group by
        query10cri0_.category_id
        
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


[{ Mobile Phones, [{ 1002, Samsumg Galaxy, 299.00 }, { 1001, iPhone 6S, 699.00 }] }, 2]
[{ Fashion, [{ 1003, Designer Skirt, 49.00 }, { 1006, Belt, 9.90 }, { 1004, Jeans, 78.99 }, { 1005, Scarf, 19.99 }] }, 4]
[{ Home, [{ 1008, Notebook, 9.00 }, { 1007, Sporinkler, 89.00 }] }, 2]
[{ School, [{ 1009, Pen, 4.99 }] }, 1]
[{ Books, [{ 1010, Diary of a Wimpy Kid, 5.99 }, { 1011, Awful Anties, 3.99 }, { 1012, Timmy Failure, 4.99 }] }, 3]
[{ Groceries, [{ 1013, Apple, 2.99 }, { 1015, Lemons, 0.99 }, { 1014, Orange, 1.29 }] }, 3]
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
