package com.springbootbbs.controller;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.I18nUtil;
import com.springbootbbs.libs.Utils;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


@Controller
public class IndexController extends BaseController {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String index(String p, @RequestParam HashMap<String, String> allRequestParams, ModelMap m, @CookieValue(value = "lang", required = false) String lang) {
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

        int p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(p1-1, 20, Sort.by(order));

        String tab = allRequestParams.getOrDefault("tab", "all");
        String searchWord = allRequestParams.getOrDefault("searchWord", "");

        Page<Topic> page;
        if (!searchWord.isEmpty()) {
            page = topicRepository.findAllByTitleLikeAndDeletedOrderByIdDesc(pageable, "%" + searchWord + "%", false);
        } else if (tab.equals("all")) {
            page = topicRepository.findAllByDeletedOrderByStickDescIdDesc(pageable, false);
        } else {
            page = topicRepository.findAllByCategoryTabAndDeletedOrderByStickDescIdDesc(pageable, tab, false);
        }

        Iterable<Topic> topics = page.getContent();
        for (Topic topic : topics) {
            if (!searchWord.isEmpty()) {
                String newTitle = topic.getTitle().replace(searchWord, "<font color=red>" + searchWord + "</font>");
                topic.setTitle(newTitle);
            } else {
                topic.setTitle(topic.getTitle(200, ".."));
            }
        }

        m.addAttribute("title", "Spring Boot BBS - " + I18nUtil.getMessage("index_page", localizeLang(lang)));
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

    @RequestMapping("/seccode/en")
    @ResponseBody
    public String seccodeEn(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        long timestamp = new Date().getTime();
        Random rand = new Random();
        int randInt = rand.nextInt(999999 - 100000 + 1) + 100000;
        String fileNamePre = "seccode_en_" + timestamp + "_" + randInt + "_";

        ArrayList<String> rgb = new ArrayList<>();
        rgb.add(String.valueOf(rand.nextInt(210-10) + 10));
        rgb.add(String.valueOf(rand.nextInt(210-10) + 10));
        rgb.add(String.valueOf(rand.nextInt(210-10) + 10));

        String[] chars = "ABCDEFGHJKLMNPQRSTUVWXY".split("");
        StringBuilder text = new StringBuilder();
        for (int i=0; i<4; i++) {
            text.append(chars[rand.nextInt(chars.length)]);
        }

        session.setAttribute("seccode", text);

        for (int i=0; i<4; i++) {
            String chr = text.toString().split("")[i];
            makeFontPngs(chr, i, fileNamePre, rgb);
        }

        return fileNamePre;
    }

    private void makeFontPngs(String chr, int i, String fileNamePre, ArrayList<String> rgb) {
        String textColor = "rgba(" + String.join(", ", rgb) + ", 1)";
        int rotate = 90;
        String command = """
                convert -size 40x40 -fill '#{text_color}' -background none \
                -swirl #{%w[- +].sample+rand(20..30).to_s} \
                -rotate -#{rotate} \
                -wave #{%w[- +].sample+rand(2..4).to_s}x#{rand(120..150)} \
                -rotate +#{rotate} \
                -wave #{%w[- +].sample+rand(2..4).to_s}x#{rand(120..150)} \
                -font '#{Rails.root}/vendor/seccode/fonts/huawencaiyun.ttf' \
                -pointsize 40 -gravity Center label:#{char} \
                /tmp/#{@file_name_pre}#{i}.png
                """;

        System.out.println(command);
    }

}
