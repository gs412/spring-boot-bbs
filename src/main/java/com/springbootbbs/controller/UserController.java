package com.springbootbbs.controller;

import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.UserRepository;
import com.springbootbbs.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@RequestMapping("/info")
	@ResponseBody
	public List<User> info() {
		List<User> list = new ArrayList<User>();
		list = userRepository.findAll();

		return list;
	}

	@RequestMapping("/register")
	public String register(ModelMap m) {
		return "user/register";
	}

	@RequestMapping("/register_save")
	public String register_save(String username, String password, String password_confirm, ModelMap m) {
		username = StringUtils.trimToEmpty(username);
		password = StringUtils.trimToEmpty(password);
		password_confirm = StringUtils.trimToEmpty(password_confirm);

		String msg = "";

		if (username.length() < 3) {
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
			userService.createUser(user);
		}

		m.addAttribute("username", username);
		m.addAttribute("msg", msg);

		return "user/register";
	}

}
