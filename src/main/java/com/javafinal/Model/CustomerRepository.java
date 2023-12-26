package com.javafinal.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
