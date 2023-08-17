package com.github.viktor2308.newsfeed.controller;

import com.github.viktor2308.newsfeed.dto.CategoryDto;
import com.github.viktor2308.newsfeed.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public List<CategoryDto> getAllCategory() {
        log.info("Request get all category");
        return categoryService.findAllCategory();
    }

    @GetMapping("/category/{id}")
    public CategoryDto getCategoryById(@PathVariable(value ="id") long id) {
        log.info("Request get category by id: {}", id);
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/category")
    public CategoryDto addCategory(@RequestBody CategoryDto newCategoryDto) {
        log.info("Request create category: {}",  newCategoryDto.getCategory());
        return categoryService.addCategory(newCategoryDto);
    }

    @PutMapping("/category/{id}")
    public CategoryDto updateCategory(@PathVariable(value ="id") long id, @RequestBody CategoryDto categoryDto) {
        log.info("Request update category with id: {}", id);
        return categoryService.updateCategory(id, categoryDto);

    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value ="id") long id) {
        log.info("Request delete category with id: {}", id);
        return categoryService.deleteNews(id)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

