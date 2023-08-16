package com.github.viktor2308.newsfeed.repository;

import com.github.viktor2308.newsfeed.entity.Category;
import com.github.viktor2308.newsfeed.entity.News;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class NewsSpecification {

    public static Specification<News> likeSearchedTitle(String searchedTitle) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + searchedTitle + "%"));
    }

    public static Specification<News> likeSearchedText(String searchedText) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("text"), "%" + searchedText + "%"));
    }

    public static Specification<News> likeSearchedCategory(String searchedCategory) {
        return (root, query, criteriaBuilder) -> {
            Join<Category, News> categoryNewsJoin = root.join("category");
            return criteriaBuilder.equal(categoryNewsJoin.get("category"), searchedCategory);
        };
    }
}
