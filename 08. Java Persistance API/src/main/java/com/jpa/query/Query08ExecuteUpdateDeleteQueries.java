package com.jpa.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Demonstrate how to execute update and delete with JPQL queries
 * 
 *
 */
public class Query08ExecuteUpdateDeleteQueries {
	
	public static void updateData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();

			Query updateQuery = em.createQuery("update tb_products p set p.name= 'Gel Pens' where p.name = :name");
			updateQuery.setParameter("name", "Pen");
			
			int rowsUpdated = updateQuery.executeUpdate();
			System.out.println(String.format("Number of rows updated : %d", rowsUpdated));
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}			
		
		/**
		 * output :
Hibernate: 
    update
        tb_products 
    set
        name='Gel Pens' 
    where
        name=?
Number of rows updated : 1
		 * 
		 */
	}
	
	public static void updateCompletexData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();

			Query updateQuery = em.createQuery(
					"update tb_categories c set c.name = "
					+ " case "
					+ "  when c.id = 221 then 'Mobiles and Accessories' "
					+ "  when c.id = 241 then 'Home and Kitchen' "
					+ "  else c.name "
					+ " end"
				);			
			int rowsUpdated = updateQuery.executeUpdate();
			System.out.println(String.format("Number of rows updated : %d", rowsUpdated));
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}			
		
		/**
		 * output :
Hibernate: 
    update
        tb_categories 
    set
        name=case 
            when id=221 then 'Mobiles and Accessories' 
            when id=241 then 'Home and Kitchen' 
            else name 
        end
Number of rows updated : 4
		 * 
		 */
	}
	
	public static void deleteData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();

			Query deleteQuery = em.createQuery("delete tb_products p where p.id > :id");
			deleteQuery.setParameter("id", 1007);
			
			int rowsUpdated = deleteQuery.executeUpdate();
			System.out.println(String.format("Number of rows deleted : %d", rowsUpdated));
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}		
		/**
		 * output :
Hibernate: 
    delete 
    from
        tb_products 
    where
        id>?
Number of rows deleted : 2
		 * 
		 */
	}
	
	
	public static void main(String[] args) {
		//updateData();
		//updateCompletexData();
		deleteData();
	}

}
