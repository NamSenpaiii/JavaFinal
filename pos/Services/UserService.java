package com.javafinal.Service;


import com.javafinal.Model.User;
import com.javafinal.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return userRepository.findAll();
    }
    @Transactional
    public User add(User user) {
        try {
            user.setUsername(getUsername(user.getEmail()));
            user.setPassword(hashPassword(user.getUsername()));
            user.setRole("user");
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String getUsername(String email){
        return email.substring(0,email.indexOf("@"));
    }

    @Transactional
    public boolean changePassword(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            System.out.println(user.getUsername());
            user.setPassword(hashPassword(password));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public boolean isPasswordMatch(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public boolean deleteByUsername(String username) {
        try {
            userRepository.deleteByUsername(userRepository.findByUsername(username).getUsername());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
