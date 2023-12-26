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

    public void setId(Integer id) {
        Id = id;
    }


    public void setProductId(Product productId) {
        ProductId = productId;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
