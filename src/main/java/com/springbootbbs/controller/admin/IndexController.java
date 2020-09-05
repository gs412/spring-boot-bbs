package com.springbootbbs.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.IndexController")
public class IndexController extends BaseController {

	@RequestMapping("/admin")
	public String index() {

		return "admin/index";
	}

}
