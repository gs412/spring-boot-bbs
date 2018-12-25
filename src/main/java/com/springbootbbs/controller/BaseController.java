package com.springbootbbs.controller;

import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

	@Autowired
	UserRepository userRepository;

	public User getUser() {
		Subject subject = SecurityUtils.getSubject();
		Long uid = (Long)subject.getPrincipal();
		User user = userRepository.findById(uid).get();

		return user;
	}

}
