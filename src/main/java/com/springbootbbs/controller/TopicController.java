package com.springbootbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.*;
import com.springbootbbs.exception.PageNotFoundException;
import com.springbootbbs.repository.CategoryRepository;
import com.springbootbbs.repository.PostRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.AttachService;
import com.springbootbbs.service.PostService;
import com.springbootbbs.service.TopicService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class TopicController extends BaseController {

    @Autowired
    TopicService topicService;
    @Autowired
    PostService postService;
    @Autowired
    AttachService attachService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @RequestMapping("/topic_new")
    public String topic_new(String tab, ModelMap m) {
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);
        m.addAttribute("tab", tab);

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
        Optional<Topic> topic = topicRepository.findById(id);

        if (topic.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        int p1 = NumberUtils.toInt(p, 1);
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(p1-1, 10, Sort.by(order));

        Page<Post> page = postRepository.findAllByTopicIdAndDeleted(id, pageable, false);

        m.addAttribute("topic", topic.get());
        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "topic/topic_show";
    }

    @RequestMapping(value = "/topic/{id}/edit", method = RequestMethod.GET)
    public String topic_edit(@PathVariable Long id, ModelMap m) {
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();
        Optional<Topic> topic = topicRepository.findById(id);

        if (topic.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        Post post = postRepository.findTopByIsFirstAndTopic(true, topic.get());

        m.addAttribute("topic", topic.get());
        m.addAttribute("post", post);
        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "topic/topic_edit";
    }

    @RequestMapping(value = "/topic/{id}/edit_post", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String topic_edit_post(@PathVariable Long id, Long category_id, String title, String content) throws JsonProcessingException {
        Optional<Category> category = categoryRepository.findById(category_id);

        if (category.isEmpty()) {
            throw new PageNotFoundException("分类不存在");
        }

        Optional<Topic> topicOptional = topicRepository.findById(id);
        Topic topic;
        if (topicOptional.isEmpty()) {
            throw new PageNotFoundException("铁子不存在");
        } else {
            topic = topicOptional.get();
        }

        Post post = postRepository.findTopByIsFirstAndTopic(true, topic);
        topic.setCategory(category.get());
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
        Optional<Topic> topic = topicRepository.findById(id);

        if (topic.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        Post post = new Post();
        post.setContent(content);
        post.setIsFirst(false);
        post.setTopic(topic.get());
        post.setUser(getUser());
        postService.save(post);

        return "redirect:/topic/"+topic.get().getId();
    }

    @RequestMapping(value = "/topic/{id}/remove", method = RequestMethod.POST)
    public String topic_remove(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);

        if (topic.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        topicService.soft_delete(topic.get());

        return "redirect:/";
    }

    @RequestMapping(value = "/post/{id}/remove", method = RequestMethod.POST)
    public String post_remove(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            throw new PageNotFoundException("回帖不存在");
        }

        Long topic_id = post.get().getTopic().getId();
        postService.soft_delete(post.get());

        return "redirect:/topic/" + topic_id;
    }

    @RequestMapping(value = "/topic_upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String topic_upload(@RequestParam("file")MultipartFile file) throws JsonProcessingException {
        User user = getUser();
        HashMap<String, Object> map = new HashMap<>();

        if (file.isEmpty()) {
            map.put("success", "0");
            map.put("message", "上传失败，请选择文件");
        } else if (file.getSize() > 1024 * 1024 * 10) {
            map.put("success", "0");
            map.put("message", "最大不超过10M");
        } else {
            Attach attach = new Attach();
            attach.setFile(file);
            attach.setOwnerType(Attach.OwnerType.TOPIC_FILE);
            attach.setOwnerId(0L);
            attach.setUser(user);
            Attach attach_result = attachService.save(attach);
            if (attach_result == null) {
                map.put("success", "0");
                map.put("message", "上传文件失败");
            } else {
                map.put("success", "1");
                map.put("message", "上传成功");
                map.put("isImage", Boolean.valueOf(attach.getContentType().startsWith("image/")));
                map.put("fileName", attach.getName());
                map.put("url", "/attach/show/" + String.valueOf(attach_result.getId()));
            }
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

}
