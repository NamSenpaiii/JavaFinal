package finalproject.pos.Models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);

    void deleteByUsername(String username);
}
