package com.springbootbbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/session")
public class SessionController extends BaseController {
    private String fileNamePre;

    private ArrayList<Integer> rgb;

    @GetMapping("/seccodeEn")
    public String seccodeEn() {
        this.fileNamePre = new SimpleDateFormat("yyyyMMddHHmmss_").format(new Date()) + (new Random().nextInt(99999999 - 10000000 + 1) + 10000000) + "_";
        rgb.add(new Random().nextInt(210 - 10 + 1) + 10);
        rgb.add(new Random().nextInt(210 - 10 + 1) + 10);
        rgb.add(new Random().nextInt(210 - 10 + 1) + 10);

        String text = "ABCDEFGHJKLMNPQRTUVWXY";

        return "";
    }

}
