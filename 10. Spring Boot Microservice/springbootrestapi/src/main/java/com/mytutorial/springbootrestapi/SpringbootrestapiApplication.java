package com.mytutorial.springbootrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.mytutorial.springbootrestapi.dao.ProductRepository;
import com.mytutorial.springbootrestapi.model.Product;

@SpringBootApplication
@EnableCaching
public class SpringbootrestapiApplication implements CommandLineRunner {
	
	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootrestapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productRepository.save(new Product("Television", "Electornics"));
		productRepository.save(new Product("Air Conditioner", "Electornics"));
		productRepository.save(new Product("Sofa", "Electornics"));
		productRepository.save(new Product("Cushions", "Home Essentials"));
		productRepository.save(new Product("Wardrobe", "Furniture"));
	}
	
}
