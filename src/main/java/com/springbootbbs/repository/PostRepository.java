package com.springbootbbs.repository;

import com.springbootbbs.entiry.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
