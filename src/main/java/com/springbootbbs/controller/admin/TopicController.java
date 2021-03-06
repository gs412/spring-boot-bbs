package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.AjaxResult;
import com.springbootbbs.libs.helpers.ShowDate;
import com.springbootbbs.repository.TopicRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller("admin.TopicController")
@RequestMapping("/admin/topic")
public class TopicController extends BaseController {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicService topicService;

    @RequestMapping("/topics")
    public String topics(String p, ModelMap m) {
        int p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");

        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<Topic> page = topicRepository.findAllByDeletedOrderByIdDesc(pageable, false);

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());
        m.addAttribute("show_date", new ShowDate());

        return "admin/topic/topics";
    }

    @PostMapping("/topic/{id}/remove")
    public String topicRemove(@PathVariable Long id, final HttpServletRequest request) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        topicOptional.ifPresent(topic -> topicService.soft_delete(topic));

        return "redirect:" + request.getHeader("referer");
    }

}
