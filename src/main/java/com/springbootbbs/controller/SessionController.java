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

    @RequestMapping("/seccode/zh")
    @ResponseBody
    public void seccodeZh(HttpServletResponse response, HttpSession session) throws IOException, InterruptedException {
        long timestamp = new Date().getTime();
        String fileNamePre = "seccode_zh_" + timestamp + "_" + rands(100000, 999999) + "_";

        ArrayList<String> rgb = new ArrayList<>();
        rgb.add(rands(10, 210));
        rgb.add(rands(10, 210));
        rgb.add(rands(10, 210));

        String text = RandomStringUtils.random(4, "的一是在了不和有大这主中人上为们地个用工时要动国产以我到他会作来分生对于学下级就年阶义发成部民可出能方进同行面说种过命度革而多子后自社加小机也经力线本电高量长党得实家定深法表着水理化争现所二起政三好十战无农使性前等反体合斗路图把结第里正新开论之物从当两些还天资事队批如应形想制心样干都向变关点育重其思与间内去因件日利相由压员气业代全组数果期导平各基或月毛然问比展那它最及外没看治提五解系林者米群头意只明四道马认次文通但条较克又公孔领军流入接席位情运器并飞原油放立题质指建区验活众很教决特此常石强极土少已根共直团统式转别造切九你取西持总料连任志观调七么山程百报更见必真保热委手改管处己将修支识病象几先老光专什六型具示复安带每东增则完风回南广劳轮科北打积车计给节做务被整联步类集号列温装即毫知轴研单色坚据速防史拉世设达尔场织历花受求传口断况采精金界品判参层止边清至万确究书术状厂须离再目海交权且儿青才证低越际八试规斯近注办布门铁需走议县兵固除般引齿千胜细影济白格效置推空配刀叶率述今选养德话查差半敌始片施响收华觉备名红续均药标记难存测士身紧液派准斤角降维板许破述技消底床田势端感往神便贺村构照容非搞亚磨族火段算适讲按值美态黄易彪服早班麦削信排台声该击素张密害侯草何树肥继右属市严径螺检左页抗苏显苦英快称坏移约巴材省黑武培著河帝仅针怎植京助升王眼她抓含苗副杂普谈围食射源例致酸旧却充足短划剂宣环落首尺波承粉践府鱼随考刻靠够满夫失包住促枝局菌杆周护岩师举曲春元超负砂封换太模贫减阳扬江析亩木言球朝医校古呢稻宋听唯输滑站另卫字鼓刚写刘微略范供阿块某功套友限项余倒卷创律雨让骨远帮初皮播优占死毒圈伟季训控激找叫云互跟裂粮粒母练塞钢顶策双留误础吸阻故寸盾晚丝女散焊功株亲院冷彻弹错散商视艺灭版烈零室轻血倍缺厘泵察绝富城冲喷壤简否柱李望盘磁雄似困巩益洲脱投送奴侧润盖挥距触星松送获兴独官混纪依未突架宽冬章湿偏纹吃执阀矿寨责熟稳夺硬价努翻奇甲预职评读背协损棉侵灰虽矛厚罗泥辟告卵箱掌氧恩爱停曾溶营终纲孟钱待尽俄缩沙退陈讨奋械载胞幼哪剥迫旋征槽倒握担仍呀鲜吧卡粗介钻逐弱脚怕盐末阴丰编印蜂急拿扩伤飞露核缘游振操央伍域甚迅辉异序免纸夜乡久隶缸夹念兰映沟乙吗儒杀汽磷艰晶插埃燃欢铁补咱芽永瓦倾阵碳演威附牙芽永瓦斜灌欧献顺猪洋腐请透司危括脉宜笑若尾束壮暴企菜穗楚汉愈绿拖牛份染既秋遍锻玉夏疗尖殖井费州访吹荣铜沿替滚客召旱悟刺脑");

        session.setAttribute("seccodeZh", text);

        for (int i=0; i<4; i++) {
            String chr = text.toString().split("")[i];
            makeFontPngsZh(chr, i + 1, fileNamePre, rgb);
        }

        StringBuilder command = new StringBuilder("convert -size 150x50 xc:white ");
        for (int i=1; i<=4; i++) {
            command.append(
                    "/tmp/#{fileNamePre}#{i}.png -geometry +#{geometry}+3 -composite "
                            .replace("#{fileNamePre}", fileNamePre)
                            .replace("#{i}", String.valueOf(i))
                            .replace("#{geometry}", String.valueOf(1+(i-1)*34+randi(-1,1)))
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

    private void makeFontPngsZh(String chr, int i, String fileNamePre, ArrayList<String> rgb) throws IOException, InterruptedException {
        String i_to_s = String.valueOf(i);
        String textColor = "rgba(" + String.join(", ", rgb) + ", 1)";
        String rotate = String.valueOf(90);
        String swirl = RandomStringUtils.random(1, "+-") + rands(20, 30);
        String wave1 = RandomStringUtils.random(1, "+-") + rands(2, 4) + "x" + rands(120, 150);
        String wave2 = RandomStringUtils.random(1, "+-") + rands(2, 4) + "x" + rands(120, 150);
        String fontPath = Utils.getBasePath() + "/vendor/fonts/huawencaiyun.ttf";

        String command = """
                /usr/bin/convert -size 40x40 -fill '#{textColor}' -background none \
                -swirl #{swirl} \
                -rotate -#{rotate} \
                -wave #{wave1} \
                -rotate +#{rotate} \
                -wave #{wave2} \
                -font #{fontPath} \
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
