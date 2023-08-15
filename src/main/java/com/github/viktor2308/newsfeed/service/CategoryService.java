package com.github.viktor2308.newsfeed.service;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryByName(String category);

    List<CategoryDto> findAllCategory();

    CategoryDto getCategoryById(Long id);

    CategoryDto addCategory(CategoryDto newCategoryDto);

    CategoryDto updateCategory(long id, CategoryDto categoryDto);

    boolean deleteNews(long id);
}
