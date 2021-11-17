package com.mvc.annualsciencefair.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class MySQLConnectionTest {
	
	@Autowired
	private JdbcTemplate jt;

	@Test
	public void testCheckConnection() throws Exception {
		String query = "SELECT count(1) from registrations";
		int rowFound = jt.queryForObject(query, Integer.class);
		System.out.println(String.format("Query registration table and row found %d", rowFound));
		
		assertTrue(true);
	}
	
}
