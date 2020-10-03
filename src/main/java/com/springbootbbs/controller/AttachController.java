package com.springbootbbs.controller;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attach")
public class AttachController extends BaseController {

    @Autowired
    AttachRepository attachRepository;

    @RequestMapping(value = "/show/{id}")
    public String show(@PathVariable Long id) {
        Attach attach = attachRepository.findById(id).get();


        return "";
    }

}
