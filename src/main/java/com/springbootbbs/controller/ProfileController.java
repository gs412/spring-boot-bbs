package com.springbootbbs.controller;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.Result;
import com.springbootbbs.libs.I18nUtil;
import com.springbootbbs.repository.AttachRepository;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.AttachService;
import com.springbootbbs.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public Result basic_post(@RequestParam("user_face") MultipartFile file) {
        User user = getUser();

        if (file.isEmpty()) {
            return Result.failure("上传失败，请选择文件");
        } else if (file.getSize() > 1024 * 1024) {
            return Result.failure("最大不超过1M");
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

            return Result.success("上传成功");
        }
    }

    @GetMapping("/password")
    public String password(@CookieValue(value = "lang", required = false) String lang, ModelMap m) {
        User user = getUser();

        m.addAttribute("title", I18nUtil.getMessage("profile_password", localizeLang(lang)) + " - Spring Boot BBS");
        m.addAttribute("user", user);

        return "profile/password";
    }

    @PostMapping("/password_post")
    public String password_post(String password_origin, String password_new, String password_confirm, @CookieValue(value = "lang", required = false) String lang, RedirectAttributes redirAttrs) {
        User user = getUser();

        // 校验原密码
        Object result = new SimpleHash("md5", password_origin, user.getUsername(), 1024);
        String password_old = userService.selectPassword(user.getUsername());
        if (!password_old.equals(result.toString())) {
            redirAttrs.addFlashAttribute("message", I18nUtil.getMessage("profile_password_origin_error", localizeLang(lang)));
            return "redirect:/profile/password";
        }

        // 校验密码是否符合要求
        if (password_new.length() < 6) {
            redirAttrs.addFlashAttribute("message", I18nUtil.getMessage("password_min_6", localizeLang(lang)));
            return "redirect:/profile/password";
        }

        // 校验确认密码
        if (!password_new.equals(password_confirm)) {
            redirAttrs.addFlashAttribute("message", I18nUtil.getMessage("profile_password_confirm_error", localizeLang(lang)));
            return "redirect:/profile/password";
        }

        // 设置新密码
        Object result1 = new SimpleHash("md5", password_new, user.getUsername(), 1024);
        user.setPassword(result1.toString());
        userService.save(user);
        // 退出登录
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        redirAttrs.addFlashAttribute("message", I18nUtil.getMessage("profile_password_success", localizeLang(lang)));
        return "redirect:/login";
    }

}
