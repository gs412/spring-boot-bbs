package com.springbootbbs.controller;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.repository.AttachRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/attach")
public class AttachController extends BaseController {

    @Autowired
    AttachRepository attachRepository;

    @RequestMapping(value = "/show/{id}")
    @ResponseBody
    public void show(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Attach attach = attachRepository.findById(id).get();

        File file = new File(attach.getAbsolutePath());
        response.setContentType(attach.getContentType());
        InputStream in=new FileInputStream(file);
        IOUtils.copy(in, response.getOutputStream());
    }

}
