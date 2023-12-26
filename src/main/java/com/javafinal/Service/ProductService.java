package com.javafinal.Service;

import com.javafinal.Model.Product;
import com.javafinal.Model.ProductNotFoundException;
import com.javafinal.Model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired private ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public Product get(Integer productId) throws ProductNotFoundException {
        Optional<Product> result = repo.findById(productId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ProductNotFoundException("Could not find any products with ID " + productId);
    }

    public void delete(Integer productId) throws ProductNotFoundException {
        Long count = repo.countByProductId(productId);
        if (count == null || count == 0) {
            throw new ProductNotFoundException("Could not find any products with ID " + productId);
        }
        repo.deleteById(productId);
    }
}
