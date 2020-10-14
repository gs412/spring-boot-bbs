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

		topic.setUpdatedAt(date);

		return topicRepository.save(topic);
	}

    public Boolean soft_delete(Topic topic) {
        topic.setDeleted(true);
        topicRepository.save(topic);
        return true;
    }

    public Boolean delete(Topic topic) {
        topicRepository.delete(topic);
        return true;
    }
}
