package com.jpa.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.query.Query13WorkingWithPresistenceCallback.Department;

/**
 * To Demonstrate how to hook into the entity lifecycle before and after entity removal
 * 
 *
 */
public class Query16WorkingWithRemoveCallbacks {

	public static void removeData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Department department = em.find(Department.class, 1);

			em.remove(department);
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output :
Hibernate: 
    select
        query13wor0_.id as id1_27_0_,
        query13wor0_.name as name2_27_0_ 
    from
        departments query13wor0_ 
    where
        query13wor0_.id=?
====================== After loading department object : Engineering
====================== Before remove department object : Engineering
Hibernate: 
    select
        employees0_.department_id as departme3_34_0_,
        employees0_.id as id1_34_0_,
        employees0_.id as id1_34_1_,
        employees0_.department_id as departme3_34_1_,
        employees0_.name as name2_34_1_ 
    from
        employee employees0_ 
    where
        employees0_.department_id=?
====================== After loading employee object : Alice
====================== After loading employee object : Zoe
====================== Before remove employee object : Alice
====================== Before remove employee object : Zoe
Hibernate: 
    update
        employee 
    set
        department_id=null 
    where
        department_id=?
Hibernate: 
    delete 
    from
        employee 
    where
        id=?
====================== After remove employee object : Alice
Hibernate: 
    delete 
    from
        employee 
    where
        id=?
====================== After remove employee object : Zoe
Hibernate: 
    delete 
    from
        departments 
    where
        id=?
====================== After remove department object : Engineering
		 * 
		 */

	}
	
	public static void main(String[] args) {
		removeData();

	}

}
