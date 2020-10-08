package com.springbootbbs.repository;

import com.springbootbbs.entiry.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {

    Page<Topic> findAllByCategoryTabOrderByIdDesc(Pageable page, String categoryTab);

    Page<Topic> findAllByTitleLikeOrderByIdDesc(Pageable page, String title);

}
