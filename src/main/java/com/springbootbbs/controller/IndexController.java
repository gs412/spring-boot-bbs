package com.springbootbbs.controller;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.Utils;
import com.springbootbbs.libs.helpers.Local;
import com.springbootbbs.repository.CategoryRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@Controller
public class IndexController extends BaseController {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String index(String p, @RequestParam(defaultValue = "all") String tab, ModelMap m, @RequestParam HashMap<String, String> allRequestParams) {
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

        int p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(p1-1, 20, Sort.by(order));

        String searchWord = allRequestParams.getOrDefault("searchWord", "");

        Page<Topic> page;
        if (!searchWord.isEmpty()) {
            page = topicRepository.findAllByTitleLikeAndDeletedOrderByIdDesc(pageable, "%" + searchWord + "%", false);
        } else if (tab.equals("all")) {
            page = topicRepository.findAllByDeletedOrderByIdDesc(pageable, false);
        } else {
            page = topicRepository.findAllByCategoryTabAndDeletedOrderByIdDesc(pageable, tab, false);
        }

        Iterable<Topic> topics = page.getContent();
        for (Topic topic : topics) {
            if (!searchWord.isEmpty()) {
                String newTitle = topic.getTitle().replace(searchWord, "<font color=red>" + searchWord + "</font>");
                topic.setTitle(newTitle);
            } else {
                topic.setTitle(topic.getTitle(50, ".."));
            }
        }

        m.addAttribute("title", "Spring Boot BBS - 首页");
        m.addAttribute("page", page);
        m.addAttribute("topics", topics);
        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);
        m.addAttribute("tab", tab);
        m.addAttribute("query_str", Utils.makeQueryStr(allRequestParams));
        m.addAttribute("searchWord", searchWord);

        return "index";
    }

    @RequestMapping("/change_language")
    public String changeLanguage(@RequestParam String lang, HttpServletRequest request, HttpServletResponse response) {
        User user = getUser();
        if (user != null) {
            user.setLang(lang);
            userRepository.save(user);
        }

        Cookie cookie = new Cookie("lang", lang);
        cookie.setMaxAge(86400*365*10);
        response.addCookie(cookie);

        return "redirect:" + request.getHeader("referer");
    }

}
