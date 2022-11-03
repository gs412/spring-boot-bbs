package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.Post;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.PostRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.AttachService;
import com.springbootbbs.service.PostService;
import com.springbootbbs.service.TopicService;
import com.springbootbbs.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller("admin.UserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserService userService;

    @Autowired
    AttachService attachService;

    @Autowired
    PostService postService;

    @Autowired
    TopicService topicService;

    @GetMapping("/users")
    public String users(String p, ModelMap m) {
        int p1 = NumberUtils.toInt(p, 1);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<User> page = userRepository.findAllByBanned(pageable, false);

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "admin/user/users";
    }

    @GetMapping("/banned-users")
    public String bannedUsers(String p, ModelMap m) {
        int p1 = NumberUtils.toInt(p, 1);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<User> page = userRepository.findAllByBanned(pageable, true);

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "admin/user/banned-users";
    }

    @PostMapping("/user/{id}/ban")
    public String userBan(@PathVariable Long id, final HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(userService::ban);

        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/user/{id}/unban")
    public String userUnBan(@PathVariable Long id, final HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(userService::unBan);

        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/user/{id}/remove")
    public String userRemove(@PathVariable Long id, final HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<Attach> attachList = attachRepository.findAllByUser(user);
            attachList.forEach(attachService::delete);

            List<Post> postList = postRepository.findAllByUser(user);
            postList.forEach(postService::delete);

            List<Topic> topicList = topicRepository.findAllByUser(user);
            topicList.forEach(topicService::delete);

            userService.delete(user);
        }

        return "redirect:" + request.getHeader("referer");
    }

}
