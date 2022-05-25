package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Boolean existsByNameCn(String name);

    Boolean existsByNameEn(String name);

    Boolean existsByTab(String tab);

    Boolean existsByNameCnAndIdNot(String name, Long id);

    Boolean existsByNameEnAndIdNot(String name, Long id);

    Boolean existsByTabAndIdNot(String tab, Long id);

    Category findTopByOrderBySortDesc();

    Iterable<Category> findAllByOrderBySortAscIdAsc();

    Iterable<Category> findAllByIdNotOrderBySortAscIdAsc(Long id);

}
