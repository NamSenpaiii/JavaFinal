package com.javafinal;

import com.javafinal.Model.Customer;
import com.javafinal.Model.Product;
import com.javafinal.Model.User;
import com.javafinal.Service.ProductService;
import com.javafinal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @GetMapping("/Login")
    public String login(Model model) {
        userService.createAdmin();
        model.addAttribute("user", new User());
        return "Login";
    }
//    @GetMapping("user/sales")
//    public String showNewSale(Model model){
//        List<Product> productList = productService.listAll();
//        model.addAttribute("productList", productList);
//        model.addAttribute("customer", new Customer());
//        return "user/sales";
//    }
}
