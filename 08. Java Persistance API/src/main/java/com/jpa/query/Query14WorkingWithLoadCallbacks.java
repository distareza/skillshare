package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.jpa.query.Query13WorkingWithPresistenceCallback.Department;
import com.jpa.query.Query13WorkingWithPresistenceCallback.Employee;

/**
 * Demonstrate how to hook into the entity lifecycle after load
 * 
 *
 */
public class Query14WorkingWithLoadCallbacks {

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			Department tech = em.find(Department.class, 1);
			System.out.println(tech);
			
			Department operation = em.find(Department.class, 2);
			System.out.println(operation);
			
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
====================== After loading employee object : Ben
{ Name: Tech, Employeess : [Name : Alice, Name : Ben] }
Hibernate: 
    select
        query13wor0_.id as id1_27_0_,
        query13wor0_.name as name2_27_0_ 
    from
        departments query13wor0_ 
    where
        query13wor0_.id=?
====================== After loading department object : Operations
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
{ Name: Operations, Employeess : [Name : Cora, Name : Dennis] }

			 * 
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	public static void retrieveQuery() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			TypedQuery<Department> deptQuery = em.createQuery("select d from departments d", Department.class);
			List<Department> deptList = deptQuery.getResultList();
			System.out.println(deptList);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output : 
Hibernate: 
    select
        query13wor0_.id as id1_27_,
        query13wor0_.name as name2_27_ 
    from
        departments query13wor0_
====================== After loading department object : Tech
====================== After loading department object : Operations
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
====================== After loading employee object : Ben
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
[{ Name: Tech, Employeess : [Name : Alice, Name : Ben] }, { Name: Operations, Employeess : [Name : Dennis, Name : Cora] }]
		 * 
		 */
	}
	
	public static void retrieveQuery2() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CompanyDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {
			
			TypedQuery<Employee> empQuery = em.createQuery("select e from employee e", Employee.class);
			List<Employee> empList = empQuery.getResultList();
			System.out.println(empList);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
		
		/**
		 * output : 
Hibernate: 
    select
        query13wor0_.id as id1_34_,
        query13wor0_.department_id as departme3_34_,
        query13wor0_.name as name2_34_ 
    from
        employee query13wor0_
Hibernate: 
    select
        query13wor0_.id as id1_27_0_,
        query13wor0_.name as name2_27_0_ 
    from
        departments query13wor0_ 
    where
        query13wor0_.id=?
====================== After loading department object : Tech
Hibernate: 
    select
        query13wor0_.id as id1_27_0_,
        query13wor0_.name as name2_27_0_ 
    from
        departments query13wor0_ 
    where
        query13wor0_.id=?
====================== After loading department object : Operations
====================== After loading employee object : Alice
====================== After loading employee object : Ben
====================== After loading employee object : Dennis
====================== After loading employee object : Cora
[Name : Alice, Name : Ben, Name : Dennis, Name : Cora]
		 * 
		 */
	}	
	
	public static void main(String[] args) {
		//retrieveData();
//		retrieveQuery();
		retrieveQuery2();
	}
}
