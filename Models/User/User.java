package finalproject.pos.Models.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fname")
    private String fName;
    @Column(name = "lname")
    private String lName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "role")
    private String role;
    @Column(name = "status")
    private String status;

    public boolean isActive() {
        return this.status.equals("Active");
    }
}
