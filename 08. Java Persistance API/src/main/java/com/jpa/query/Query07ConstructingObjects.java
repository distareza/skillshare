package com.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Demonstrate how to construct special objects to hold multiple fields within JPQL queries
 * 
 *
 */
public class Query07ConstructingObjects {
	
	public static class CategoryPrice {
		private String name;
		private Double avgPrice;
		public CategoryPrice() {
		}
		public CategoryPrice(String name, Double avgPrice) {
			this.name = name;
			this.avgPrice = avgPrice;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double getAvgPrice() {
			return avgPrice;
		}
		public void setAvgPrice(Double avgPrice) {
			this.avgPrice = avgPrice;
		}
		@Override
		public String toString() {
			return String.format("Cateory Name : %s, Average Price : %.2f", this.name, this.avgPrice);
		}
	}

	public static void retrieveData() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit_Read");
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<CategoryPrice> query = em.createQuery("select new com.jpa.query.Query07ConstructingObjects$CategoryPrice(c.name, avg(p.price)) from tb_categories c inner join c.products p group by c.name", CategoryPrice.class);
			List<CategoryPrice> resultList = query.getResultList();
			resultList.forEach(r -> System.out.println(r));
			/**
			 * output :
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
Cateory Name : Fashion, Average Price : 39.47
Cateory Name : Home, Average Price : 49.00
Cateory Name : Mobile Phones, Average Price : 499.00
Cateory Name : School, Average Price : 4.99
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
