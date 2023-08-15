package com.github.viktor2308.newsfeed.service;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.dto.UpdateNewsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {

    List<NewsDto> getAllNews();


    NewsDto createNews(CreateNewsDto newsDto);

    NewsDto updateNews(long id, UpdateNewsDto updateNewsDto);

    NewsDto updateNewsCategory(long id, CategoryDto category);

    boolean deleteNews(long id);
}
