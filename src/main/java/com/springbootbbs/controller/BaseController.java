package com.springbootbbs.controller;

import com.springbootbbs.entiry.User;
import com.springbootbbs.libs.helpers.Local;
import com.springbootbbs.libs.helpers.ShowDate;
import com.springbootbbs.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

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
	public Local local(@CookieValue(value = "lang", required = false) String lang) {
		String localize;
		User user = getUser();
		Locale locale = LocaleContextHolder.getLocale();
		String system_lang = locale.getLanguage();
		System.out.println(system_lang);

		if (user.getLang().equals("cn")) {
			localize = "zh-CN";
		} else if (user.getLang().equals("en")) {
			localize = "en-US";
		} else if (lang.equals("cn")) {
			localize = "zh-CN";
		} else if (lang.equals("en")) {
			localize = "en-US";
		} else if (system_lang.equals("zh")) {
			localize = "zh-CN";
		} else {
			localize = "en-US";
		}

		return new Local(localize);
	}

	@ModelAttribute("show_date")
	public ShowDate show_date() {
		return new ShowDate();
	}

	@ModelAttribute("local_lang")
	public String localLang(@CookieValue(value = "lang", required = false) String lang) {
		return lang;
	}

}
