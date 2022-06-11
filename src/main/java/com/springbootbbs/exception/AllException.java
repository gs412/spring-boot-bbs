package com.springbootbbs.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉类
 *
 * @author Bean
 *
 */
@ControllerAdvice
public class AllException {

	// 角色權权限异常捕捉
	@ExceptionHandler(value = UnauthorizedException.class)
	@ResponseBody
	public String roleException(UnauthorizedException e) {
		System.out.println("------------------------->" + e);
		return "角色权限不够!!!";
	}

	// 网页未找到捕捉
	@ExceptionHandler(value = PageNotFoundException.class)
	public ResponseEntity<Object> pageNotFoundException(PageNotFoundException e) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body("<h3>404 " + e.getMessage() + "</h3>");
	}

	// 其它异常异常捕捉
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String allException(Exception e) {
		System.out.println("------------------------->" + e);
		e.printStackTrace();
		return "系统出现异常!!!";
	}

}
