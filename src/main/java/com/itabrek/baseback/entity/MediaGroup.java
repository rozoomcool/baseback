package com.itabrek.baseback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "media_groups")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class MediaGroup {
}
