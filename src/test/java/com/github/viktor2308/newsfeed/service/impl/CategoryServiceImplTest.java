package com.github.viktor2308.newsfeed.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.repository.CategoryRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;


    @Test
    void testGetCategoryByName() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        when(categoryRepository.findByCategory(Mockito.<String>any())).thenReturn(category);
        when(categoryRepository.existsCategoriesByCategory(Mockito.<String>any())).thenReturn(true);
        assertSame(category, categoryServiceImpl.getCategoryByName("Category"));
        verify(categoryRepository).existsCategoriesByCategory(Mockito.<String>any());
        verify(categoryRepository).findByCategory(Mockito.<String>any());
    }


    @Test
    void testGetCategoryByName2() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        when(categoryRepository.existsCategoriesByCategory(Mockito.<String>any())).thenReturn(false);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        assertSame(category, categoryServiceImpl.getCategoryByName("Category"));
        verify(categoryRepository).existsCategoriesByCategory(Mockito.<String>any());
        verify(categoryRepository).save(Mockito.<Category>any());
    }
}

