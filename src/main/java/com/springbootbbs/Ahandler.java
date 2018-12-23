package com.springbootbbs;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Ahandler {

	@Autowired
	private UserRepository userRepository;

	// 登录的url
	@RequestMapping({"/login", "/"})
	public String indexHtml(ModelMap m) {
		return "index";
	}

	// 属于user角色@RequiresRoles("user")
	// 必须同时属于user和admin角@RequiresRoles({ "user", "admin" })
	// 属于user或者admin之一，修改logical 为 OR 即可 @RequiresRoles(value = { "user", "admin"}, logical = Logical.OR)
	@RequestMapping("/showUserHtml.action")
	@RequiresRoles(value = {"user", "admin"}, logical = Logical.OR)
	@RequiresPermissions("user:query")
	public String userHtml() {
		return "user";
	}

	@RequestMapping("/showAdminHtml.action")
	@RequiresRoles("admin")
	@RequiresPermissions("admin:query")
	public String adminHtml() {
		return "admin";
	}

	@RequestMapping("/unauthorized.action")
	public String unauthorized() {
		return "abc";
	}

	@RequestMapping("/LoginSuccess.action")
	public String listHtml(ModelMap m) {
		Subject subject = SecurityUtils.getSubject();
		String username = (String)subject.getPrincipal();
		User user = userRepository.findByUsername(username);

		m.addAttribute("user", user);

		return "list";
	}

	@RequestMapping("/error.action")
	public String error() {
		int a = 1/0;
		return "abc";
	}

}
