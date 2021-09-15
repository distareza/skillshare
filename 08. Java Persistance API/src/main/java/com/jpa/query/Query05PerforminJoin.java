package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.jpa.query.Query01Native.Category;

/**
 * Demonstrate how to perform join operations using JPQL queries
 *
 */
public class Query05PerforminJoin {

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Category> categoryQuery = em.createQuery("select c from tb_categories c inner join c.products", Category.class);
			List<Category> categories = categoryQuery.getResultList();
			System.out.println(categories);
			
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_78_,
        query01nat0_.name as name2_78_ 
    from
        tb_categories query01nat0_ 
    inner join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id
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
[	
	{ Mobile Phones, [{ iPhone 6S, 699.00 }, { Samsumg Galaxy, 299.00 }] }, 
	{ Mobile Phones, [{ iPhone 6S, 699.00 }, { Samsumg Galaxy, 299.00 }] }, 
	{ Fashion, [{ Designer Skirt, 49.00 }, { Belt, 9.90 }, { Jeans, 78.99 }, { Scarf, 19.99 }] }, 
	{ Fashion, [{ Designer Skirt, 49.00 }, { Belt, 9.90 }, { Jeans, 78.99 }, { Scarf, 19.99 }] }, 
	{ Fashion, [{ Designer Skirt, 49.00 }, { Belt, 9.90 }, { Jeans, 78.99 }, { Scarf, 19.99 }] }, 
	{ Fashion, [{ Designer Skirt, 49.00 }, { Belt, 9.90 }, { Jeans, 78.99 }, { Scarf, 19.99 }] }, 
	{ Home, [{ Notebook, 9.00 }, { Sporinkler, 89.00 }] }, 
	{ Home, [{ Notebook, 9.00 }, { Sporinkler, 89.00 }] }, 
	{ School, [{ Pen, 4.99 }] }
]
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

			TypedQuery<Category> categoryQuery = em.createQuery("select c from tb_categories c inner join c.products p where p.price > ?1", Category.class);
			categoryQuery.setParameter(1, 80f);
			List<Category> categories = categoryQuery.getResultList();
			System.out.println(categories);
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_78_,
        query01nat0_.name as name2_78_ 
    from
        tb_categories query01nat0_ 
    inner join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        products1_.price>?
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
[{ Mobile Phones, [{ Samsumg Galaxy, 299.00 }, { iPhone 6S, 699.00 }] }, { Mobile Phones, [{ Samsumg Galaxy, 299.00 }, { iPhone 6S, 699.00 }] }, { Home, [{ Sporinkler, 89.00 }, { Notebook, 9.00 }] }]
			 * 
			 */
			
			TypedQuery<Category> categoryQuery2 = em.createQuery("select c from tb_categories c inner join fetch c.products p where p.price > ?1", Category.class);
			categoryQuery2.setParameter(1, 80f);
			List<Category> categories2 = categoryQuery2.getResultList();
			System.out.println(categories2);
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.id as id1_78_0_,
        products1_.id as id1_79_1_,
        query01nat0_.name as name2_78_0_,
        products1_.category_id as category4_79_1_,
        products1_.name as name2_79_1_,
        products1_.price as price3_79_1_,
        products1_.category_id as category4_79_0__,
        products1_.id as id1_79_0__ 
    from
        tb_categories query01nat0_ 
    inner join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        products1_.price>?
[{ Mobile Phones, [{ Samsumg Galaxy, 299.00 }, { iPhone 6S, 699.00 }] }, { Mobile Phones, [{ Samsumg Galaxy, 299.00 }, { iPhone 6S, 699.00 }] }, { Home, [{ Sporinkler, 89.00 }] }]
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
	 * demonstrate how to use fetch join to allows you to fetch related entities in a single query
	 * it is more performance
	 */
	public static void retrieveData3() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Category> categoryQuery = em.createQuery("select c from tb_categories c inner join fetch c.products p where c.name = ?1 and p.price > ?2", Category.class);
			categoryQuery.setParameter(1, "Fashion");
			categoryQuery.setParameter(2, 60f);
			List<Category>categories = categoryQuery.getResultList();
			System.out.println(categories);
			
			/**
			 * output : 
Hibernate: 
    select
        query01nat0_.id as id1_78_0_,
        products1_.id as id1_79_1_,
        query01nat0_.name as name2_78_0_,
        products1_.category_id as category4_79_1_,
        products1_.name as name2_79_1_,
        products1_.price as price3_79_1_,
        products1_.category_id as category4_79_0__,
        products1_.id as id1_79_0__ 
    from
        tb_categories query01nat0_ 
    inner join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    where
        query01nat0_.name=? 
        and products1_.price>?
[{ Fashion, [{ Jeans, 78.99 }] }]

-- result If not using fetch keyword :
[{ Fashion, [{ Scarf, 19.99 }, { Designer Skirt, 49.00 }, { Jeans, 78.99 }, { Belt, 9.90 }] }]  
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
