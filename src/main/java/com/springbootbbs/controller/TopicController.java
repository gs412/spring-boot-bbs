package com.springbootbbs.controller;

import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.PostRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.PostService;
import com.springbootbbs.service.TopicService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TopicController extends BaseController {

	@Autowired
	TopicService topicService;
	@Autowired
	PostService postService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TopicRepository topicRepository;
	@Autowired
	PostRepository postRepository;


	@RequestMapping("/topic_new")
	public String topic_new(ModelMap m) {
		m.addAttribute("user", getUser());

		return "topic/topic_new";
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
		post.setIsFirst(true);
		post.setTopic(topic);
		post.setUser(user);
		postService.save(post);

		return "redirect:/";
	}

	@RequestMapping(value = "/topic/{id}")
	public String topic_show(@PathVariable Long id, String p, ModelMap m) {
		Topic topic = topicRepository.findById(id).get();

		Integer p1 = NumberUtils.toInt(p, 1);
		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(p1-1, 10, Sort.by(order));

		Page<Post> page = postRepository.findAllByTopicId(id, pageable);

		m.addAttribute("topic", topic);
		m.addAttribute("page", page);
		m.addAttribute("user", getUser());

		return "topic/topic_show";
	}

	@RequestMapping(value = "/topic/{id}/reply", method = RequestMethod.POST)
	public String topic_reply(@PathVariable Long id, String content) {
		Topic topic = topicRepository.findById(id).get();

		Post post = new Post();
		post.setContent(content);
		post.setIsFirst(false);
		post.setTopic(topic);
		post.setUser(getUser());
		postService.save(post);

		return "redirect:/topic/"+topic.getId();
	}

}
