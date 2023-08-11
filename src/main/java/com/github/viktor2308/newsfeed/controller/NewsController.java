package com.github.viktor2308.newsfeed.controller;

import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Slf4j
@RequiredArgsConstructor
public class NewsController {

    private NewsService newsService;

    @GetMapping("/news/")
    public List<NewsDto> getAllNews() {
        log.info("Request for all news");
        return newsService.getAllNews();
    }
}
