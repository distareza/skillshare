package com.mytutorial.springbootrestapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mytutorial.springbootrestapi.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
