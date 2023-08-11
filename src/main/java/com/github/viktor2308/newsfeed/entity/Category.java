package com.github.viktor2308.newsfeed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    private Long categoryId;
    private String category;
    @OneToMany(mappedBy = "category")
    private List<News> newsList= new ArrayList<>();
}
