package com.github.viktor2308.newsfeed.service.impl;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.repository.CategoryRepository;
import com.github.viktor2308.newsfeed.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    @Override
    public Category getCategoryByName(String category) {
        return repository.existsCategoriesByCategory(category)
                ? repository.findByCategory(category)
                : repository.save(Category.builder().category(category).build());
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return null;
    }

    @Override
    public CategoryDto addCategory(CategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public boolean deleteNews(long id) {
        return false;
    }
}
