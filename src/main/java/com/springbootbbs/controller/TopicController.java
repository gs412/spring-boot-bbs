package com.springbootbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.CategoryRepository;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

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
    @Autowired
    CategoryRepository categoryRepository;


    @RequestMapping("/topic_new")
    public String topic_new(ModelMap m) {
        Iterable<Category> categories = categoryRepository.findByOrderBySortAscIdAsc();

        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "topic/topic_new";
    }

    @RequestMapping("/topic_save")
    public String topic_save(String title, String content, Long category_id) {
        User user = getUser();
        Category category = categoryRepository.findById(category_id).get();

        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setReplies(0);
        topic.setUser(user);
        topic.setCategory(category);
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

    @RequestMapping(value = "/topic/{id}/edit", method = RequestMethod.GET)
    public String topic_edit(@PathVariable Long id, ModelMap m) {
        Iterable<Category> categories = categoryRepository.findByOrderBySortAscIdAsc();
        Topic topic = topicRepository.findById(id).get();
        Post post = postRepository.findTopByIsFirstAndTopic(true, topic);

        m.addAttribute("topic", topic);
        m.addAttribute("post", post);
        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "topic/topic_edit";
    }

    @RequestMapping(value = "/topic/{id}/edit_post", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String topic_edit_post(@PathVariable Long id, Long category_id, String title, String content) throws JsonProcessingException {
        Category category = categoryRepository.findById(category_id).get();
        Topic topic = topicRepository.findById(id).get();
        Post post = postRepository.findTopByIsFirstAndTopic(true, topic);
        topic.setCategory(category);
        topic.setTitle(title);
        post.setContent(content);
        topicService.save(topic);
        postService.save(post);

        HashMap<String, String> map = new HashMap<>();
        map.put("success", "1");
        map.put("message", "ok");

        String json = new ObjectMapper().writeValueAsString(map);

        return json;
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

    @RequestMapping(value = "/topic/{id}/remove", method = RequestMethod.POST)
    public String topic_remove(@PathVariable Long id) {

        return "redirect:/";
    }

    @RequestMapping(value = "/post/{id}/remove", method = RequestMethod.POST)
    public String post_remove(@PathVariable Long id) {

        return "redirect:/";
    }

}
