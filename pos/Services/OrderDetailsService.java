package com.javafinal.Service;

import com.javafinal.Model.CustomerRepository;
import com.javafinal.Model.Order;
import com.javafinal.Model.OrderDetails;
import com.javafinal.Model.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired private OrderDetailsRepository repo;
    public List<OrderDetails> getOrderDetailsByOrderId(Order order) {
        return repo.findByOrderId(order);
    }

    public Boolean isExistInAnyOrder(Integer pid){
        return repo.existsById(pid);
    }
}
