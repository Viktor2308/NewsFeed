package com.github.viktor2308.newsfeed.controller;

import com.github.viktor2308.newsfeed.dto.*;
import com.github.viktor2308.newsfeed.service.NewsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("/news/{id}")
    public NewsDto updateNews(@PathVariable long id, @RequestBody @Valid UpdateNewsDto updateNewsDto){
        log.info("Request update news with id: {}", id);
        return newsService.updateNews(id, updateNewsDto);
    }
    @PatchMapping("/news/{id}/category")
    public NewsDto updateNewsCategory(@PathVariable long id, @RequestBody @Valid CategoryDto category){
        log.info("Request update news category with id: {}, to category: {}", id, category);
        return newsService.updateNewsCategory(id, category);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable @NotBlank long id){
        log.info("Request delete news with id: {}", id);
        return newsService.deleteNews(id)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/news/search")
    public List<NewsDto> searchNews(SearchDto searchDto) {
        log.info("Request for search news");
        return newsService.searchNews(searchDto);
    }

}
