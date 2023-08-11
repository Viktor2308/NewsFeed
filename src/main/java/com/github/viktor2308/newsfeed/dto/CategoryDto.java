package com.github.viktor2308.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @NotBlank
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Name must contain only letters a-z and A-Z")
    @Length(min = 2, max = 15, message = "Breed length must be from 2 to 15")
    private String category;
}
