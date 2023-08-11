package com.github.viktor2308.newsfeed.service.impl;

import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.repository.CategoryRepository;
import com.github.viktor2308.newsfeed.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
