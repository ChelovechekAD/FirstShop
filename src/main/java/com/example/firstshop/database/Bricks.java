package com.example.firstshop.database;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Entity
public class Bricks {
    @Id
    @SequenceGenerator(name = "bricksIdSeq", sequenceName = "bricks_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bricksIdSeq")
    private Long id;
    private String name;
    private String color;
    private String company;
    private Double price;
    private Integer count;
    private String rate;
    private String description;

    public Bricks() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
