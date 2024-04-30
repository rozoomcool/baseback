package com.itabrek.baseback.dto;

import com.itabrek.baseback.entity.ProductCategory;
import com.itabrek.baseback.entity.Review;
import com.itabrek.baseback.entity.Tag;
import com.itabrek.baseback.entity.UserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String title;
    private String description;
    private double price;
    private int count;
    private String quantity;
    private ProductCategory category;
    private List<Tag> tags;
    private List<Review> reviews;
    private UserDataResponse ownerUserData;
    private Date createdAt;
    private Date updatedAt;
}
