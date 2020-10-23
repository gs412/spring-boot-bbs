package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.springbootbbs.entiry.Topic;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Boolean existsByName(String name);

    Boolean existsByTab(String tab);

    Boolean existsByNameAndIdNot(String name, Long id);

    Boolean existsByTabAndIdNot(String tab, Long id);

    Category findTopByOrderBySortDesc();

    Iterable<Category> findAllByOrderBySortAscIdAsc();

    Iterable<Category> findAllByIdNotOrderBySortAscIdAsc(Long id);

    @Transactional
    @Modifying
    @Query("update Topic set category=:targetCategory where category=:sourceCategory")
    void updateCategoryByCategory(@Param("targetCategory") Category targetCategory, @Param("sourceCategory") Category sourceCategory);

}
