package com.springbootbbs.repository;

import com.springbootbbs.entiry.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
