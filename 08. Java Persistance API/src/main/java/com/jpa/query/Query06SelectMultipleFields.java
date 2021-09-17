package com.jpa.query;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Demonstrate how retrieve multiple fields using JPQL queries
 * 
 *
 */
public class Query06SelectMultipleFields {

	@SuppressWarnings("unchecked")
	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Long> countQuery = em.createQuery("select count(c) from tb_categories c ", Long.class);
			System.out.println(countQuery.getSingleResult());
			/**
			 * output :
Hibernate: 
    select
        count(query01nat0_.id) as col_0_0_ 
    from
        tb_categories query01nat0_
4
			 * 
			 */
			
			TypedQuery<Double> averageQuery = em.createQuery("select avg(p.price) from tb_products p ", Double.class);
			System.out.println(averageQuery.getSingleResult());
			/**
			 * output:
Hibernate: 
    select
        avg(query01nat0_.price) as col_0_0_ 
    from
        tb_products query01nat0_
139.87444411383734
			 */
			
			Query aggQuery = em.createQuery("select c.name, avg(p.price) from tb_categories c inner join c.products p group by c.name");
			List<Object[]> resultList = aggQuery.getResultList();
			resultList.forEach(r -> System.out.println(Arrays.toString(r)));
			/**
			 * output:
Hibernate: 
    select
        query01nat0_.name as col_0_0_,
        avg(products1_.price) as col_1_0_ 
    from
        tb_categories query01nat0_ 
    inner join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    group by
        query01nat0_.name
[Fashion, 39.46999931335449]
[Home, 49.0]
[Mobile Phones, 499.0]
[School, 4.989999771118164]
			 */
			
			aggQuery = em.createQuery("select c.name, max(p.price) from tb_categories c inner join c.products p group by c.name having max(p.price) > ?1");
			aggQuery.setParameter(1,  50f);
			resultList = aggQuery.getResultList();
			resultList.forEach(r -> System.out.println(Arrays.toString(r)));
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.name as col_0_0_,
        max(products1_.price) as col_1_0_ 
    from
        tb_categories query01nat0_ 
    inner join
        tb_products products1_ 
            on query01nat0_.id=products1_.category_id 
    group by
        query01nat0_.name 
    having
        max(products1_.price)>?
[Fashion, 78.99]
[Home, 89.0]
[Mobile Phones, 699.0]
			 * 
			 */
			
			aggQuery = em.createQuery("select c.name, c.id from tb_categories c where exists (select p from tb_products p where price > ?1 and p.category.id = c.id) ");
			aggQuery.setParameter(1,  50f);
			resultList = aggQuery.getResultList();
			resultList.forEach(r -> System.out.println(Arrays.toString(r)));
			/**
			 * output
Hibernate: 
    select
        query01nat0_.name as col_0_0_,
        query01nat0_.id as col_1_0_ 
    from
        tb_categories query01nat0_ 
    where
        exists (
            select
                query01nat1_.id 
            from
                tb_products query01nat1_ 
            where
                query01nat1_.price>? 
                and query01nat1_.category_id=query01nat0_.id
        )
[Mobile Phones, 221]
[Fashion, 231]
[Home, 241]
			 * 
			 */
			
			Query complexQuery = em.createQuery(
						"select p.name, " +
						"	p.price, " +
						"	case p.category.id " +
						" 	when 221 then 'Mobiles and Accessories' " +
						"	when 241 then 'Home and Kitchen' " +
						"	else p.category.name end " +
						" from tb_products p");
			List<Object[]> resultList2 = complexQuery.getResultList();
			resultList2.forEach(r -> System.out.println(Arrays.toString(r)));
			/**
			 * output :
Hibernate: 
    select
        query01nat0_.name as col_0_0_,
        query01nat0_.price as col_1_0_,
        case query01nat0_.category_id 
            when 221 then 'Mobiles and Accessories' 
            when 241 then 'Home and Kitchen' 
            else query01nat1_.name 
        end as col_2_0_ 
    from
        tb_products query01nat0_ cross 
    join
        tb_categories query01nat1_ 
    where
        query01nat0_.category_id=query01nat1_.id
[iPhone 6S, 699.0, Mobiles and Accessories]
[Samsumg Galaxy, 299.0, Mobiles and Accessories]
[Designer Skirt, 49.0, Fashion]
[Jeans, 78.99, Fashion]
[Scarf, 19.99, Fashion]
[Belt, 9.9, Fashion]
[Sporinkler, 89.0, Home and Kitchen]
[Notebook, 9.0, Home and Kitchen]
[Pen, 4.99, School]
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
