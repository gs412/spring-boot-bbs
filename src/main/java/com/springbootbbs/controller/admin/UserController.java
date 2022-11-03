package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.UserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String users(String p, ModelMap m) {
        int p1 = NumberUtils.toInt(p, 1);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(p1 - 1, 20, Sort.by(order));

        Page<User> page = userRepository.findAllByBanned(pageable, false);
        System.out.println(page.getContent());

        m.addAttribute("page", page);
        m.addAttribute("user", getUser());

        return "admin/user/users";
    }

}
