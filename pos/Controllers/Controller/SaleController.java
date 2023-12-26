package com.javafinal.Controller;

import com.javafinal.Model.*;
import com.javafinal.Service.CustomerService;
import com.javafinal.Service.OrderDetailsService;
import com.javafinal.Service.OrderService;
import com.javafinal.Service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class SaleController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @GetMapping("/sales")
    public String showNewSale(Model model){
        List<Product> productList = productService.listAll();
        model.addAttribute("productList", productList);
        model.addAttribute("customer", new Customer());
        return "admin/sales";
    }
    @GetMapping("/sales/{p}")
    public String showNewSale(@PathVariable("p") String p, Model model){
        Optional<Customer> existingCustomer = customerService.findByPhoneNumber(p);

        if (existingCustomer.isPresent()) {
            model.addAttribute("customer", existingCustomer.get());
            List<Product> productList = productService.listAll();
            model.addAttribute("productList", productList);
            return "admin/sales";
        } else {
            return "redirect:/admin/salesHistory";
        }
    }
    @GetMapping("/salesHistory")
    public String showSaleHistory(){
        /*List<Order> orderList = orderService.listAll();
        model.addAttribute("orderList", orderList);*/
        /*for(Order o : orderList) {
            System.out.println(o.toString());
        }*/
        return "admin/salesHistory";
    }


    @GetMapping("/sales/checkCustomer/{p}")
    public String checkCustomer(@PathVariable("p") String p, Model model, RedirectAttributes ra) {
        try {
            Optional<Customer> existingCustomer = customerService.findByPhoneNumber(p);

            if (existingCustomer.isPresent()) {
                model.addAttribute("customer", existingCustomer.get());
                //System.out.println(existingCustomer.get());
                ra.addFlashAttribute("message", "The Customer is exist.");
                return "redirect:/admin/sales/{p}";
            } else {
                // Customer does not exist, create a new instance for manual entry
                model.addAttribute("customer", new Customer(p));
                ra.addFlashAttribute("message", "The Customer is not exist. Please enter all fields.");
            }

            return "redirect:/admin/sales";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /*catch (CustomerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/sales";
        }*/
    }
    @PostMapping("/sales/save")
    public String saveOrder(Customer customer,Model model, RedirectAttributes ra) {
        customerService.save(customer);
        model.addAttribute("customer", customer);
        ra.addFlashAttribute("message", "The Customer has been saved successfully.");

        return "redirect:/admin/sales";
    }

    @GetMapping("/sales/search")
    public String searchSales(@RequestParam("startDate") String startDate,
                              @RequestParam("endDate") String endDate,
                              Model model) {

        List<Order> orderList = orderService.findByDateRange(startDate, endDate);

        model.addAttribute("orderList", orderList);
        return "admin/salesHistory";
    }

    @GetMapping("/sales/details/{orderId}")
    public String showOrderDetails(@PathVariable("orderId") Integer orderId, Model model) {
        Optional<Order> order = orderService.findById(orderId);

        if(order.isPresent() ) {
            List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByOrderId(order.get());
            model.addAttribute("order", order.get());
            model.addAttribute("orderDetailsList", orderDetailsList);
            return "admin/orderDetails";
        } else {
            System.out.println(orderId);

            return "redirect:/admin/salesHistory";
        }
    }
}
