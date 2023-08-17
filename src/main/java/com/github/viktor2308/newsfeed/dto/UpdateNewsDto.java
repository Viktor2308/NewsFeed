package com.github.viktor2308.newsfeed.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsDto {
    @Size(min = 3, max = 50)
    private String title;
    @Size(min = 3, max = 1000)
    private String text;
}
