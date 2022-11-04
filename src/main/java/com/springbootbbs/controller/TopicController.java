package com.springbootbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.*;
import com.springbootbbs.exception.PageNotFoundException;
import com.springbootbbs.repository.*;
import com.springbootbbs.service.AttachService;
import com.springbootbbs.service.PostService;
import com.springbootbbs.service.TopicService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    AttachRepository attachRepository;


    @RequiresRoles("user")
    @RequestMapping("/topic_new")
    public String topic_new(String tab, ModelMap m) {
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);
        m.addAttribute("tab", tab);

        return "topic/topic_new";
    }

    @RequiresRoles("user")
    @RequestMapping("/topic_save")
    public String topic_save(String title, String content, Long category_id) {
        User user = getUser();

        Optional<Category> categoryOptional = categoryRepository.findById(category_id);
        if (categoryOptional.isEmpty()) {
            throw new PageNotFoundException("分类不存在");
        }

        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setReplies(0);
        topic.setUser(user);
        topic.setCategory(categoryOptional.get());
        topicService.save(topic);

        Post post = new Post();
        post.setContent(content);
        post.setIsFirst(true);
        post.setTopic(topic);
        post.setUser(user);
        postService.save(post);

        manageAttachForPostCreate(post);

        return "redirect:/";
    }

    @RequestMapping(value = "/topic/{id}")
    public String topic_show(@PathVariable Long id, String p, ModelMap m) {
        Optional<Topic> oTopic = topicRepository.findById(id);

        if (oTopic.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        Topic topic = oTopic.get();

        if (topic.getUser().getBanned()) {
            throw new PageNotFoundException("作者已被封禁");
        }

        int p1 = NumberUtils.toInt(p, 1);
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(p1-1, 10, Sort.by(order));

        Page<Post> page = postRepository.findAllByTopicIdAndDeleted(id, pageable, false);

        m.addAttribute("title", topic.getTitle() + " - Spring Boot BBS");
        m.addAttribute("topic", topic);
        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "topic/topic-show";
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/topic/{id}/edit", method = RequestMethod.GET)
    public String topic_edit(@PathVariable Long id, ModelMap m) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        Post post = postRepository.findTopByIsFirstAndTopic(true, topicOptional.get());
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

        m.addAttribute("topic", topicOptional.get());
        m.addAttribute("post", post);
        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "topic/topic_edit";
    }

    @RequiresRoles("user")
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
            throw new PageNotFoundException("帖子不存在");
        } else {
            topic = topicOptional.get();
        }

        Post post = postRepository.findTopByIsFirstAndTopic(true, topic);
        topic.setCategory(category.get());
        topic.setTitle(title);
        post.setContent(content);
        topicService.save(topic);
        postService.save(post);

        manageAttachForPostUpdate(post);

        HashMap<String, String> map = new HashMap<>();
        map.put("success", "1");
        map.put("message", "ok");

        String json = new ObjectMapper().writeValueAsString(map);

        return json;
    }

    @RequiresRoles("user")
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

        manageAttachForPostCreate(post);

        return "redirect:/topic/"+topic.get().getId();
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/topic/{id}/remove", method = RequestMethod.POST)
    public String topic_remove(@PathVariable Long id) {
        Optional<Topic> oTopic = topicRepository.findById(id);

        if (oTopic.isEmpty()) {
            throw new PageNotFoundException("帖子不存在");
        }

        topicService.soft_delete(oTopic.get());

        return "redirect:/";
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/post/{id}/edit")
    public String post_edit(@PathVariable Long id, ModelMap m) {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty()) {
            throw new PageNotFoundException("回帖不存在");
        }

        Post post = oPost.get();

        m.addAttribute("post", post);

        return "post/post_edit";
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/post/{id}/edit_post", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String post_edit_post(@PathVariable Long id, String content) throws JsonProcessingException {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty()) {
            throw new PageNotFoundException("回帖不存在");
        }

        Post post = oPost.get();
        post.setContent(content);
        System.out.println("更新成功post");
        postService.save(post);

        manageAttachForPostUpdate(post);

        HashMap<String, String> map = new HashMap<>();
        map.put("success", "1");
        map.put("message", "ok");

        String json = new ObjectMapper().writeValueAsString(map);

        return json;
    }

    @RequiresRoles("user")
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

    @RequiresRoles("user")
    @RequestMapping(value = "/topic_upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String topic_upload(@RequestParam("file")MultipartFile file) throws JsonProcessingException {
        User user = getUser();
        HashMap<String, Object> map = new HashMap<>();

        if (file.isEmpty()) {
            map.put("success", "0");
            map.put("message", "上传失败，请选择文件");
        } else if (file.getSize() > 1024 * 1024 * 30) {
            map.put("success", "0");
            map.put("message", "最大不超过30M");
        } else {
            Attach attach = new Attach();
            attach.setFile(file);
            attach.setOwnerType(Attach.OwnerType.POST_ATTACH);
            attach.setOwnerId(0L);
            attach.setUser(user);
            if (attachService.save(attach) == null) {
                map.put("success", "0");
                map.put("message", "上传文件失败");
            } else {
                map.put("success", "1");
                map.put("message", "上传成功");
                map.put("isImage", attach.getContentType().startsWith("image/"));
                map.put("fileName", attach.getName());
                map.put("url", "/attach/show/" + attach.getId());
            }
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/topic_stick", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String stick(Long id, String action) throws JsonProcessingException {
        System.out.println(id);
        System.out.println(action);
        Topic topic = topicRepository.findById(id).get();
        topic.setStick(action.equals("on"));
        topicService.save(topic);

        Map<String, Object> map = new HashMap<>();
        map.put("success", 1);

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

    private List<Long> pickAidsFromContent(String content) {
        List<Long> adis = new ArrayList<>();

        System.out.println(content);
        Matcher m = Pattern.compile("\\]\\(/attach/show/(\\d+)\\)").matcher(content);
        while (m.find()) {
            adis.add(Long.valueOf(m.group(1)));
        }

        return adis;
    }

    private void manageAttachForPostCreate(Post post) {
        List<Long> aids = pickAidsFromContent(post.getContent());
        for (Long aid : aids) {
            Optional<Attach> oAttach = attachRepository.findById(aid);
            if (oAttach.isPresent()) {
                Attach attach = oAttach.get();
                if (attach.getOwnerId() == 0L) {
                    attach.setOwnerId(post.getId());
                    attach.setOwnerType(Attach.OwnerType.POST_ATTACH);
                    attach.setUser(post.getUser());
                    attachService.save(attach);
                }
            }
        }

        // 把无主(ownerId=0)的附件都查出来删除
        List<Attach> noOwnerAttaches = attachRepository.findAllByOwnerIdAndOwnerTypeAndUser(0L, Attach.OwnerType.POST_ATTACH, post.getUser());
        for (Attach attach : noOwnerAttaches) {
            attachService.delete(attach);
        }
    }

    private void manageAttachForPostUpdate(Post post) {
        // 先把该post的所有attach设为无主
        attachRepository.updateOwnerIdByOwnerIdAndOwnerTypeAndUser(0L, post.getId(), Attach.OwnerType.POST_ATTACH, post.getUser());

        // 把aid在内容中出现的设置为有主(本post)
        List<Long> aids = pickAidsFromContent(post.getContent());
        for (Long aid : aids) {
            Optional<Attach> oAttach = attachRepository.findById(aid);
            if (oAttach.isPresent()) {
                Attach attach = oAttach.get();
                if (attach.getOwnerId() == 0L) {
                    attach.setOwnerId(post.getId());
                    attach.setOwnerType(Attach.OwnerType.POST_ATTACH);
                    attach.setUser(post.getUser());
                    attachService.save(attach);
                }
            }
        }

        // 把无主(ownerId=0)的附件都查出来删除
        List<Attach> noOwnerAttaches = attachRepository.findAllByOwnerIdAndOwnerTypeAndUser(0L, Attach.OwnerType.POST_ATTACH, post.getUser());
        for (Attach attach : noOwnerAttaches) {
            attachService.delete(attach);
        }
    }

}
