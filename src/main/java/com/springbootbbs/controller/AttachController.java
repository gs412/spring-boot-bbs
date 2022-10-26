package com.springbootbbs.controller;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/attach")
public class AttachController extends BaseController {

    @Autowired
    AttachRepository attachRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/show/{id}")
    @ResponseBody
    public void show(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Attach attach = attachRepository.findById(id).get();

        response.setContentType(attach.getContentType());
        if (!attach.getContentType().startsWith("image/")) {
            String fileName = URLEncoder.encode(attach.getName(), StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLengthLong(attach.getSize());
        }

        File file = new File(attach.getAbsolutePath());
        InputStream in=new FileInputStream(file);
        IOUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = "/show_user_face/{uid}")
    @ResponseBody
    public void show_user_face(@PathVariable Long uid, HttpServletResponse response) throws IOException {
        User user = userRepository.findById(uid).get();
        Attach attach = user.getUserFace(attachRepository);

        InputStream in;
        if (attach != null) {
            File file = new File(attach.getAbsolutePath());
            in = new FileInputStream(file);
            response.setContentType(attach.getContentType());
        } else {
            in = new ClassPathResource("vendor/images/userface.jpg").getInputStream();
            response.setContentType("image/jpeg");
            response.setHeader("Last-Modified", "Wed, 20 Oct 2022 11:42:47 GMT");
            response.setHeader("Vary", "Origin");
            response.setHeader("Vary", "Access-Control-Request-Method");
            response.setHeader("Vary", "Access-Control-Request-Headers");
        }
        IOUtils.copy(in, response.getOutputStream());
    }

}
