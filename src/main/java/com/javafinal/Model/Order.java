package com.javafinal.Model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "UserId",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "CustomerId",referencedColumnName = "Id")
    private Customer CustomerId;
    @Column()
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    @Column(length = 100)
    private String PaymentMethod;
    @Column()
    private Double Total;

    public Order() {
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCustomerId(Customer customerId) {
        CustomerId = customerId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Id=" + Id +
                ", UserId=" + user +
                ", CustomerId=" + CustomerId +
                ", OrderDate=" + orderDate +
                ", PaymentMethod='" + PaymentMethod + '\'' +
                ", Total=" + Total +
                '}';
    }
}
