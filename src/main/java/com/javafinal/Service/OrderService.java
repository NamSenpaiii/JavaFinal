package com.javafinal.Service;
import com.javafinal.Model.Order;
import com.javafinal.Model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repo;
    public List<Order> listAll(){
        return (List<Order>) repo.findAll();
    }
    public List<Order> findByDateRange(String startDateStr, String endDateStr) {
        try {
            // Parse the string dates to Date objects
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Call the repository method
            return repo.findByOrderDateBetween(startDate, endDate);
        } catch (ParseException e) {
            // Handle parsing exceptions
            throw new RuntimeException("Error parsing dates", e);
        }
    }

    public Optional<Order> findById(Integer orderId) {
        return repo.findById(orderId);
    }
}
