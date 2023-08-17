package com.github.viktor2308.newsfeed.repository;

import com.github.viktor2308.newsfeed.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategory(String category);
    boolean existsCategoriesByCategory(String category);

    boolean deleteByCategoryId(long id);

}
