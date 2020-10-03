package com.springbootbbs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.AttachService;
import com.springbootbbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

        m.addAttribute("user", getUser());

        return "profile/basic";
    }

    @RequestMapping("/basic_post")
    @ResponseBody
    public String basic_post(@RequestParam("user_face") MultipartFile file) throws JsonProcessingException {
        User user = getUser();
        HashMap<String, String> map = new HashMap<>();

        if (file.isEmpty()) {
            map.put("success", "0");
            map.put("message", "上传失败，请选择文件");
        } else {
            String fileName = file.getOriginalFilename();
            // todo https://blog.csdn.net/gnail_oug/article/details/80324120
            Attach attach = new Attach();
            attach.setFile(file);
            attach.setOwnerType(Attach.OwnerType.USER_FACE);
            attach.setOwnerId(user.getId());
            attach.setUserId(user.getId());
            attachService.save(attach);
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

}
