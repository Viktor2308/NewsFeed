package com.github.viktor2308.newsfeed.service.impl;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.dto.UpdateNewsDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import com.github.viktor2308.newsfeed.exception.NewsNotfoundException;
import com.github.viktor2308.newsfeed.mapper.NewsMapper;
import com.github.viktor2308.newsfeed.repository.NewsRepository;
import com.github.viktor2308.newsfeed.service.CategoryService;
import com.github.viktor2308.newsfeed.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final CategoryService categoryService;

    @Override
    public List<NewsDto> getAllNews() {
        return repository.findAll().stream()
                .map(NewsMapper.INSTANCE::newsToNewsDto)
                .toList();
    }

    @Override
    public NewsDto createNews(CreateNewsDto newsDto) {

        News news = repository.save(News.builder()
                .title(newsDto.getTitle())
                .text(newsDto.getText())
                .category(categoryService.getCategoryByName(newsDto.getCategory()))
                .created(LocalDateTime.now())
                .build());
        return NewsMapper.INSTANCE.newsToNewsDto(news);
    }

    @Override
    public NewsDto updateNews(long id, UpdateNewsDto updateNewsDto) {
        News news = repository.findById(id).orElseThrow(() ->
                new NewsNotfoundException("News with id " + id + " not found."));
        NewsMapper.INSTANCE.patch(news,updateNewsDto);
        return NewsMapper.INSTANCE.newsToNewsDto(repository.save(news));
    }

    @Override
    public NewsDto updateNewsCategory(long id, CategoryDto newCategory) {
        News news = repository.findById(id).orElseThrow(() ->
                new NewsNotfoundException("News with id " + id + " not found."));
        Category category = categoryService.getCategoryByName(newCategory.getCategory());
        news.setCategory(category);
        return NewsMapper.INSTANCE.newsToNewsDto(repository.save(news));
    }
}
