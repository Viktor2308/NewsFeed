package com.github.viktor2308.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewsDto {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String category;
}
