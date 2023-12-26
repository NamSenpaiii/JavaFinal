package com.javafinal.Controller;

import com.javafinal.Model.Product;
import com.javafinal.Model.ProductNotFoundException;
import com.javafinal.Model.User;
import com.javafinal.Service.OrderDetailsService;
import com.javafinal.Service.ProductService;
import com.javafinal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes("username")
public class ProductControllerForUser {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @GetMapping("/products")
    public String showProductList(@ModelAttribute("username") String username, Model model){
        List<Product> productList = productService.listAll();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("productList", productList);
        return "user/products";
    }
}
