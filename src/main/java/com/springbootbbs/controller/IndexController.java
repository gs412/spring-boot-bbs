package com.springbootbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.I18nUtil;
import com.springbootbbs.libs.Utils;
import com.springbootbbs.repository.CategoryRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        String searchWord = allRequestParams.getOrDefault("searchWord", "").trim();

        Page<Topic> page;
        if (!searchWord.isEmpty()) {
            String[] keywords = searchWord.split(" ");
            List<String> keywordList = Arrays.stream(keywords).filter(word->word.length()>0).toList();
            List<String> kws = keywordList.stream().map("%%%s%%"::formatted).toList();

            //page = topicRepository.searchTitleByKeywords(keywordList, pageable);
            if (keywordList.size() == 1) {
                page = topicRepository.searchTitleByKeywords(kws.get(0), pageable);
            } else if (keywordList.size() == 2) {
                page = topicRepository.searchTitleByKeywords(kws.get(0), kws.get(1), pageable);
            } else if (keywordList.size() == 3) {
                page = topicRepository.searchTitleByKeywords(kws.get(0), kws.get(1), kws.get(2), pageable);
            } else if (keywordList.size() == 4) {
                page = topicRepository.searchTitleByKeywords(kws.get(0), kws.get(1), kws.get(2), kws.get(3), pageable);
            } else {
                page = topicRepository.searchTitleByKeywords(kws.get(0), kws.get(1), kws.get(2), kws.get(3), kws.get(4), pageable);
            }

            searchWord = String.join(" ", keywordList);
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

    @RequestMapping(value = "/seccode_check", method = RequestMethod.POST, produces = "application/json")
    public String seccodeCheck(String seccode, HttpSession session) throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();

        if (seccode == session.getAttribute("seccode")) {
            map.put("success", true);
            map.put("message", "");
        } else {
            map.put("success", false);
            map.put("message", "");
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

    @RequestMapping("/seccode/en")
    @ResponseBody
    public void seccodeEn(HttpServletResponse response, HttpSession session) throws IOException, InterruptedException {
        long timestamp = new Date().getTime();
        String fileNamePre = "seccode_en_" + timestamp + "_" + rands(100000, 999999) + "_";

        ArrayList<String> rgb = new ArrayList<>();
        rgb.add(rands(10, 210));
        rgb.add(rands(10, 210));
        rgb.add(rands(10, 210));

        String[] chars = "ABCDEFGHJKLPQRSTUVXY".split("");
        StringBuilder text = new StringBuilder();
        for (int i=0; i<4; i++) {
            text.append(chars[randi(0, chars.length)]);
        }

        session.setAttribute("seccode", text);

        for (int i=0; i<4; i++) {
            String chr = text.toString().split("")[i];
            makeFontPngs(chr, i + 1, fileNamePre, rgb);
        }

        StringBuilder command = new StringBuilder("convert -size 120x47 xc:white ");
        for (int i=1; i<=4; i++) {
            command.append(
                    "/tmp/#{fileNamePre}#{i}.png -geometry +#{geometry}+3 -composite "
                            .replace("#{fileNamePre}", fileNamePre)
                            .replace("#{i}", String.valueOf(i))
                            .replace("#{geometry}", String.valueOf(1+(i-1)*24+randi(-1,1)))
            );
        }
        command.append("png:-");
        Process ps = Runtime.getRuntime().exec(command.toString());
        ps.waitFor();
        InputStream in = ps.getInputStream();

        for (int i=1; i<=4; i++) {
            Runtime.getRuntime().exec("rm /tmp/" + fileNamePre + i + ".png");
        }

        response.setContentType("image/png");
        IOUtils.copy(in, response.getOutputStream());
    }

    private void makeFontPngs(String chr, int i, String fileNamePre, ArrayList<String> rgb) throws IOException, InterruptedException {
        String i_to_s = String.valueOf(i);
        String textColor = "rgba(" + String.join(", ", rgb) + ", 1)";
        String rotate = String.valueOf(90);
        String swirl = new String[]{"+", "-"}[randi(0, 1)] + rands(20, 30);
        String wave1 = new String[]{"+", "-"}[randi(0, 1)] + rands(2, 4) + "x" + rands(120, 150);
        String wave2 = new String[]{"+", "-"}[randi(0, 1)] + rands(2, 4) + "x" + rands(120, 150);
        String fontPath = Utils.getBasePath() + "/vendor/fonts/huawencaiyun.ttf";

        String command = """
                /usr/bin/convert -size 40x40 -fill '#{textColor}' -background none \
                -swirl #{swirl} \
                -rotate -#{rotate} \
                -wave #{wave1} \
                -rotate +#{rotate} \
                -wave #{wave2} \
                -font '#{fontPath}' \
                -pointsize 40 -gravity Center label:#{char} \
                /tmp/#{fileNamePre}#{i_to_s}.png
                """
                .replace("#{textColor}", textColor)
                .replace("#{swirl}", swirl)
                .replace("#{rotate}", rotate)
                .replace("#{wave1}", wave1)
                .replace("#{wave2}", wave2)
                .replace("#{fontPath}", fontPath)
                .replace("#{char}", chr)
                .replace("#{fileNamePre}", fileNamePre)
                .replace("#{i_to_s}", i_to_s);

        Process ps = Runtime.getRuntime().exec(command);
        ps.waitFor();
    }

    private int randi(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }

    private String rands(int start, int end) {
        Random random = new Random();
        return String.valueOf(random.nextInt(end - start) + start);
    }

}
