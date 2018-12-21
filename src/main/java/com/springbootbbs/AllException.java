package com.springbootbbs;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllException {

	@ExceptionHandler(value = UnauthorizedException.class)
	@ResponseBody
	public String roleException(UnauthorizedException e) {
		System.out.println("------------------------->" + e);
		return "角色权限不够!!!";
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String allException(Exception e) {
		System.out.println("------------------------->" + e);
		return "系统出现异常!!!";
	}

}
