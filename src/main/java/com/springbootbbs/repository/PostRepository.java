package com.springbootbbs.repository;

import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	Page<Post> findAllByTopicId(Long id, Pageable pageable);

    Post findByIsFirstAndTopic(Boolean isFirst, Topic topic);

}
