package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.TopicRepository;
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

@Controller("admin.TopicController")
@RequestMapping("/admin/topic")
public class TopicController extends BaseController {

    @Autowired
    TopicRepository topicRepository;

    @RequestMapping("/topics")
    public String topics(String p, ModelMap m) {
        Integer p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");

        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<Topic> page = topicRepository.findAll(pageable);

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "admin/topic/topics";
    }

}
