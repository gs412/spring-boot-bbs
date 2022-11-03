package com.springbootbbs.repository;

import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {


	Page<Post> findAllByTopicIdAndDeleted(Long id, Pageable pageable, Boolean deleted);

    List<Post> findAllByUser(User user);

    Post findTopByIsFirstAndTopic(Boolean isFirst, Topic topic);

}
