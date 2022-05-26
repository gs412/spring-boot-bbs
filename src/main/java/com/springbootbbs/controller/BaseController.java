package com.springbootbbs.controller;

import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.helpers.Local;
import com.springbootbbs.libs.helpers.ShowDate;
import com.springbootbbs.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class BaseController {

	@Autowired
	UserRepository userRepository;

	public User getUser() {
		Subject subject = SecurityUtils.getSubject();
		Long uid = (Long)subject.getPrincipal();
		if (uid != null) {
			User user = userRepository.findById(uid).get();
			return user;
		} else {
			return null;
		}
	}

	@ModelAttribute("local")
	public Local local() {
		return new Local("zn-CN");
	}

	@ModelAttribute("show_date")
	public ShowDate show_date() {
		return new ShowDate();
	}

}
