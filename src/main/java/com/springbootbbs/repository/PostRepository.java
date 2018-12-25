package com.springbootbbs.repository;

import com.springbootbbs.entiry.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	public Page<Post> findAllByTopicId(Long id, Pageable pageable);

}
