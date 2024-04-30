package com.itabrek.baseback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private int count;
    private String quantity;
    @ManyToOne
    private ProductCategory category;
    @ManyToMany
    private List<Tag> tags;
    @OneToMany
    private List<Review> reviews;
    @ManyToOne
    private UserData ownerUserData;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
