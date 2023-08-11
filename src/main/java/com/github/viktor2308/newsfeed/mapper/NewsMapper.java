package com.github.viktor2308.newsfeed.mapper;

import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);
    NewsDto newsToNewsDto(News news);

    default String categoryMapper(Category category) {
        return category != null
                ? category.getCategory()
                : null;
    }
    default String createdMapper(LocalDateTime dateTime) {
        return dateTime != null
                ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                :  null;
    }
}
