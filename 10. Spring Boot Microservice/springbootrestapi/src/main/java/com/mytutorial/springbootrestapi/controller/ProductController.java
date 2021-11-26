package com.mytutorial.springbootrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mytutorial.springbootrestapi.exception.ProductNotFoundException;
import com.mytutorial.springbootrestapi.model.Product;
import com.mytutorial.springbootrestapi.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/products/{pId}")
	public Product getProduct(@PathVariable("pId") Long id) {
		return productService.getProduct(id).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@PostMapping("/products")
	public void addProduct(@RequestBody Product product) {
		productService.addProduct(product);
	}
	
	@PutMapping("/products/{pId}")
	public void updateProduct(@RequestBody Product product, @PathVariable("pId") Long id) {
		productService.updateProduct(id, product);
	}
	
	@DeleteMapping("/products/{pId}")
	public void deleteProduct(@PathVariable("pId") Long id) {
		productService.deleteProduct(id);
	}
}
