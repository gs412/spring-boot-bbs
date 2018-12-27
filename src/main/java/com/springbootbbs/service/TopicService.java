package com.springbootbbs.service;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TopicService {

	@Autowired
	TopicRepository topicRepository;

	public Topic save(Topic topic) {
		Date date = new Date();

		topic.setCreatedAt(date);
		topic.setUpdatedAt(date);

		return topicRepository.save(topic);
	}

}
