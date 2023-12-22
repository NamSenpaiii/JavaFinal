package finalproject.pos.Services;

import finalproject.pos.Models.User.User;
import finalproject.pos.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getId());
        }
        return userRepository.findAll();
    }

    public  User add(User user) {
        try {
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
}
