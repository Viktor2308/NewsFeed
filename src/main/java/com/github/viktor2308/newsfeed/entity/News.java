package com.github.viktor2308.newsfeed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "news")
public class News {
    @Id
    private Long newsId;
    private String title;
    private String text;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;
}