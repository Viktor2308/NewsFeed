package com.github.viktor2308.newsfeed.repository;

import com.github.viktor2308.newsfeed.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}