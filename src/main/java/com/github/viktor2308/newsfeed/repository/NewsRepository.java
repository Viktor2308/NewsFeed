package com.github.viktor2308.newsfeed.repository;

import com.github.viktor2308.newsfeed.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    boolean deleteByNewsId(long id);

    List<News> findAllByOrderByNewsIdDesc();

    List<News> findAllByCategory_Category(String category);

    List<News> findAllByTitle(String title);

    List<News> findAllByText(String text);

}
