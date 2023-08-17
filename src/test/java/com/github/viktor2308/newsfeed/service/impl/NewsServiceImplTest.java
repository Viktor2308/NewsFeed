package com.github.viktor2308.newsfeed.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import com.github.viktor2308.newsfeed.repository.NewsRepository;
import com.github.viktor2308.newsfeed.service.CategoryService;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NewsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class NewsServiceImplTest {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private NewsRepository newsRepository;

    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @Test
    void testGetAllNews() {
        when(newsRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.getAllNews().isEmpty());
        verify(newsRepository).findAll();
    }

    @Test
    void testCreateNews() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());

        News news = new News();
        news.setCategory(category);
        news.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news.setNewsId(1L);
        news.setText("Text");
        news.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());
        when(categoryService.getCategoryByName(Mockito.<String>any())).thenReturn(category2);
        NewsDto actualCreateNewsResult = newsServiceImpl.createNews(new CreateNewsDto("Dr", "Text", "Category"));
        assertEquals("Category", actualCreateNewsResult.getCategory());
        assertEquals("Dr", actualCreateNewsResult.getTitle());
        assertEquals("Text", actualCreateNewsResult.getText());
        assertEquals(1L, actualCreateNewsResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualCreateNewsResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(categoryService).getCategoryByName(Mockito.<String>any());
    }
}