package com.springbootbbs;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginHandler {

	@Autowired
	private UserService userService;

	@RequestMapping("/login.action")
	public String login(String username, String password, Map<String, Object> map, HttpSession session) {
		System.out.println(username + "---" + password);

		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			String msg = "";
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);

			token.setRememberMe(true);
			try {
				currentUser.login(token);
				return "redirect:/LoginSuccess.action";
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

			map.put("msg", msg);
			return "/index";
		}

		return "redirect:/LoginSuccess.action";
	}

}
