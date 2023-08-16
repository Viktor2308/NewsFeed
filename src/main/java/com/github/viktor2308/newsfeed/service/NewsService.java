package com.github.viktor2308.newsfeed.service;

import com.github.viktor2308.newsfeed.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {

    List<NewsDto> getAllNews();


    NewsDto createNews(CreateNewsDto newsDto);

    NewsDto updateNews(long id, UpdateNewsDto updateNewsDto);

    NewsDto updateNewsCategory(long id, CategoryDto category);

    boolean deleteNews(long id);

    List<NewsDto> searchNews(SearchDto searchDto);
}
