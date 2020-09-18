package com.springbootbbs.service;

import com.springbootbbs.entiry.Post;
import com.springbootbbs.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

	public Post save(Post post) {
		Date date = new Date();

		post.setUpdatedAt(date);

		return postRepository.save(post);
	}

}
