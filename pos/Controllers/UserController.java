package finalproject.pos.Controllers;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import finalproject.pos.Models.User.User;
import finalproject.pos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {
    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final String encodedKey;

    public UserController(@Autowired UserService userService, @Autowired JavaMailSender mailSender) {
        this.userService = userService;
        this.javaMailSender = mailSender;
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKey secretKey = keyGen.generateKey();
        this.encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    @GetMapping("/admin")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "UserManagement";
    }

    @PostMapping("/admin/add")
    public String add(User user, Model model) {
        // Check if email is already registered
        if (null != userService.findByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "CreateUser";
        }
        userService.add(user);

        // Create JWT token
        String jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 60000)) // 1 minute
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();

        // Create password reset link
        String loginLink = "http://localhost:8080/Login?username=" + user.getUsername() + "&token=" + jwtToken;

        // Send email
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Our Service!");
            String htmlContent = "<h1>Welcome, " + user.getFname() + " " + user.getLname() + "!</h1>"
                    + "<p>Your account has been created. Please login using the link below:</p>"
                    + "<a href=\"" + loginLink + "\">Login Here</a>"
                    + "<p>Best,<br>Your Service Team</p>";
            helper.setText(htmlContent, true); // Set the second parameter to 'true' to indicate that the text is HTML
            javaMailSender.send(mail);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "CreateUser";
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "CreateUser";
    }

    @GetMapping("/ChangePassword")
    public String changePassword(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("user", new User());
        return "ChangePassword";
    }

    @PostMapping("/ChangePassword")
    public String changePassword(@RequestParam String username, @ModelAttribute User user, Model model) {
        String password = user.getPassword();
        //update password
        if (userService.changePassword(username, password)) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Something went wrong please try again");
            return "ChangePassword";
        }
    }

    @GetMapping("/Login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "Login";
    }

    @PostMapping("/Login")
    public String login(@RequestParam String username, @RequestParam String token, @ModelAttribute User user, Model model) {
        User user1 = userService.findByUsername(username);
        if (user1 == null) {
            model.addAttribute("error", "Invalid username");
            return "Login";
        } else {
            //login first time but not via email
            if ((username.isEmpty() || token.isEmpty()) && (userService.isPasswordMatch(user.getUsername(), user1.getPassword()))) {
                model.addAttribute("error", "Please login via email");
                return "Login";
            } else if (!username.isEmpty() && !token.isEmpty()) {
                //check token is valid
                try {
                    Jwts.parser().setSigningKey(encodedKey).parseClaimsJws(token);
                } catch (Exception e) {
                    model.addAttribute("error", "Invalid token or token expired");
                    return "Login";
                }
                //login via email
                if (userService.isPasswordMatch(user.getPassword(), user1.getPassword())) {
                    //check status
                    if (!user1.isActive()) {
                        model.addAttribute("error", "Your account is not active");
                        return "Login";
                    }
                    return "redirect:/ChangePassword?username=" + username;
                } else {
                    model.addAttribute("error", "Invalid username or password");
                    return "Login";
                }
            }
            //login via username
            //is not first time login (password changed)
            else if (!userService.isPasswordMatch(user.getUsername(), user1.getPassword())) {
                //check password
                if (userService.isPasswordMatch(user.getPassword(), user1.getPassword())) {
                    //check status
                    if (!user1.isActive()) {
                        model.addAttribute("error", "Your account is not active");
                        return "Login";
                    }
                    if (user1.getRole().equals("admin"))
                        return "redirect:/admin";
                    else
                        return "redirect:/";
                }
            }
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }
    }

    @PostMapping("admin/sendmail")
    public String sendmail(@RequestParam String username, Model model) {
        User user = userService.findByUsername(username);
        String jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 60000)) // 1 minute
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();

        // Create password reset link
        String loginLink = "http://localhost:8080/Login?username=" + user.getUsername() + "&token=" + jwtToken;

        // Send email
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Our Service!");
            String htmlContent = "<h1>Welcome, " + user.getFname() + " " + user.getLname() + "!</h1>"
                    + "<p>Your account has been created. Please login using the link below:</p>"
                    + "<a href=\"" + loginLink + "\">Login Here</a>"
                    + "<p>Best,<br>Your Service Team</p>";
            helper.setText(htmlContent, true); // Set the second parameter to 'true' to indicate that the text is HTML
            javaMailSender.send(mail);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/admin";
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam String username, Model model) {
        if (userService.deleteByUsername(username)) {
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Something went wrong please try again");
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/edit")
    public String edit(@RequestParam String username, Model model) {
        User user = userService.findByUsername(username);
        if (user.getStatus().equals("active")) {
            user.setStatus("inactive");
        } else {
            user.setStatus("active");
        }
        if (userService.update(user)) {
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Something went wrong please try again");
            return "redirect:/admin";
        }
    }

    @GetMapping("/profile")
    public String profile(@RequestParam String username, Model model) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "UserProfile";
    }

    @PostMapping("/profile/upload")
    public String upload(@RequestParam String username, @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        // Define the path to the static folder
        String location = "/img/UserPicture/";
        String path = new File("src/main/resources/static/img/UserPicture/").getAbsolutePath();

        User user = userService.findByUsername(username);

        // Get the original filename
        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());

        // Extract the file extension
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Create the new filename using the username and the original file extension
        String newFilename = username + fileExtension;

        // Build the path to the file
        Path filePath = Paths.get(path, newFilename);

        try {
            // Create directories if they do not exist
            Files.createDirectories(filePath.getParent());

            // Save the file to the static folder
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            user.setAvatar(location + newFilename);
            userService.update(user);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to save image: " + e.getMessage());
            model.addAttribute("user", user);
            return "UserProfile";
        }
        return "redirect:/";
    }
}
