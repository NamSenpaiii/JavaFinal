package com.javafinal.Model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Order orderId;
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product ProductId;
    @Column()
    private Integer Quantity;

    public OrderDetails() {
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "Id=" + Id +
                ", orderId=" + orderId +
                ", ProductId=" + ProductId +
                ", Quantity=" + Quantity +
                '}';
    }
}
