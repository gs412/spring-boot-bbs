package com.springbootbbs.controller;

import com.springbootbbs.libs.Result;
import com.springbootbbs.libs.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/session")
public class SessionController extends BaseController{

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

        String text = RandomStringUtils.random(Utils.seccodeSize, "ABCDEFGJKLPQRSTUVY");

        session.setAttribute("seccodeEn", text);

        for (int i=0; i<Utils.seccodeSize; i++) {
            String chr = text.toString().split("")[i];
            makeFontPngsEn(chr, i + 1, fileNamePre, rgb);
        }

        StringBuilder command = new StringBuilder("convert -size " + (Utils.seccodeSize*25+20) + "x60 xc:white ");
        for (int i=1; i<=Utils.seccodeSize; i++) {
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

        for (int i=1; i<=Utils.seccodeSize; i++) {
            Runtime.getRuntime().exec("rm /tmp/" + fileNamePre + i + ".png");
        }

        response.setContentType("image/png");
        IOUtils.copy(in, response.getOutputStream());
    }

    private void makeFontPngsEn(String chr, int i, String fileNamePre, ArrayList<String> rgb) throws IOException, InterruptedException {
        String i_to_s = String.valueOf(i);
        String textColor = "rgba(" + String.join(", ", rgb) + ", 1)";
        String rotate = String.valueOf(90);
        String swirl = RandomStringUtils.random(1, "+-") + rands(50, 60);
        String wave1 = RandomStringUtils.random(1, "+-") + rands(2, 4) + "x" + rands(120, 150);
        String wave2 = RandomStringUtils.random(1, "+-") + rands(2, 4) + "x" + rands(120, 150);
        String fontPath = Utils.getBasePath() + "/vendor/fonts/LondrinaShadow-Regular.otf";

        String command = """
                /usr/bin/convert -size 40x50 -fill '#{textColor}' -background none \
                -swirl #{swirl} \
                -rotate -#{rotate} \
                -wave #{wave1} \
                -rotate +#{rotate} \
                -wave #{wave2} \
                -font #{fontPath} \
                -pointsize 60 -gravity Center label:#{char} \
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
