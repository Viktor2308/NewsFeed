package com.github.viktor2308.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private long id;
    private String title;
    private String text;
    private String category;
    private String created;
}
