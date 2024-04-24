package com.itabrek.baseback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String body;

    @Column(name = "publish_date", nullable = false)
    private Date publishDate = new Date();

    @ManyToOne
    private User user;
}
