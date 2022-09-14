package com.springbootbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.I18nUtil;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.AttachService;
import com.springbootbbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AttachRepository attachRepository;

    @Autowired
    private AttachService attachService;

    @RequestMapping("/basic")
    public String basic(ModelMap m) {
        User user = getUser();

        m.addAttribute("user", user);
        m.addAttribute("user_face_link", user.getUserFaceLink());

        return "profile/basic";
    }

    @RequestMapping(value = "/basic_post", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String basic_post(@RequestParam("user_face") MultipartFile file) throws JsonProcessingException {
        User user = getUser();
        HashMap<String, String> map = new HashMap<>();

        if (file.isEmpty()) {
            map.put("success", "0");
            map.put("message", "上传失败，请选择文件");
        } else if (file.getSize() > 1024 * 1024) {
            map.put("success", "0");
            map.put("message", "最大不超过1M");
        } else {
            Attach oldFace = user.getUserFace(attachRepository);

            Attach attach = new Attach();
            attach.setFile(file);
            attach.setOwnerType(Attach.OwnerType.USER_FACE);
            attach.setOwnerId(user.getId());
            attach.setUser(user);
            if (attachService.save(attach) != null) {
                attachService.delete(oldFace);
            }

            map.put("success", "1");
            map.put("message", "上传成功");
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

    @GetMapping("/password")
    public String password(@CookieValue(value = "lang", required = false) String lang, ModelMap m) {
        User user = getUser();

        m.addAttribute("title", I18nUtil.getMessage("profile_password", localizeLang(lang)) + " - Spring Boot BBS");
        m.addAttribute("user", user);

        return "profile/password";
    }

    @PostMapping("/password_post")
    @ResponseBody
    public String password_post(String password_origin, String password_new, String password_confirm, @CookieValue(value = "lang", required = false) String lang, RedirectAttributes redirAttrs) {
        

        redirAttrs.addFlashAttribute("message", I18nUtil.getMessage("profile_password_success", localizeLang(lang)));

        return "";
    }

}
