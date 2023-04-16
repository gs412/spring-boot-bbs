package com.springbootbbs.controller;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.Result;
import com.springbootbbs.libs.I18nUtil;
import com.springbootbbs.libs.Utils;
import com.springbootbbs.repository.CategoryRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Controller
public class IndexController extends BaseController {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String index(String p, @RequestParam HashMap<String, String> allRequestParams, ModelMap m, @CookieValue(value = "lang", required = false) String lang,
                        @CookieValue(value = "index_order_by", required = false) User.IndexOrderBy cookieIndexOrderBy) {
        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

        User user = getUser();
        User.IndexOrderBy indexOrderBy;
        if (user != null) {
            indexOrderBy = user.getIndexOrderBy();
        } else {
            indexOrderBy = cookieIndexOrderBy != null ? cookieIndexOrderBy : User.IndexOrderBy.REPLIED_AT;
        }
        String order_by_label;
        if (indexOrderBy == User.IndexOrderBy.CREATED_AT) {
            order_by_label = I18nUtil.getMessage("index_order_by_created_at", localizeLang(lang));
        } else {
            order_by_label = I18nUtil.getMessage("index_order_by_replied_at", localizeLang(lang));
        }

        int p1 = NumberUtils.toInt(p, 1);
        Order order = new Order(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(p1-1, 20, Sort.by(order));

        String tab = allRequestParams.getOrDefault("tab", "all");
        String searchWord = allRequestParams.getOrDefault("searchWord", "").trim();

        Page<Topic> page;
        if (!searchWord.isEmpty()) {
            String[] keywords = searchWord.split(" ");
            List<String> keywordList = Arrays.stream(keywords).filter(word->word.length()>0).distinct().toList();

            page = topicRepository.searchTitleByKeywords(keywordList, pageable);

            searchWord = String.join(" ", keywordList);
        } else if (tab.equals("all")) {
            page = topicRepository.findAllForIndex(pageable, indexOrderBy);
        } else {
            page = topicRepository.findAllForIndexByCategory(tab, pageable, indexOrderBy);
        }

        Iterable<Topic> topics = page.getContent();
        for (Topic topic : topics) {
            if (!searchWord.isEmpty()) {
                String newTitle = topic.getTitle();
                for (String word : searchWord.split(" ")) {
                    newTitle = newTitle.replaceAll("((?i)%s)".formatted(word), "<font color=red>$1</font>");
                }
                topic.setShortTitle(newTitle);
            } else {
                topic.setShortTitle(topic.getTitle(200, ".."));
            }
        }

        m.addAttribute("title", "Spring Boot BBS - " + I18nUtil.getMessage("index_page", localizeLang(lang)));
        m.addAttribute("page", page);
        m.addAttribute("topics", topics);
        m.addAttribute("user", user);
        m.addAttribute("categories", categories);
        m.addAttribute("tab", tab);
        m.addAttribute("query_str", Utils.makeQueryStr(allRequestParams));
        m.addAttribute("searchWord", searchWord);
        m.addAttribute("indexOrderBy", indexOrderBy);
        m.addAttribute("created_at", User.IndexOrderBy.CREATED_AT);
        m.addAttribute("replied_at", User.IndexOrderBy.REPLIED_AT);
        m.addAttribute("order_by_label", order_by_label);

        return "index";
    }

    @GetMapping("/change_index_order_by")
    public String ChangeIndexOrderBy(@RequestParam User.IndexOrderBy order_by, HttpServletRequest request, HttpServletResponse response) {
        User user = getUser();
        if (user != null) {
            user.setIndexOrderBy(order_by);
            userRepository.save(user);
        }

        Cookie cookie = new Cookie("index_order_by", order_by.toString());
        cookie.setMaxAge(86400*365*10);
        response.addCookie(cookie);

        return "redirect:" + request.getHeader("referer");
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

    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @RequestMapping(value = "/seccode_check", method = RequestMethod.POST, produces = "application/json")
    public Result seccodeCheck(String seccode, HttpSession session) {
        if (seccode == session.getAttribute("seccode")) {
            return Result.success("");
        } else {
            return Result.failure("");
        }
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

        String text = RandomStringUtils.random(4, "ABCDEFGHJKLPQRSTUVXY");

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
        String swirl = RandomStringUtils.random(1, "+-") + rands(20, 30);
        String wave1 = RandomStringUtils.random(1, "+-") + rands(2, 4) + "x" + rands(120, 150);
        String wave2 = RandomStringUtils.random(1, "+-") + rands(2, 4) + "x" + rands(120, 150);
        String fontPath = Utils.getBasePath() + "/vendor/fonts/LondrinaShadow-Regular.otf ";

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
