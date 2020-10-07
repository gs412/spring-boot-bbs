package com.springbootbbs.controller;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.libs.helpers.ShowDate;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController extends BaseController {

	@Autowired
	TopicRepository topicRepository;

	@RequestMapping("/")
	public String index(String p, ModelMap m) {
		Integer p1 = NumberUtils.toInt(p, 1);
		Order order = new Order(Direction.DESC, "id");

		Pageable pageable = PageRequest.of(p1-1, 20, Sort.by(order));

		Page<Topic> page = topicRepository.findAll(pageable);

		m.addAttribute("page", page);
		m.addAttribute("user", getUser());
        m.addAttribute("show_date", new ShowDate());

		return "index";
	}

}
