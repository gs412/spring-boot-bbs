package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.libs.Utils;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.PostRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.service.AttachService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller("admin.TopicController")
@RequestMapping("/admin/topic")
public class TopicController extends BaseController {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicService topicService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    AttachService attachService;

    @RequestMapping("/topics")
    public String topics(String p, ModelMap m) {
        int p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");

        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<Topic> page = topicRepository.findAllByDeletedOrderByIdDesc(pageable, false);

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "admin/topic/topics";
    }

    @PostMapping("/topic/{id}/remove")
    public String topicRemove(@PathVariable Long id, final HttpServletRequest request) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        topicOptional.ifPresent(topic -> topicService.soft_delete(topic));

        return Utils.referer(request);
    }

    @GetMapping("/topics-trash")
    public String topics_trash(String p, ModelMap m) {
        int p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");

        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<Topic> page = topicRepository.findAllByDeletedOrderByIdDesc(pageable, true);

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "admin/topic/topics-trash";
    }

    @PostMapping("/topic/{id}/resume")
    public String topicResume(@PathVariable Long id, final HttpServletRequest request) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        topicOptional.ifPresent(topic -> topicService.resume(topic));

        return Utils.referer(request);
    }

    @PostMapping("/topic/{id}/remove-hard")
    public String topicRemoveHard(@PathVariable Long id, final HttpServletRequest request) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isPresent()) {
            Topic topic = topicOptional.get();

            List<Post> postList = postRepository.findAllByTopic(topic);
            postList.forEach(post -> {
                List<Attach> attachList = attachRepository.findAllByOwnerIdAndOwnerType(post.getId(), Attach.OwnerType.POST_ATTACH);
                attachList.forEach(attachService::delete);

                postService.delete(post);
            });

            topicService.delete(topic);
        }

        return Utils.referer(request);
    }

    @PostMapping("/clean-trash")
    public String cleanTrash(final HttpServletRequest request) {
        List<Topic> topicList = topicRepository.findAllByDeleted(true);
        topicList.forEach(topic -> {
            List<Post> postList = postRepository.findAllByTopic(topic);
            postList.forEach(post -> {
                List<Attach> attachList = attachRepository.findAllByOwnerIdAndOwnerType(post.getId(), Attach.OwnerType.POST_ATTACH);
                attachList.forEach(attachService::delete);

                postService.delete(post);
            });

            topicService.delete(topic);
        });

        return Utils.referer(request);
    }

}
