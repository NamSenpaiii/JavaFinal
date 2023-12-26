package com.javafinal.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public boolean isActive() {
        return this.status.equals("active");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status +
                '}';
    }
    public String getFullName(){
        return this.fname + " " + this.lname;
    }
}
