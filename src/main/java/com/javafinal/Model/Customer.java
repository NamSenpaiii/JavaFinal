package com.javafinal.Model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String fullName;

    @Column(length = 100)
    private String address;
    @Column(length = 100, unique = true)
    private String phoneNumber;
    public Customer() {
    }

    public Customer(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
