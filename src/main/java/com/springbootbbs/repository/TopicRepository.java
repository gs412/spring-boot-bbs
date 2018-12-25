package com.springbootbbs.repository;

import com.springbootbbs.entiry.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
}
