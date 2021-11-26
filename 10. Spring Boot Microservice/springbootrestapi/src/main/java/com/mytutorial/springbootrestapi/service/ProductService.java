package com.mytutorial.springbootrestapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.mytutorial.springbootrestapi.dao.ProductRepository;
import com.mytutorial.springbootrestapi.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
			
	@Cacheable("products")
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);		
		return products;
	}

	@Cacheable(value = "product", key = "#p0")
	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}
	
	@CacheEvict(value = "products", allEntries = true)
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	@Caching(evict = {
			@CacheEvict(value = "product", key = "#p0"),
			@CacheEvict(value = "products", allEntries = true)
	})
	public void updateProduct(Long id, Product product) {
		if (productRepository.findById(id).get()!=null)
			productRepository.save(product);
	}
	
	@Caching(evict = {
			@CacheEvict(value = "product", key = "#p0"),
			@CacheEvict(value = "products", allEntries = true)
	})
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
}
