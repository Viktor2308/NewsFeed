package com.github.viktor2308.newsfeed.service;

import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;

import java.util.List;

public interface NewsService {

    List<NewsDto> getAllNews();


    NewsDto createNews(CreateNewsDto newsDto);
}
