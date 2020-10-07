package com.springbootbbs.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.CategoryController")
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @RequestMapping("/categories")
    public String categories(ModelMap m) {

        m.addAttribute("user", getUser());

        return "/admin/category/categories";
    }

}
