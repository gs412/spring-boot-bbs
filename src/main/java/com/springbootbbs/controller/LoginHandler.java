package com.springbootbbs.controller;

import com.springbootbbs.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/*
 * 登陆的controller
 */
@Controller
public class LoginHandler {

	@Autowired
	private UserService userService;

	@RequestMapping("/login.action")
	public String login(String username, String password, ModelMap map, HttpSession session) {
		System.out.println(username + "---" + password);

		// 获得当前Subject
		Subject currentUser = SecurityUtils.getSubject();

		// 验证用户是否验证，即是否登录
		if (!currentUser.isAuthenticated()) {
			String msg = "";
			// 把用户名和密码封装为 UsernamePasswordToken 对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);

			// remembermMe记住密码
			token.setRememberMe(true);
			try {
				// 执行登录.
				currentUser.login(token);
				// 登录成功...
				return "redirect:/";
			} catch (IncorrectCredentialsException e) {
				msg = "登陆密码错误";
				System.out.println("登录密码错误！！！" + e);
			} catch (ExcessiveAttemptsException e) {
				msg = "登陆失败次数过多";
				System.out.println("登陆失败次数过多！！！" + e);
			} catch (LockedAccountException e) {
				msg = "账号已被锁定";
				System.out.println("账号已被锁定！！！" + e);
			} catch (DisabledAccountException e) {
				msg = "账号已被禁用";
				System.out.println("账号已被禁用！！！" + e);
			} catch (ExpiredCredentialsException e) {
				msg = "账号已过期";
				System.out.println("账号已过期！！！" + e);
			} catch (UnknownAccountException e) {
				msg = "账号不存在";
				System.out.println("账号不存在！！！" + e);
			} catch (UnauthorizedException e) {
				msg = "您没有的都相应的授权";
				System.out.println("您没有的都相应的授权！！！" + e);
			} catch (Exception e) {
				System.out.println("出错！！！" + e);
			}

			map.addAttribute("msg", msg);
			return "index";
		}

		// 登录成功，重定向到LoginSuccess.action
		return "redirect:/";
	}

}
