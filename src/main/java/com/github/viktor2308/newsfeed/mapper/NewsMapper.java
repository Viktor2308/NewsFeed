package com.github.viktor2308.newsfeed.mapper;

import com.github.viktor2308.newsfeed.dto.NewsDto;
import com.github.viktor2308.newsfeed.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsDto newsToNewsDto(News news);

    News newsDtoToNews(NewsDto newsDto);
}
