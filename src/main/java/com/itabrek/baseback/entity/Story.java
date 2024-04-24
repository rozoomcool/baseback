package com.itabrek.baseback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shorts")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name = "video_title", nullable = false)
    private String videoTitle;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
