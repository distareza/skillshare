package com.mytutorial.springbootrestapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mytutorial.springbootrestapi.model.Product;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService service;
	
	@Test
	public void testAddProduct() {
		service.addProduct(new Product("Television", "Electornics"));
		service.addProduct(new Product("Air Conditioner", "Electornics"));
		service.addProduct(new Product("Sofa", "Electornics"));
		service.addProduct(new Product("Cushions", "Home Essentials"));
		service.addProduct(new Product("Wardrobe", "Furniture"));
	}
	
}
