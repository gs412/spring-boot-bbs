package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.TopicController")
@RequestMapping("/admin/topic")
public class TopicController extends BaseController {

    @Autowired
    TopicRepository topicRepository;

    @RequestMapping("/topics")
    public String topics(ModelMap m) {
        User user = this.getUser();

        m.addAttribute("user", user);

        return "admin/topic/topics";
    }

}
