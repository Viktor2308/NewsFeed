package com.github.viktor2308.newsfeed.service.impl;

import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.mapper.NewsMapper;
import com.github.viktor2308.newsfeed.repository.NewsRepository;
import com.github.viktor2308.newsfeed.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    @Override
    public List<NewsDto> getAllNews() {
        return repository.findAll().stream()
                .map(NewsMapper.INSTANCE::newsToNewsDto)
                .toList();
    }
}
