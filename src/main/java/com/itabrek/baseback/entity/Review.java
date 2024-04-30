package com.itabrek.baseback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int rating;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserData userdata;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
