package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Boolean existsByName(String name);

    Boolean existsByTab(String tab);

    Boolean existsByNameAndIdNot(String name, Long id);

    Boolean existsByTabAndIdNot(String tab, Long id);

    Category findTopByOrderBySortDesc();

    Iterable<Category> findByOrderBySortAscIdAsc();

}
