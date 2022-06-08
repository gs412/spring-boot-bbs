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
		String local_lang = localLang(lang);

		String localize = "en-US";
		if (local_lang.equals("cn")) {
			localize = "zh-CN";
		}

		return new Local(localize);
	}

	@ModelAttribute("show_date")
	public ShowDate show_date() {
		return new ShowDate();
	}

	@ModelAttribute("local_lang")
	public String localLang(@CookieValue(value = "lang", required = false) String lang) {
		User user = getUser();
		if (user != null) {
			if (user.getLang().equals("cn")) {
				lang = "cn";
			} else if (user.getLang().equals("en")) {
				lang = "en";
			}
		}

		if (lang == null) {		// 如果上面用户数据库里取不到，并且cookies里也没有，则根据系统语言判定
			Locale locale = LocaleContextHolder.getLocale();
			String system_lang = locale.getLanguage();
			if (system_lang.equals("zh")) {
				lang = "cn";
			} else {
				lang = "en";
			}
		}

		return lang;
	}

}
