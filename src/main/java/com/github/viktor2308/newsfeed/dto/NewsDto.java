package com.github.viktor2308.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private long newsId;
    private String title;
    private String text;
    private String category;
    private String created;
}
