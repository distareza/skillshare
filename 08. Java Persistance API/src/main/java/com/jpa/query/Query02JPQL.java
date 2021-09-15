package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpa.query.Query01Native.Product;

/**
 * Demonstrate how JPQL Queries in Hibernate
 * 	uses method createQuery() 
 *
 */
public class Query02JPQL {

	/**
	 * how to use JPLQ Query 
	 * 
	 */
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			Query categoryQuery = em.createQuery("select c from tb_categories c"); // tb_category is the name of Entity Class
			List<?> categories = categoryQuery.getResultList();
			categories.forEach(System.out::println);
			
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_78_,
        query01nat0_.name as name2_78_ 
    from
        tb_categories query01nat0_
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
{ Mobile Phones, [{ Samsumg Galaxy, 299.00 }, { iPhone 6S, 699.00 }] }
{ Fashion, [{ Jeans, 78.99 }, { Designer Skirt, 49.00 }, { Scarf, 19.99 }, { Belt, 9.90 }] }
{ Home, [{ Sporinkler, 89.00 }, { Notebook, 9.00 }] }
{ School, [{ Pen, 4.99 }] }
			 * 
			 * 
			 * notice , even if we haven't explicitly specified that categories correspon to the category entity, 
			 * JPQL query know the result have been retrieved by creating the correct entity object
			 * 
			 */

			System.out.println(String.format("Position of first result : %d", categoryQuery.getFirstResult()));
			System.out.println(String.format("Max Result retrieved : %d", categoryQuery.getMaxResults()));
			/**
			 * additinoal parameter when executing JPQL Query
			 *  
Position of first result : 0
Max Result retrieved : 2147483647
			 * 
			 */
			
			Query productQuery = em.createQuery("select p from tb_products p");
			List<?> products = productQuery.getResultList();
			products.forEach(System.out::println);
			
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
	 * demonstrate how to configure JPQL to retrieve custom result
	 */
	public static void retrieveData2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			Query categoryQuery2 = em.createQuery("select c from tb_categories c"); // tb_category is the name of Entity Class
			categoryQuery2.setFirstResult(1);
			categoryQuery2.setMaxResults(2);
			System.out.println(String.format("Position of first result : %d", categoryQuery2.getFirstResult()));
			System.out.println(String.format("Max Result retrieved : %d", categoryQuery2.getMaxResults()));
			List<?> categoryList = categoryQuery2.getResultList();
			categoryList.forEach(System.out::println);
			/**
			 * output :
Position of first result : 1
Max Result retrieved : 2
Hibernate: 
    select
        query01nat0_.id as id1_78_,
        query01nat0_.name as name2_78_ 
    from
        tb_categories query01nat0_ limit ?,
        ?
{ Fashion, [{ Jeans, 78.99 }, { Designer Skirt, 49.00 }, { Scarf, 19.99 }, { Belt, 9.90 }] }
{ Home, [{ Sporinkler, 89.00 }, { Notebook, 9.00 }] }
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
	 * demonstrate how to use Typed Query for performing auto cast operation to the result
	 */
	public static void retrieveData3() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Product> productQuery = em.createQuery("select p from tb_products p", Product.class); // tb_products is the name of Entity Class
			List<Product> productList = productQuery.getResultList();
			productList.forEach(System.out::println);
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

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}

	public static void main(String[] args) {
		//retrieveData();
		//retrieveData2();
		retrieveData3();
	}

}
