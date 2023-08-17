package com.github.viktor2308.newsfeed.service.impl;

import com.github.viktor2308.newsfeed.dto.*;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import com.github.viktor2308.newsfeed.exception.NewsNotfoundException;
import com.github.viktor2308.newsfeed.mapper.NewsMapper;
import com.github.viktor2308.newsfeed.repository.NewsRepository;
import com.github.viktor2308.newsfeed.repository.NewsSpecification;
import com.github.viktor2308.newsfeed.service.CategoryService;
import com.github.viktor2308.newsfeed.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
        return repository.findAllByOrderByNewsIdDesc().stream()
                .map(NewsMapper.INSTANCE::toNewsDto)
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
        return NewsMapper.INSTANCE.toNewsDto(news);
    }

    @Override
    public NewsDto updateNews(long id, UpdateNewsDto updateNewsDto) {
        News news = repository.findById(id).orElseThrow(() ->
                new NewsNotfoundException("News with id " + id + " not found."));
        NewsMapper.INSTANCE.patch(news, updateNewsDto);
        return NewsMapper.INSTANCE.toNewsDto(repository.save(news));
    }

    @Override
    public NewsDto updateNewsCategory(long id, CategoryDto newCategory) {
        News news = repository.findById(id).orElseThrow(() ->
                new NewsNotfoundException("News with id " + id + " not found."));
        Category category = categoryService.getCategoryByName(newCategory.getCategory());
        news.setCategory(category);
        return NewsMapper.INSTANCE.toNewsDto(repository.save(news));
    }

    @Override
    public boolean deleteNews(long id) {
        return repository.deleteByNewsId(id);
    }

    @Override
    public List<NewsDto> searchNews(SearchDto searchDto) {
        Specification<News> spec = Specification.where(null);
        if (searchDto.getTitle() != null) {
            spec = spec.and(NewsSpecification.likeSearchedTitle(searchDto.getTitle()));
        }
        if (searchDto.getText() != null) {
            spec = spec.and(NewsSpecification.likeSearchedText(searchDto.getText()));
        }
        if (searchDto.getCategory() != null) {
            spec = spec.and(NewsSpecification.likeSearchedCategory(searchDto.getCategory()));
        }
        List<News> newsList = repository.findAll(spec);

        return newsList.stream()
                .map(NewsMapper.INSTANCE::toNewsDto)
                .toList();
    }
}
