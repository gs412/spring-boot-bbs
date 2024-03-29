package com.springbootbbs.controller;

import com.springbootbbs.entiry.User;
import com.springbootbbs.exception.PageNotFoundException;
import com.springbootbbs.libs.Utils;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@GetMapping("/info/{id}")
	public String info(@PathVariable Long id, ModelMap m) {
		Optional<User> optional = userRepository.findById(id);

		if (optional.isEmpty()) {
			throw new PageNotFoundException("用户不存在");
		}

		User user = optional.get();

		m.addAttribute("user", user);

		return "user/info";
	}

	@RequestMapping("/register")
	public String register(ModelMap m) {
		String seccodeSrc;
		int inputWidth;
		if (Utils.seccodeType == 2) {
			Utils.seccodeSize = 4;
			seccodeSrc = "/session/seccode/zh";
			inputWidth = 100;
		} else {
			seccodeSrc = "/session/seccode/en";
			inputWidth = Utils.seccodeSize * 10 + 10;
		}
		m.addAttribute("seccodeSize", Utils.seccodeSize);
		m.addAttribute("seccodeSrc", seccodeSrc);
		m.addAttribute("inputWidth", inputWidth);

		return "user/register";
	}

	@RequestMapping("/register_save")
	public String register_save(String username, String password, String password_confirm, String captcha, HttpSession session, ModelMap m) {
		username = StringUtils.trimToEmpty(username);
		password = StringUtils.trimToEmpty(password);
		password_confirm = StringUtils.trimToEmpty(password_confirm);

		String msg = "";
		String sessionName = Utils.seccodeType == 1 ? "seccodeEn" : "seccodeZh";

		if (captcha.equals("") || !captcha.equalsIgnoreCase(session.getAttribute(sessionName).toString())) {
			msg = "验证码错误";
			session.setAttribute(sessionName, RandomStringUtils.random(4, "ABCDEFGHJKLPQRSTUVXY"));
		} else if (userRepository.findByUsername(username) != null) {
			msg = "用户名已存在";
		} else if (username.length() < 3) {
			msg = "用户名最少3个字符";
		} else if (username.length() > 20) {
			msg = "用户名最多20个字符";
		} else if (!Pattern.matches("^[\\w_]+$", username)) {
			msg = "用户名只可以是字母、数字、下划线";
		} else if (Pattern.matches("^\\d.*", username)) {
			msg = "用户名不可以数字开头";
		} else if (password.length() < 6) {
			msg = "密码最少6个字符";
		} else if (!password.equals(password_confirm)) {
			msg = "两次密码不一致";
		} else {
			Object result = new SimpleHash("md5", password, username, 1024);
			User user = new User();
			user.setUsername(username);
			user.setPassword(result.toString());
			user.setLang("");
			userService.save(user);

			// 执行登录操作
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			currentUser.login(token);

			session.removeAttribute(sessionName);

			return "redirect:/";
		}

		m.addAttribute("username", username);
		m.addAttribute("msg", msg);

		String seccodeSrc;
		int inputWidth;
		if (Utils.seccodeType == 2) {
			Utils.seccodeSize = 4;
			seccodeSrc = "/session/seccode/zh";
			inputWidth = 100;
		} else {
			seccodeSrc = "/session/seccode/en";
			inputWidth = Utils.seccodeSize * 10 + 10;
		}
		m.addAttribute("seccodeSize", Utils.seccodeSize);
		m.addAttribute("seccodeSrc", seccodeSrc);
		m.addAttribute("inputWidth", inputWidth);

		return "user/register";
	}

}
