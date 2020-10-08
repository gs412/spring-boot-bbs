package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Boolean existsByName(String name);

    Boolean existsByTab(String tab);

    Category findTopByOrderBySortDesc();

    Iterable<Category> findByOrderBySortAscIdAsc();

}
