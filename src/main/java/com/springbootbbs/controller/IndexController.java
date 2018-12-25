package com.springbootbbs.controller;

import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.PostRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.PostService;
import com.springbootbbs.service.TopicService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class IndexController extends BaseController {

	@Autowired
	TopicService topicService;
	@Autowired
	PostService postService;
	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/topic_new")
	public String topic_new() {
		return "topic_new";
	}

	@RequestMapping("/topic_save")
	public String topic_save(String title, String content) {
		User user = getUser();

		Topic topic = new Topic();
		topic.setTitle(title);
		topic.setReplies(0);
		topic.setUser(user);
		topicService.save(topic);

		Post post = new Post();
		post.setContent(content);
		post.setTopic(topic);
		post.setUser(user);
		postService.save(post);

		return "redirect:/";
	}

}
