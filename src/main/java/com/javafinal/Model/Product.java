package com.javafinal.Model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @Column(length = 100)
    private String barcode;
    @Column(length = 100)
    private String productName;
    @Column()
    private Double importPrice;
    @Column()
    private Double retailPrice;
    @Column(length = 100)
    private String category;
    @Column()
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;
    @Column(length = 100)
    private String description;

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", barcode='" + barcode + '\'' +
                ", productName='" + productName + '\'' +
                ", importPrice=" + importPrice +
                ", retailPrice=" + retailPrice +
                ", category='" + category + '\'' +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                '}';
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImportPrice(Double importPrice) {
        this.importPrice = importPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
