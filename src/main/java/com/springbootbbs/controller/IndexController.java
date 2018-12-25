package com.springbootbbs.controller;

import com.springbootbbs.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	TopicRepository topicRepository;

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


		return "redirect:/";
	}

}
