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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

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
        Optional<Attach> optionalAttach = attachRepository.findById(id);
        Attach attach;

        if (optionalAttach.isEmpty()) {
            fileNotFound(response);
            return;
        } else {
            attach = optionalAttach.get();
        }

        if (attach.getUser().getBanned()) {
            InputStream in = new ClassPathResource("vendor/images/user-is-banned.png").getInputStream();
            response.setContentType("image/png");
            response.setHeader("Last-Modified", "Thu, 20 Oct 2022 12:45:22 GMT");
            IOUtils.copy(in, response.getOutputStream());
            return;
        }

        response.setContentType(attach.getContentType());
        if (!attach.getContentType().startsWith("image/")) {
            String fileName = URLEncoder.encode(attach.getName(), StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLengthLong(attach.getSize());
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(attach.getCreatedAt());
            cal.add(Calendar.HOUR, -8);
            Format formatter = new SimpleDateFormat("EEE, d MMM yyyy H:m:s 'GMT'");
            response.setHeader("Last-Modified", formatter.format(cal.getTime()));
            response.setHeader("strict-transport-security", "max-age=31536000");
        }

        File file = new File(attach.getAbsolutePath());
        if (!file.exists()) {
            fileNotFound(response);
        } else {
            InputStream in = new FileInputStream(file);
            IOUtils.copy(in, response.getOutputStream());
        }
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

            Calendar cal = Calendar.getInstance();
            cal.setTime(attach.getCreatedAt());
            cal.add(Calendar.HOUR, -8);
            Format formatter = new SimpleDateFormat("EEE, d MMM yyyy H:m:s 'GMT'");
            response.setHeader("Last-Modified", formatter.format(cal.getTime()));
        } else {
            in = new ClassPathResource("vendor/images/userface.jpg").getInputStream();
            response.setContentType("image/jpeg");
            response.setHeader("Last-Modified", "Thu, 20 Oct 2022 12:45:22 GMT");
        }
        response.setHeader("strict-transport-security", "max-age=31536000");
        IOUtils.copy(in, response.getOutputStream());
    }

    private void fileNotFound(HttpServletResponse response) throws IOException {
        InputStream in = new ClassPathResource("vendor/images/image-not-found.png").getInputStream();
        response.setContentType("image/png");
        response.setHeader("Last-Modified", "Thu, 20 Oct 2022 12:45:22 GMT");
        IOUtils.copy(in, response.getOutputStream());
    }

}
