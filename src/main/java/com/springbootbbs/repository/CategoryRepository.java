package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
