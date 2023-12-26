package com.javafinal.Controller;

import com.javafinal.Model.*;
import com.javafinal.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@SessionAttributes("username")
@Controller
public class SaleControllerForUser {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private UserService userService;

    @GetMapping("/sales")
    public String showNewSale(Model model,@ModelAttribute("username") String username){
        List<Product> productList = productService.listAll();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("productList", productList);
        model.addAttribute("customer", new Customer());
        return "user/sales";
    }
    @GetMapping("/sales/{p}")
    public String showNewSale(@PathVariable("p") String p, Model model,@ModelAttribute("username") String username){
        Optional<Customer> existingCustomer = customerService.findByPhoneNumber(p);

        if (existingCustomer.isPresent()) {
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("customer", existingCustomer.get());
            List<Product> productList = productService.listAll();
            model.addAttribute("productList", productList);
            return "user/sales";
        } else {
            return "redirect:/user/salesHistory";
        }
    }
    @GetMapping("/salesHistory")
    public String showSaleHistory(@ModelAttribute("username") String username ,Model model){
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        /*List<Order> orderList = orderService.listAll();
        model.addAttribute("orderList", orderList);*/
        /*for(Order o : orderList) {
            System.out.println(o.toString());
        }*/
        return "user/salesHistory";
    }


    @GetMapping("/sales/checkCustomer/{p}")
    public String checkCustomer(@PathVariable("p") String p, Model model, RedirectAttributes ra, @ModelAttribute("username") String username) {
        try {
            Optional<Customer> existingCustomer = customerService.findByPhoneNumber(p);

            if (existingCustomer.isPresent()) {
                model.addAttribute("customer", existingCustomer.get());
                User user = userService.findByUsername(username);
                model.addAttribute("user", user);
                //System.out.println(existingCustomer.get());
                ra.addFlashAttribute("message", "The Customer is exist.");
                return "redirect:/user/sales/{p}";
            } else {
                // Customer does not exist, create a new instance for manual entry
                model.addAttribute("customer", new Customer(p));
                ra.addFlashAttribute("message", "The Customer is not exist. Please enter all fields.");
            }

            return "redirect:/user/sales";
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

        return "redirect:/user/sales";
    }

    @GetMapping("/sales/search")
    public String searchSales(@RequestParam("startDate") String startDate,
                              @RequestParam("endDate") String endDate,
                              Model model,@ModelAttribute("username") String username) {

        List<Order> orderList = orderService.findByDateRange(startDate, endDate);
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("orderList", orderList);
        return "user/salesHistory";
    }

    @GetMapping("/sales/details/{orderId}")
    public String showOrderDetails(@PathVariable("orderId") Integer orderId, Model model,@ModelAttribute("username") String username) {
        Optional<Order> order = orderService.findById(orderId);

        if(order.isPresent() ) {
            List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByOrderId(order.get());
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("order", order.get());
            model.addAttribute("orderDetailsList", orderDetailsList);
            return "user/orderDetails";
        } else {
            System.out.println(orderId);
            return "redirect:/user/salesHistory";
        }
    }

}
