package com.jpa.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.query.Query13WorkingWithPresistenceCallback.Department;
import com.jpa.query.Query13WorkingWithPresistenceCallback.Employee;

/**
 * Demonstrate how to hook into the entity lifecycle before and after entity load
 * 
 *
 */
public class Query15WorkingWithUpdateCallbacks {

	public static void updateData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Department tech = em.find(Department.class, 1);
			tech.setName("Engineering");
			
			em.merge(tech);
			
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
====================== After loading department object : Tech
====================== Before update department object : Engineering
Hibernate: 
    update
        departments 
    set
        name=? 
    where
        id=?
====================== After update department object : Engineering
		 * 
		 */

	}
	
	public static void updateData2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Department operations = em.find(Department.class, 2);
			operations.setName("Ops");
			
			em.merge(operations);
			
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
====================== After loading department object : Operations
====================== Before update department object : Ops
Hibernate: 
    update
        departments 
    set
        name=? 
    where
        id=?
====================== After update department object : Ops
		 * 
		 */

	}

	public static void updateData3() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Department operations = em.find(Department.class, 2);
			operations.setName("Operations");
			
			Employee elise = new Employee("Elise");
			operations.addEmployees(elise);
			
			em.merge(operations);
			
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
====================== After loading department object : Ops
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
====================== After loading employee object : Dennis
====================== After loading employee object : Cora
====================== Before perisisting employee object : Elise
Hibernate: 
    select
        next_val as id_val 
    from
        hibernate_sequence for update
            
Hibernate: 
    update
        hibernate_sequence 
    set
        next_val= ? 
    where
        next_val=?
====================== Before update department object : Operations
Hibernate: 
    insert 
    into
        employee
        (department_id, name, id) 
    values
        (?, ?, ?)
====================== After perisisting employee object : Elise
Hibernate: 
    update
        departments 
    set
        name=? 
    where
        id=?
====================== After update department object : Operations
Hibernate: 
    update
        employee 
    set
        department_id=? 
    where
        id=?
		 * 
		 */

	}

	public static void updateData4() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Employee employee = em.find(Employee.class, 2);
			employee.setName("Zoe");
			
			em.merge(employee);
			
			em.getTransaction().commit();
		} catch (Exception ex) {
			
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output:
Hibernate: 
    select
        query13wor0_.id as id1_34_0_,
        query13wor0_.department_id as departme3_34_0_,
        query13wor0_.name as name2_34_0_,
        query13wor1_.id as id1_27_1_,
        query13wor1_.name as name2_27_1_ 
    from
        employee query13wor0_ 
    left outer join
        departments query13wor1_ 
            on query13wor0_.department_id=query13wor1_.id 
    where
        query13wor0_.id=?
====================== After loading employee object : Ben
====================== After loading department object : Engineering
====================== Before update employee object : Zoe
Hibernate: 
    update
        employee 
    set
        department_id=?,
        name=? 
    where
        id=?
====================== After update employee object : Zoe
		 * 
		 */
	}
	
	public static void main(String[] args) {
		//updateData();
		//updateData2();
		//updateData3();
		updateData4();
	}

}
