package finalproject.pos.Controllers;

import finalproject.pos.Models.User.User;
import finalproject.pos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "UserManagement";
    }

    @PostMapping("/add")
    public String add(String Email, String Password, String Username, String FirstName, String LastName, String Role) {
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "CreateUser";
    }
}
