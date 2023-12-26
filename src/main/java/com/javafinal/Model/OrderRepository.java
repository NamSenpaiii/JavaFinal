package com.javafinal.Model;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
}
