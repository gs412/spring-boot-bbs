package com.springbootbbs.controller;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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

        File file = new File(attach.getAbsolutePath());
        response.setContentType(attach.getContentType());
        InputStream in=new FileInputStream(file);
        IOUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = "/show_user_face/{uid}")
    @ResponseBody
    public void show_user_face(@PathVariable Long uid, HttpServletResponse response) throws IOException {
        User user = userRepository.findById(uid).get();
        Attach attach = user.getUserFace(attachRepository);

        File file = new File(attach.getAbsolutePath());
        response.setContentType(attach.getContentType());
        InputStream in=new FileInputStream(file);
        IOUtils.copy(in, response.getOutputStream());
    }

}
