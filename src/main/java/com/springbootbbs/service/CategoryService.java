package com.springbootbbs.service;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Boolean delete(Category category) {
        categoryRepository.delete(category);
        return true;
    }

}
