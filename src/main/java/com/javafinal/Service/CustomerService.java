package com.javafinal.Service;

import com.javafinal.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public List<Customer> listAll(){
        return (List<Customer>) repo.findAll();
    }

    public void save(Customer customer) {
        repo.save(customer);
    }


    public Optional<Customer> findByPhoneNumber(String phoneNumber) {

        return repo.findByPhoneNumber(phoneNumber);
    }

    public Optional<Customer> findById(Integer customerId) {
        return repo.findById(customerId);
    }
}
