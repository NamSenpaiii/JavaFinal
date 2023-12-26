package com.javafinal.Model;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    public Long countByProductId(Integer id);
}
