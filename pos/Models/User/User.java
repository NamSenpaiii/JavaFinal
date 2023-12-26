package com.javafinal.Model;

import jakarta.persistence.*;
import lombok.*;

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
    private String fname;
    @Column(name = "lname")
    private String lname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email" , unique = true)
    private String email;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "role",columnDefinition = "varchar(255) default 'user'")
    private String role;
    @Column(name = "status",columnDefinition = "varchar(255) default 'active'")
    private String status;

    public boolean isActive() {
        return this.status.equals("active");
    }
}
