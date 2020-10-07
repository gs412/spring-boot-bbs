package com.springbootbbs.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.CategoryController")
@RequestMapping("/admin/catetory")
public class CategoryController extends BaseController {

    public String categories(ModelMap m) {

        m.addAttribute("user", getUser());

        return "/admin/category/categories";
    }

}
