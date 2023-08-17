package com.github.viktor2308.newsfeed.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.dto.SearchDto;
import com.github.viktor2308.newsfeed.dto.UpdateNewsDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import com.github.viktor2308.newsfeed.exception.NewsNotfoundException;
import com.github.viktor2308.newsfeed.repository.NewsRepository;
import com.github.viktor2308.newsfeed.service.CategoryService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
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
    void testGetAllNews2() {
        when(newsRepository.findAllByOrderByNewsIdDesc()).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.getAllNews().isEmpty());
        verify(newsRepository).findAllByOrderByNewsIdDesc();
    }

    @Test
    void testGetAllNews3() {
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

        ArrayList<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.findAllByOrderByNewsIdDesc()).thenReturn(newsList);
        List<NewsDto> actualAllNews = newsServiceImpl.getAllNews();
        assertEquals(1, actualAllNews.size());
        NewsDto getResult = actualAllNews.get(0);
        assertEquals("Category", getResult.getCategory());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Text", getResult.getText());
        assertEquals(1L, getResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", getResult.getCreated());
        verify(newsRepository).findAllByOrderByNewsIdDesc();
    }

    @Test
    void testGetAllNews4() {
        when(newsRepository.findAllByOrderByNewsIdDesc()).thenThrow(new NewsNotfoundException("An error occurred"));
        assertThrows(NewsNotfoundException.class, () -> newsServiceImpl.getAllNews());
        verify(newsRepository).findAllByOrderByNewsIdDesc();
    }

    @Test
    void testGetAllNews5() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());
        News news = mock(News.class);
        when(news.getCategory()).thenReturn(category2);
        when(news.getNewsId()).thenReturn(1L);
        when(news.getText()).thenReturn("Text");
        when(news.getTitle()).thenReturn("Dr");
        when(news.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news).setCategory(Mockito.any());
        doNothing().when(news).setCreated(Mockito.any());
        doNothing().when(news).setNewsId(Mockito.any());
        doNothing().when(news).setText(Mockito.any());
        doNothing().when(news).setTitle(Mockito.any());
        news.setCategory(category);
        news.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news.setNewsId(1L);
        news.setText("Text");
        news.setTitle("Dr");

        ArrayList<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.findAllByOrderByNewsIdDesc()).thenReturn(newsList);
        List<NewsDto> actualAllNews = newsServiceImpl.getAllNews();
        assertEquals(1, actualAllNews.size());
        NewsDto getResult = actualAllNews.get(0);
        assertEquals("Category", getResult.getCategory());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Text", getResult.getText());
        assertEquals(1L, getResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", getResult.getCreated());
        verify(newsRepository).findAllByOrderByNewsIdDesc();
        verify(news).getCategory();
        verify(news, atLeast(1)).getNewsId();
        verify(news).getText();
        verify(news).getTitle();
        verify(news).getCreated();
        verify(news).setCategory(Mockito.any());
        verify(news).setCreated(Mockito.any());
        verify(news).setNewsId(Mockito.any());
        verify(news).setText(Mockito.any());
        verify(news).setTitle(Mockito.any());
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
        when(categoryService.getCategoryByName(Mockito.any())).thenReturn(category2);
        NewsDto actualCreateNewsResult = newsServiceImpl.createNews(new CreateNewsDto("Dr", "Text", "Category"));
        assertEquals("Category", actualCreateNewsResult.getCategory());
        assertEquals("Dr", actualCreateNewsResult.getTitle());
        assertEquals("Text", actualCreateNewsResult.getText());
        assertEquals(1L, actualCreateNewsResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualCreateNewsResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testCreateNews2() {
        when(categoryService.getCategoryByName(Mockito.any()))
                .thenThrow(new NewsNotfoundException("An error occurred"));
        assertThrows(NewsNotfoundException.class,
                () -> newsServiceImpl.createNews(new CreateNewsDto("Dr", "Text", "Category")));
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testCreateNews3() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());
        News news = mock(News.class);
        when(news.getCategory()).thenReturn(category2);
        when(news.getNewsId()).thenReturn(1L);
        when(news.getText()).thenReturn("Text");
        when(news.getTitle()).thenReturn("Dr");
        when(news.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news).setCategory(Mockito.<Category>any());
        doNothing().when(news).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news).setNewsId(Mockito.any());
        doNothing().when(news).setText(Mockito.any());
        doNothing().when(news).setTitle(Mockito.any());
        news.setCategory(category);
        news.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news.setNewsId(1L);
        news.setText("Text");
        news.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news);

        Category category3 = new Category();
        category3.setCategory("Category");
        category3.setCategoryId(1L);
        category3.setNewsList(new ArrayList<>());
        when(categoryService.getCategoryByName(Mockito.any())).thenReturn(category3);
        NewsDto actualCreateNewsResult = newsServiceImpl.createNews(new CreateNewsDto("Dr", "Text", "Category"));
        assertEquals("Category", actualCreateNewsResult.getCategory());
        assertEquals("Dr", actualCreateNewsResult.getTitle());
        assertEquals("Text", actualCreateNewsResult.getText());
        assertEquals(1L, actualCreateNewsResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualCreateNewsResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(news).getCategory();
        verify(news, atLeast(1)).getNewsId();
        verify(news).getText();
        verify(news).getTitle();
        verify(news).getCreated();
        verify(news).setCategory(Mockito.<Category>any());
        verify(news).setCreated(Mockito.<LocalDateTime>any());
        verify(news).setNewsId(Mockito.any());
        verify(news).setText(Mockito.any());
        verify(news).setTitle(Mockito.any());
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testUpdateNews() {
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
        Optional<News> ofResult = Optional.of(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());

        News news2 = new News();
        news2.setCategory(category2);
        news2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news2.setNewsId(1L);
        news2.setText("Text");
        news2.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news2);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);
        NewsDto actualUpdateNewsResult = newsServiceImpl.updateNews(1L, new UpdateNewsDto("Dr", "Text"));
        assertEquals("Category", actualUpdateNewsResult.getCategory());
        assertEquals("Dr", actualUpdateNewsResult.getTitle());
        assertEquals("Text", actualUpdateNewsResult.getText());
        assertEquals(1L, actualUpdateNewsResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualUpdateNewsResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
    }

    @Test
    void testUpdateNews2() {
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
        Optional<News> ofResult = Optional.of(news);
        when(newsRepository.save(Mockito.<News>any())).thenThrow(new NewsNotfoundException("An error occurred"));
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);
        assertThrows(NewsNotfoundException.class, () -> newsServiceImpl.updateNews(1L, new UpdateNewsDto("Dr", "Text")));
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
    }

    @Test
    void testUpdateNews3() {
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
        Optional<News> ofResult = Optional.of(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());

        Category category3 = new Category();
        category3.setCategory("Category");
        category3.setCategoryId(1L);
        category3.setNewsList(new ArrayList<>());
        News news2 = mock(News.class);
        when(news2.getCategory()).thenReturn(category3);
        when(news2.getNewsId()).thenReturn(1L);
        when(news2.getText()).thenReturn("Text");
        when(news2.getTitle()).thenReturn("Dr");
        when(news2.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news2).setCategory(Mockito.<Category>any());
        doNothing().when(news2).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news2).setNewsId(Mockito.any());
        doNothing().when(news2).setText(Mockito.any());
        doNothing().when(news2).setTitle(Mockito.any());
        news2.setCategory(category2);
        news2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news2.setNewsId(1L);
        news2.setText("Text");
        news2.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news2);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);
        NewsDto actualUpdateNewsResult = newsServiceImpl.updateNews(1L, new UpdateNewsDto("Dr", "Text"));
        assertEquals("Category", actualUpdateNewsResult.getCategory());
        assertEquals("Dr", actualUpdateNewsResult.getTitle());
        assertEquals("Text", actualUpdateNewsResult.getText());
        assertEquals(1L, actualUpdateNewsResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualUpdateNewsResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
        verify(news2).getCategory();
        verify(news2, atLeast(1)).getNewsId();
        verify(news2).getText();
        verify(news2).getTitle();
        verify(news2).getCreated();
        verify(news2).setCategory(Mockito.<Category>any());
        verify(news2).setCreated(Mockito.<LocalDateTime>any());
        verify(news2).setNewsId(Mockito.any());
        verify(news2).setText(Mockito.any());
        verify(news2).setTitle(Mockito.any());
    }

    @Test
    void testUpdateNews4() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        News news = mock(News.class);
        doNothing().when(news).setCategory(Mockito.<Category>any());
        doNothing().when(news).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news).setNewsId(Mockito.any());
        doNothing().when(news).setText(Mockito.any());
        doNothing().when(news).setTitle(Mockito.any());
        news.setCategory(category);
        news.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news.setNewsId(1L);
        news.setText("Text");
        news.setTitle("Dr");
        Optional<News> ofResult = Optional.of(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());

        Category category3 = new Category();
        category3.setCategory("Category");
        category3.setCategoryId(1L);
        category3.setNewsList(new ArrayList<>());
        News news2 = mock(News.class);
        when(news2.getCategory()).thenReturn(category3);
        when(news2.getNewsId()).thenReturn(1L);
        when(news2.getText()).thenReturn("Text");
        when(news2.getTitle()).thenReturn("Dr");
        when(news2.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news2).setCategory(Mockito.<Category>any());
        doNothing().when(news2).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news2).setNewsId(Mockito.any());
        doNothing().when(news2).setText(Mockito.any());
        doNothing().when(news2).setTitle(Mockito.any());
        news2.setCategory(category2);
        news2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news2.setNewsId(1L);
        news2.setText("Text");
        news2.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news2);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);
        NewsDto actualUpdateNewsResult = newsServiceImpl.updateNews(1L, new UpdateNewsDto("Dr", "Text"));
        assertEquals("Category", actualUpdateNewsResult.getCategory());
        assertEquals("Dr", actualUpdateNewsResult.getTitle());
        assertEquals("Text", actualUpdateNewsResult.getText());
        assertEquals(1L, actualUpdateNewsResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualUpdateNewsResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
        verify(news2).getCategory();
        verify(news2, atLeast(1)).getNewsId();
        verify(news2).getText();
        verify(news2).getTitle();
        verify(news2).getCreated();
        verify(news2).setCategory(Mockito.<Category>any());
        verify(news2).setCreated(Mockito.<LocalDateTime>any());
        verify(news2).setNewsId(Mockito.any());
        verify(news2).setText(Mockito.any());
        verify(news2).setTitle(Mockito.any());
        verify(news).setCategory(Mockito.<Category>any());
        verify(news).setCreated(Mockito.<LocalDateTime>any());
        verify(news).setNewsId(Mockito.any());
        verify(news, atLeast(1)).setText(Mockito.any());
        verify(news, atLeast(1)).setTitle(Mockito.any());
    }

    @Test
    void testUpdateNewsCategory() {
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
        Optional<News> ofResult = Optional.of(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());

        News news2 = new News();
        news2.setCategory(category2);
        news2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news2.setNewsId(1L);
        news2.setText("Text");
        news2.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news2);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);

        Category category3 = new Category();
        category3.setCategory("Category");
        category3.setCategoryId(1L);
        category3.setNewsList(new ArrayList<>());
        when(categoryService.getCategoryByName(Mockito.any())).thenReturn(category3);
        NewsDto actualUpdateNewsCategoryResult = newsServiceImpl.updateNewsCategory(1L, new CategoryDto("Category"));
        assertEquals("Category", actualUpdateNewsCategoryResult.getCategory());
        assertEquals("Dr", actualUpdateNewsCategoryResult.getTitle());
        assertEquals("Text", actualUpdateNewsCategoryResult.getText());
        assertEquals(1L, actualUpdateNewsCategoryResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualUpdateNewsCategoryResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testUpdateNewsCategory2() {
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
        Optional<News> ofResult = Optional.of(news);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);
        when(categoryService.getCategoryByName(Mockito.any()))
                .thenThrow(new NewsNotfoundException("An error occurred"));
        assertThrows(NewsNotfoundException.class,
                () -> newsServiceImpl.updateNewsCategory(1L, new CategoryDto("Category")));
        verify(newsRepository).findById(Mockito.any());
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testUpdateNewsCategory3() {
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
        Optional<News> ofResult = Optional.of(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());

        Category category3 = new Category();
        category3.setCategory("Category");
        category3.setCategoryId(1L);
        category3.setNewsList(new ArrayList<>());
        News news2 = mock(News.class);
        when(news2.getCategory()).thenReturn(category3);
        when(news2.getNewsId()).thenReturn(1L);
        when(news2.getText()).thenReturn("Text");
        when(news2.getTitle()).thenReturn("Dr");
        when(news2.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news2).setCategory(Mockito.<Category>any());
        doNothing().when(news2).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news2).setNewsId(Mockito.any());
        doNothing().when(news2).setText(Mockito.any());
        doNothing().when(news2).setTitle(Mockito.any());
        news2.setCategory(category2);
        news2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news2.setNewsId(1L);
        news2.setText("Text");
        news2.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news2);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);

        Category category4 = new Category();
        category4.setCategory("Category");
        category4.setCategoryId(1L);
        category4.setNewsList(new ArrayList<>());
        when(categoryService.getCategoryByName(Mockito.any())).thenReturn(category4);
        NewsDto actualUpdateNewsCategoryResult = newsServiceImpl.updateNewsCategory(1L, new CategoryDto("Category"));
        assertEquals("Category", actualUpdateNewsCategoryResult.getCategory());
        assertEquals("Dr", actualUpdateNewsCategoryResult.getTitle());
        assertEquals("Text", actualUpdateNewsCategoryResult.getText());
        assertEquals(1L, actualUpdateNewsCategoryResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualUpdateNewsCategoryResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
        verify(news2).getCategory();
        verify(news2, atLeast(1)).getNewsId();
        verify(news2).getText();
        verify(news2).getTitle();
        verify(news2).getCreated();
        verify(news2).setCategory(Mockito.<Category>any());
        verify(news2).setCreated(Mockito.<LocalDateTime>any());
        verify(news2).setNewsId(Mockito.any());
        verify(news2).setText(Mockito.any());
        verify(news2).setTitle(Mockito.any());
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testUpdateNewsCategory4() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());
        News news = mock(News.class);
        doNothing().when(news).setCategory(Mockito.<Category>any());
        doNothing().when(news).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news).setNewsId(Mockito.any());
        doNothing().when(news).setText(Mockito.any());
        doNothing().when(news).setTitle(Mockito.any());
        news.setCategory(category);
        news.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news.setNewsId(1L);
        news.setText("Text");
        news.setTitle("Dr");
        Optional<News> ofResult = Optional.of(news);

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());

        Category category3 = new Category();
        category3.setCategory("Category");
        category3.setCategoryId(1L);
        category3.setNewsList(new ArrayList<>());
        News news2 = mock(News.class);
        when(news2.getCategory()).thenReturn(category3);
        when(news2.getNewsId()).thenReturn(1L);
        when(news2.getText()).thenReturn("Text");
        when(news2.getTitle()).thenReturn("Dr");
        when(news2.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news2).setCategory(Mockito.<Category>any());
        doNothing().when(news2).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news2).setNewsId(Mockito.any());
        doNothing().when(news2).setText(Mockito.any());
        doNothing().when(news2).setTitle(Mockito.any());
        news2.setCategory(category2);
        news2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news2.setNewsId(1L);
        news2.setText("Text");
        news2.setTitle("Dr");
        when(newsRepository.save(Mockito.<News>any())).thenReturn(news2);
        when(newsRepository.findById(Mockito.any())).thenReturn(ofResult);

        Category category4 = new Category();
        category4.setCategory("Category");
        category4.setCategoryId(1L);
        category4.setNewsList(new ArrayList<>());
        when(categoryService.getCategoryByName(Mockito.any())).thenReturn(category4);
        NewsDto actualUpdateNewsCategoryResult = newsServiceImpl.updateNewsCategory(1L, new CategoryDto("Category"));
        assertEquals("Category", actualUpdateNewsCategoryResult.getCategory());
        assertEquals("Dr", actualUpdateNewsCategoryResult.getTitle());
        assertEquals("Text", actualUpdateNewsCategoryResult.getText());
        assertEquals(1L, actualUpdateNewsCategoryResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", actualUpdateNewsCategoryResult.getCreated());
        verify(newsRepository).save(Mockito.<News>any());
        verify(newsRepository).findById(Mockito.any());
        verify(news2).getCategory();
        verify(news2, atLeast(1)).getNewsId();
        verify(news2).getText();
        verify(news2).getTitle();
        verify(news2).getCreated();
        verify(news2).setCategory(Mockito.<Category>any());
        verify(news2).setCreated(Mockito.<LocalDateTime>any());
        verify(news2).setNewsId(Mockito.any());
        verify(news2).setText(Mockito.any());
        verify(news2).setTitle(Mockito.any());
        verify(news, atLeast(1)).setCategory(Mockito.<Category>any());
        verify(news).setCreated(Mockito.<LocalDateTime>any());
        verify(news).setNewsId(Mockito.any());
        verify(news).setText(Mockito.any());
        verify(news).setTitle(Mockito.any());
        verify(categoryService).getCategoryByName(Mockito.any());
    }

    @Test
    void testDeleteNews() {
        when(newsRepository.deleteByNewsId(anyLong())).thenReturn(true);
        assertTrue(newsServiceImpl.deleteNews(1L));
        verify(newsRepository).deleteByNewsId(anyLong());
    }
    @Test
    void testDeleteNews2() {
        when(newsRepository.deleteByNewsId(anyLong())).thenReturn(false);
        assertFalse(newsServiceImpl.deleteNews(1L));
        verify(newsRepository).deleteByNewsId(anyLong());
    }

    @Test
    void testDeleteNews3() {
        when(newsRepository.deleteByNewsId(anyLong())).thenThrow(new NewsNotfoundException("An error occurred"));
        assertThrows(NewsNotfoundException.class, () -> newsServiceImpl.deleteNews(1L));
        verify(newsRepository).deleteByNewsId(anyLong());
    }

    @Test
    void testSearchNews() {
        when(newsRepository.findAll(Mockito.<Specification<News>>any())).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.searchNews(new SearchDto("Category", "Dr", "Text")).isEmpty());
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
    }

    @Test
    void testSearchNews2() {
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

        ArrayList<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.findAll(Mockito.<Specification<News>>any())).thenReturn(newsList);
        List<NewsDto> actualSearchNewsResult = newsServiceImpl.searchNews(new SearchDto("Category", "Dr", "Text"));
        assertEquals(1, actualSearchNewsResult.size());
        NewsDto getResult = actualSearchNewsResult.get(0);
        assertEquals("Category", getResult.getCategory());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Text", getResult.getText());
        assertEquals(1L, getResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", getResult.getCreated());
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
    }

    @Test
    void testSearchNews3() {
        when(newsRepository.findAll(Mockito.<Specification<News>>any())).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.searchNews(new SearchDto(null, "Dr", "Text")).isEmpty());
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
    }

    @Test
    void testSearchNews4() {
        when(newsRepository.findAll(Mockito.<Specification<News>>any())).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.searchNews(new SearchDto("Category", null, "Text")).isEmpty());
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
    }

    @Test
    void testSearchNews5() {
        when(newsRepository.findAll(Mockito.<Specification<News>>any())).thenReturn(new ArrayList<>());
        assertTrue(newsServiceImpl.searchNews(new SearchDto("Category", "Dr", null)).isEmpty());
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
    }


    @Test
    void testSearchNews7() {
        when(newsRepository.findAll(Mockito.<Specification<News>>any()))
                .thenThrow(new NewsNotfoundException("An error occurred"));
        assertThrows(NewsNotfoundException.class,
                () -> newsServiceImpl.searchNews(new SearchDto("Category", "Dr", "Text")));
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
    }

    @Test
    void testSearchNews8() {
        Category category = new Category();
        category.setCategory("Category");
        category.setCategoryId(1L);
        category.setNewsList(new ArrayList<>());

        Category category2 = new Category();
        category2.setCategory("Category");
        category2.setCategoryId(1L);
        category2.setNewsList(new ArrayList<>());
        News news = mock(News.class);
        when(news.getCategory()).thenReturn(category2);
        when(news.getNewsId()).thenReturn(1L);
        when(news.getText()).thenReturn("Text");
        when(news.getTitle()).thenReturn("Dr");
        when(news.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(news).setCategory(Mockito.<Category>any());
        doNothing().when(news).setCreated(Mockito.<LocalDateTime>any());
        doNothing().when(news).setNewsId(Mockito.any());
        doNothing().when(news).setText(Mockito.any());
        doNothing().when(news).setTitle(Mockito.any());
        news.setCategory(category);
        news.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        news.setNewsId(1L);
        news.setText("Text");
        news.setTitle("Dr");

        ArrayList<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.findAll(Mockito.<Specification<News>>any())).thenReturn(newsList);
        List<NewsDto> actualSearchNewsResult = newsServiceImpl.searchNews(new SearchDto("Category", "Dr", "Text"));
        assertEquals(1, actualSearchNewsResult.size());
        NewsDto getResult = actualSearchNewsResult.get(0);
        assertEquals("Category", getResult.getCategory());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Text", getResult.getText());
        assertEquals(1L, getResult.getNewsId());
        assertEquals("1970-01-01 00:00:00", getResult.getCreated());
        verify(newsRepository).findAll(Mockito.<Specification<News>>any());
        verify(news).getCategory();
        verify(news, atLeast(1)).getNewsId();
        verify(news).getText();
        verify(news).getTitle();
        verify(news).getCreated();
        verify(news).setCategory(Mockito.any());
        verify(news).setCreated(Mockito.any());
        verify(news).setNewsId(Mockito.any());
        verify(news).setText(Mockito.any());
        verify(news).setTitle(Mockito.any());
    }
}