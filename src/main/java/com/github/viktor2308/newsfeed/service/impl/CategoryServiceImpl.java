package com.github.viktor2308.newsfeed.service.impl;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.exception.CategoryNotFoundException;
import com.github.viktor2308.newsfeed.mapper.CategoryMapper;
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
        return repository.findAll().stream()
                .map(CategoryMapper.INSTANCE::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = repository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category with id " + id + " not found."));
        return CategoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    public CategoryDto addCategory(CategoryDto newCategoryDto) {
        Category category = repository.save(CategoryMapper.INSTANCE.toCategory(newCategoryDto));
        return CategoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
        Category category = repository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category with id " + id + " not found."));
        category.setCategory(categoryDto.getCategory());
        repository.save(category);
        return CategoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    public boolean deleteNews(long id) {
        return repository.deleteByCategoryId(id);
    }
}
