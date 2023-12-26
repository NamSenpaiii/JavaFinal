package com.javafinal.Controller;

import com.javafinal.Model.User;
import com.javafinal.Service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import com.javafinal.Model.Product;
import com.javafinal.Model.ProductNotFoundException;
import com.javafinal.Service.OrderDetailsService;
import com.javafinal.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("username")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private UserService userService;


    @GetMapping("/products")
    public String showProductList(@ModelAttribute("username") String username, Model model){
        List<Product> productList = productService.listAll();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("productList", productList);
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String AddNewProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle","Add New Product");
        return "admin/addNewProduct";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes ra) {
        productService.save(product);
        ra.addFlashAttribute("message", "The Product has been saved successfully.");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Product product = productService.get(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");

            return "admin/addNewProduct";
        } catch (ProductNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/products";
        }
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            if (orderDetailsService.isExistInAnyOrder(id)) {
                ra.addFlashAttribute("message", "Delete failed because the product ID " + id + " already exists in some order.");
                return "redirect:/admin/products";
            } else {
                productService.delete(id);
                ra.addFlashAttribute("message", "The product ID " + id + " has been deleted.");
            }
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("message", "Delete failed because the product ID " + id + " already exists in some order.");
        } catch (ProductNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/products";
    }
}
