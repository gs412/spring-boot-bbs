package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.IndexController")
public class IndexController extends BaseController {

	@RequestMapping("/admin")
	public String index() {

		return "admin/index";
	}

}
