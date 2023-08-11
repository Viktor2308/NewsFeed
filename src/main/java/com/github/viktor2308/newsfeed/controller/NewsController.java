package com.github.viktor2308.newsfeed.controller;

import com.github.viktor2308.newsfeed.dto.CreateNewsDto;
import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.service.NewsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Slf4j
@AllArgsConstructor
public class NewsController {

    private NewsService newsService;

    @GetMapping("/news")
    public List<NewsDto> getAllNews() {
        log.info("Request for all news");
        return newsService.getAllNews();
    }


    @PostMapping("/news")
    public NewsDto createNews(@RequestBody @Valid CreateNewsDto newsDto) {
        log.info("Request create new News: {},{}", newsDto.getTitle(), newsDto.getText());
        return newsService.createNews(newsDto);
    }


}
