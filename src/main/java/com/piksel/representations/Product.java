package com.piksel.representations;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String currency;
    @Column(name = "regular_price")
    @NotNull
    private Double regularPrice;
    @Column(name = "discount_price")
    @NotNull
    private Double discountPrice;

    @JoinColumn(name = "seller_id")
    private Long seller_id;


    public Product() {
    }

    public Product(Long id, String name, String currency, Double regularPrice, Double discountPrice, Long seller_id) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.regularPrice = regularPrice;
        this.discountPrice = discountPrice;
        this.seller_id = seller_id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Long seller_id) {
        this.seller_id = seller_id;
    }
}
