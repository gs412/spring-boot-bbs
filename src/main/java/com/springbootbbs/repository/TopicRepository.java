package com.springbootbbs.repository;

import com.springbootbbs.entiry.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {

    Page<Topic> findAllByDeletedOrderByIdDesc(Pageable page, Boolean deleted);

    Page<Topic> findAllByCategoryTabAndDeletedOrderByIdDesc(Pageable page, String categoryTab, Boolean deleted);

    Page<Topic> findAllByTitleLikeAndDeletedOrderByIdDesc(Pageable page, String title, Boolean deleted);

}
