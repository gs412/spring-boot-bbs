package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long>, TopicRepositoryCustom {

    Page<Topic> findAllByDeletedOrderByIdDesc(Pageable page, Boolean deleted);

    Page<Topic> findAllByDeletedOrderByStickDescIdDesc(Pageable page, Boolean deleted);

    Page<Topic> findAllByCategoryTabAndDeletedOrderByStickDescIdDesc(Pageable page, String categoryTab, Boolean deleted);

    Page<Topic> findAllByTitleLikeAndDeletedOrderByIdDesc(Pageable page, String title, Boolean deleted);

    int countByCategoryIdAndDeleted(Long categoryId, Boolean deleted);

    @Transactional
    @Modifying
    @Query("update Topic set category=:targetCategory where category=:sourceCategory")
    void moveCategoryToCategory(Category sourceCategory, Category targetCategory);

}
