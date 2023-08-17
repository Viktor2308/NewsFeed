package com.github.viktor2308.newsfeed.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import com.github.viktor2308.newsfeed.exception.CategoryNotFoundException;
import com.github.viktor2308.newsfeed.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        when(categoryRepository.findByCategory(Mockito.any())).thenReturn(category);
        when(categoryRepository.existsCategoriesByCategory(Mockito.any())).thenReturn(true);
        assertSame(category, categoryServiceImpl.getCategoryByName("Category"));
        verify(categoryRepository).existsCategoriesByCategory(Mockito.any());
        verify(categoryRepository).findByCategory(Mockito.any());
    }


    @Test
    void testGetCategoryByName2() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        when(categoryRepository.existsCategoriesByCategory(Mockito.any())).thenReturn(false);
        when(categoryRepository.save(Mockito.any())).thenReturn(category);
        assertSame(category, categoryServiceImpl.getCategoryByName("Category"));
        verify(categoryRepository).existsCategoriesByCategory(Mockito.any());
        verify(categoryRepository).save(Mockito.any());
    }

    @Test
    void testFindAllCategory() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(categoryServiceImpl.findAllCategory().isEmpty());
        verify(categoryRepository).findAll();
    }

    @Test
    void testFindAllCategory2() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDto> actualFindAllCategoryResult = categoryServiceImpl.findAllCategory();
        assertEquals(1, actualFindAllCategoryResult.size());
        assertEquals("Category", actualFindAllCategoryResult.get(0).getCategory());
        verify(categoryRepository).findAll();
    }

    @Test
    void testFindAllCategory3() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());

        Category category2 = new Category();
        category2.setCategory("com.github.viktor2308.newsfeed.entity.Category");
        category2.setCategoryId(2L);
        category2.setNewsList(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category2);
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDto> actualFindAllCategoryResult = categoryServiceImpl.findAllCategory();
        assertEquals(2, actualFindAllCategoryResult.size());
        assertEquals("com.github.viktor2308.newsfeed.entity.Category", actualFindAllCategoryResult.get(0).getCategory());
        assertEquals("Category", actualFindAllCategoryResult.get(1).getCategory());
        verify(categoryRepository).findAll();
    }

    @Test
    void testFindAllCategory4() {
        when(categoryRepository.findAll()).thenThrow(new CategoryNotFoundException("An error occurred"));
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.findAllCategory());
        verify(categoryRepository).findAll();
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals("Category", categoryServiceImpl.getCategoryById(1L).getCategory());
        verify(categoryRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetCategoryById2() {
        Category category = new Category(1L, "Category", new ArrayList<>());
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals("Category", categoryServiceImpl.getCategoryById(1L).getCategory());
        verify(categoryRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetCategoryById3() {
        Category category = mock(Category.class);
        when(category.getCategory()).thenReturn("Category");
        doNothing().when(category).setCategory(Mockito.any());
        doNothing().when(category).setCategoryId(Mockito.any());
        doNothing().when(category).setNewsList(Mockito.any());
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.any())).thenReturn(ofResult);
        assertEquals("Category", categoryServiceImpl.getCategoryById(1L).getCategory());
        verify(categoryRepository).findById(Mockito.any());
        verify(category).getCategory();
        verify(category).setCategory(Mockito.any());
        verify(category).setCategoryId(Mockito.any());
        verify(category).setNewsList(Mockito.any());
    }

    @Test
    void testGetCategoryById4() {
        when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.getCategoryById(1L));
        verify(categoryRepository).findById(Mockito.any());
    }

    @Test
    void testAddCategory() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        assertEquals("Category", categoryServiceImpl.addCategory(new CategoryDto("Category")).getCategory());
        verify(categoryRepository).save(Mockito.<Category>any());
    }

    @Test
    void testAddCategory2() {
        Category category = mock(Category.class);
        when(category.getCategory()).thenReturn("Category");
        doNothing().when(category).setCategory(Mockito.<String>any());
        doNothing().when(category).setCategoryId(Mockito.<Long>any());
        doNothing().when(category).setNewsList(Mockito.<List<News>>any());
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        assertEquals("Category", categoryServiceImpl.addCategory(new CategoryDto("Category")).getCategory());
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(category).getCategory();
        verify(category).setCategory(Mockito.<String>any());
        verify(category).setCategoryId(Mockito.<Long>any());
        verify(category).setNewsList(Mockito.<List<News>>any());
    }

    @Test
    void testAddCategory3() {
        Category category = mock(Category.class);
        when(category.getCategory()).thenReturn("Category");
        doNothing().when(category).setCategory(Mockito.<String>any());
        doNothing().when(category).setCategoryId(Mockito.<Long>any());
        doNothing().when(category).setNewsList(Mockito.<List<News>>any());
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        assertEquals("Category", categoryServiceImpl.addCategory(null).getCategory());
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(category).getCategory();
        verify(category).setCategory(Mockito.<String>any());
        verify(category).setCategoryId(Mockito.<Long>any());
        verify(category).setNewsList(Mockito.<List<News>>any());
    }

    @Test
    void testAddCategory4() {
        Category category = mock(Category.class);
        doNothing().when(category).setCategory(Mockito.<String>any());
        doNothing().when(category).setCategoryId(Mockito.<Long>any());
        doNothing().when(category).setNewsList(Mockito.<List<News>>any());
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        CategoryDto newCategoryDto = mock(CategoryDto.class);
        when(newCategoryDto.getCategory()).thenThrow(new CategoryNotFoundException("An error occurred"));
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.addCategory(newCategoryDto));
        verify(category).setCategory(Mockito.<String>any());
        verify(category).setCategoryId(Mockito.<Long>any());
        verify(category).setNewsList(Mockito.<List<News>>any());
        verify(newCategoryDto).getCategory();
    }

    @Test
    void testUpdateCategory() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals("Category", categoryServiceImpl.updateCategory(1L, new CategoryDto("Category")).getCategory());
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Long>any());
    }
    @Test
    void testUpdateCategory2() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.save(Mockito.<Category>any()))
                .thenThrow(new CategoryNotFoundException("An error occurred"));
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(CategoryNotFoundException.class,
                () -> categoryServiceImpl.updateCategory(1L, new CategoryDto("Category")));
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testUpdateCategory3() {
        Category category = mock(Category.class);
        when(category.getCategory()).thenReturn("Category");
        doNothing().when(category).setCategory(Mockito.<String>any());
        doNothing().when(category).setCategoryId(Mockito.<Long>any());
        doNothing().when(category).setNewsList(Mockito.<List<News>>any());
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals("Category", categoryServiceImpl.updateCategory(1L, new CategoryDto("Category")).getCategory());
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryRepository).findById(Mockito.<Long>any());
        verify(category).getCategory();
        verify(category, atLeast(1)).setCategory(Mockito.<String>any());
        verify(category).setCategoryId(Mockito.<Long>any());
        verify(category).setNewsList(Mockito.<List<News>>any());
    }

    @Test
    void testUpdateCategory4() {
        when(categoryRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class,
                () -> categoryServiceImpl.updateCategory(1L, new CategoryDto("Category")));
        verify(categoryRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testDeleteNews() {
        when(categoryRepository.deleteByCategoryId(anyLong())).thenReturn(true);
        assertTrue(categoryServiceImpl.deleteNews(1L));
        verify(categoryRepository).deleteByCategoryId(anyLong());
    }

    @Test
    void testDeleteNews2() {
        when(categoryRepository.deleteByCategoryId(anyLong())).thenReturn(false);
        assertFalse(categoryServiceImpl.deleteNews(1L));
        verify(categoryRepository).deleteByCategoryId(anyLong());
    }

    @Test
    void testDeleteNews3() {
        when(categoryRepository.deleteByCategoryId(anyLong()))
                .thenThrow(new CategoryNotFoundException("An error occurred"));
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.deleteNews(1L));
        verify(categoryRepository).deleteByCategoryId(anyLong());
    }
}

